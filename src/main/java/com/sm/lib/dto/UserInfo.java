package com.sm.lib.dto;

import lombok.Data;

@Data
public class UserInfo {
    private String token;
    private String phone;
    private String fullName;
    private String username;
    private String refreshToken;
    // private List<UserRolePermissionData> roles;
}
