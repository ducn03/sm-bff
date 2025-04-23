package com.sm.lib.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserInfo {
    private String token;
    private String phone;
    private String fullName;
    private String username;
}
