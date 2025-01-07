package com.sm.bff.service;

import com.sm.jpa.domain.User;
import com.sm.jpa.repository.UserRepository;
import com.sm.lib.helper.UniHelper;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class UserDataService {
    private final UserRepository userRepository;

    public UserDataService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Lấy tất cả người dùng
    public Uni<List<User>> getAll() {
        return userRepository.findAll().list();
    }

    public Uni<User> create(User user) {
        return userRepository.persistAndFlush(user);
    }

    public Uni<User> update(Long id, User updatedUser) {
        Uni<User> userUni = userRepository.findById(id);
        return userUni.onItem().transformToUni(user -> {
            setUserData(user, updatedUser);
            return userRepository.persistAndFlush(user).replaceWith(user);
        });
    }

    private void setUserData(User user, User updatedUser){
        user.setUsername(updatedUser.getUsername());
        user.setPassword(updatedUser.getPassword());
        user.setPhone(updatedUser.getPhone());
        user.setEmail(updatedUser.getEmail());
        user.setFullname(updatedUser.getFullname());
    }

    public Uni<Boolean> delete(Long id) {
        Uni<User> userUni = userRepository.findById(id);
        if (userUni == null) {
            return null;
        }

        Uni<Boolean> result = userUni.onItem().transformToUni(user
                -> userRepository.delete(user).onItem().transformToUni(userDelete
                -> userRepository.flush().onItem().transform(flushed -> true)));

        return result;
    }

    public Uni<Boolean> deleteById(Long id) {
        return userRepository.deleteById(id)
                .onItem().transformToUni(user -> {
                    return userRepository.flush().onItem().transform(flushed -> true);
                }).onFailure().recoverWithItem(false);
    }

}
