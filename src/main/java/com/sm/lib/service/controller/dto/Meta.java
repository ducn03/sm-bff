package com.sm.lib.service.controller.dto;

import lombok.Data;

@Data
public class Meta {

    private int returnCode;
    private String message;

    public Meta() {

    }

    public Meta(int returnCode, String message) {
        this.returnCode = returnCode;
        this.message = message;
    }
}
