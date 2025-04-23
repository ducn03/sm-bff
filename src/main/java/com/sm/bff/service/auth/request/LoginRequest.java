package com.sm.bff.service.auth.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String username;
    private String phone;
    private String email;
    private String password;
}
