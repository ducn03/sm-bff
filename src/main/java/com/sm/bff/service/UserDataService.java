package com.sm.bff.service;

import com.sm.jpa.domain.User;
import com.sm.jpa.repository.UserRepository;
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
        user.setFullname("testing");
        return userRepository.persistAndFlush(user);
    }

    public Uni<User> update(Long id, User updatedUser) {
        return userRepository.findById(id)
                .onItem().ifNotNull().transform(user -> {
                    // Cập nhật các trường của user nếu tìm thấy
                    user.setUsername(updatedUser.getUsername());
                    user.setPassword(updatedUser.getPassword());
                    user.setPhone(updatedUser.getPhone());
                    user.setEmail(updatedUser.getEmail());
                    user.setFullname(updatedUser.getFullname());
                    return userRepository.persistAndFlush(user).replaceWith(user); // Persist và trả về người dùng đã cập nhật
                }).replaceWith(updatedUser);
    }


    public Uni<Boolean> delete(Long id) {
        return userRepository.findById(id)
                .onItem().ifNotNull().transform(user -> {
                    userRepository.delete(user); // Xóa người dùng
                    return true; // Trả về true nếu xóa thành công
                })
                .onItem().ifNull().continueWith(false);
    }
}
