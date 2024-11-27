package com.sm.lib.service.controller.dto;

import com.sm.lib.exception.ErrorCodes;
import lombok.Data;

@Data
public class ResponseData {

    private Meta meta;
    private Object data;

    public static ResponseData success(Object data) {
        ResponseData responseData = new ResponseData();
        responseData.setMeta(new Meta(ErrorCodes.OK, "success"));
        responseData.setData(data);
        return responseData;
    }

    public static ResponseData error(int error, String message) {
        ResponseData responseData = new ResponseData();
        responseData.setMeta(new Meta(error, message));
        responseData.setData(null);
        return responseData;
    }
}
