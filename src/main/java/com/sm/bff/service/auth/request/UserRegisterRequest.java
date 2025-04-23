package com.sm.bff.service.auth.request;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterRequest {
    private String username;
    private String password;
    private String phone;
    private String email;
    private String fullName;
}
