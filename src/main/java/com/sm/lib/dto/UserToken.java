package com.sm.lib.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserToken {
    private String username;
    private List<String> tokenList;
}
