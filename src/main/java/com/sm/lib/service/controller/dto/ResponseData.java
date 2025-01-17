package com.sm.lib.service.controller.dto;

import com.sm.lib.exception.ErrorCodes;
import lombok.Data;

import java.util.UUID;

@Data
public class ResponseData {

    private Meta meta;
    private Object data;

    public static ResponseData success(Object data) {
        ResponseData responseData = new ResponseData();
        responseData.setMeta(
                new Meta(ErrorCodes.OK, "success",
                responseData.getRequestId(),
                responseData.generateResponseId() )
        );
        responseData.setData(data);
        return responseData;
    }

    public static ResponseData error(int error, String message) {
        ResponseData responseData = new ResponseData();
        responseData.setMeta(
                new Meta(error, message,
                        responseData.getRequestId(),
                        responseData.generateResponseId() )
        );
        responseData.setData(null);
        return responseData;
    }

    private String getRequestId() {
        return UUID.randomUUID().toString();
    }

    private String generateResponseId() {
        return UUID.randomUUID().toString();
    }
}
