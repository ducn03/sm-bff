package com.sm.bff.service;

import com.sm.jpa.domain.User;
import com.sm.jpa.repository.UserRepository;
import com.sm.lib.helper.UniHelper;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.CustomLog;

import java.util.List;

@ApplicationScoped
@CustomLog
public class UserDataService {
    private final UserRepository userRepository;

    public UserDataService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Lấy tất cả người dùng
    public Uni<List<User>> getAll() {
        return userRepository.findAll().list();
    }

    @Transactional
    public Uni<User> create(User user) {
        return userRepository.persist(user);
    }


    public Uni<User> update(Long id, User updatedUser) {
        Uni<User> userUni = userRepository.findById(id);
        return userUni.onItem().transform(user -> {
            setUserData(user, updatedUser);
            return user;
        });
    }

    private void setUserData(User user, User updatedUser){
        user.setUsername(updatedUser.getUsername());
        user.setPassword(updatedUser.getPassword());
        user.setPhone(updatedUser.getPhone());
        user.setEmail(updatedUser.getEmail());
        user.setFullname(updatedUser.getFullname());
    }

    @Transactional
    public Uni<Boolean> delete(Long id) {
        Uni<User> userUni = userRepository.findById(id);
        if (userUni == null) {
            return null;
        }

        return userUni.onItem().transformToUni(user
                -> userRepository.delete(user).replaceWith(true)
                ).onFailure().recoverWithItem(false);
    }

    @Transactional
    public Uni<Boolean> deleteById(Long id) {
        return userRepository.deleteById(id)
                .replaceWith(true)
                .onFailure().recoverWithItem(false);
    }

}
