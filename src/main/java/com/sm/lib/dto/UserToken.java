package com.sm.lib.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserToken {
    private String username;
    private Set<String> tokenList;
}
