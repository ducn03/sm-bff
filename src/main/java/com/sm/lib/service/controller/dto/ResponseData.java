package com.sm.lib.service.controller.dto;

import com.sm.lib.exception.ErrorCodes;
import lombok.Data;

import java.util.UUID;

@Data
public class ResponseData {

    private Meta meta;
    private Object data;

    public static ResponseData success(Object data) {
        return buildResponseData(data, null);
    }

    public static ResponseData success(Object data, Object pagination) {
        return buildResponseData(data, pagination);
    }

    private static ResponseData buildResponseData(Object data, Object pagination) {
        ResponseData responseData = new ResponseData();
        Meta meta = new Meta();
        meta.setReturnCode(getSuccessCode());
        meta.setMessage(getSuccessMessage());
        responseData.setMeta(meta);
        responseData.setData(data);

        // Trả data phân trang nếu có
        if (pagination != null) {
            responseData.getMeta().setPagination(pagination);
        }

        return responseData;
    }


    public static ResponseData error(int code, String message) {
        ResponseData responseData = new ResponseData();
        Meta meta = new Meta();
        meta.setReturnCode(code);
        meta.setMessage(message);
        responseData.setMeta(meta);
        responseData.setData(null);
        return responseData;
    }

    private static String getSuccessMessage(){
        return "Success";
    }

    /**
     * HTTP 200 OK
     */
    private static int getSuccessCode(){
        return 200;
    }
}
