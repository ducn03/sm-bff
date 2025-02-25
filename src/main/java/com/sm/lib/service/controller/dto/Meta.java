package com.sm.lib.service.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class Meta {

    private int returnCode;
    private String message;
    private String requestId;
    private String responseId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object pagination;

    public Meta() {

    }

    public Meta(int returnCode, String message, String requestId, String responseId) {
        this.returnCode = returnCode;
        this.message = message;
        this.requestId = requestId;
        this.responseId = responseId;
    }
}
