package com.sm.lib.service.controller;

import com.sm.lib.exception.AppException;
import com.sm.lib.exception.ErrorCodes;
import com.sm.lib.service.controller.dto.ResponseData;
import com.sm.lib.helper.JsonHelper;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import lombok.CustomLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.UUID;

@ApplicationScoped
@CustomLog
public class ControllerServiceImpl implements ControllerService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Override
    public Uni<Response> success(Object dataUni) {
        return ((Uni<?>) dataUni)
                .map(this::buildSuccessResponse)
                .onFailure().recoverWithUni(this::handleFailure);
    }

    @Override
    public Uni<Response> success(Object dataUni, Object paginationUni) {
        return ((Uni<?>) dataUni).flatMap(data ->
                ((Uni<?>) paginationUni).map(pagination -> buildSuccessResponse(data, pagination))
        ).onFailure().recoverWithUni(this::handleFailure);
    }

    private Response buildSuccessResponse(Object data) {
        ResponseData responseData = ResponseData.success(data);
        setResponseMeta(responseData);

        logger.info("Response: {}", JsonHelper.toJson(responseData.getData()));
        return Response.ok(JsonHelper.toJson(responseData))
                .header("Content-Type", "application/json")
                .build();
    }

    private Response buildSuccessResponse(Object data, Object pagination) {
        ResponseData responseData = ResponseData.success(data, pagination);
        setResponseMeta(responseData);

        logger.info("Response: {}", JsonHelper.toJson(responseData.getData()));
        return Response.ok(JsonHelper.toJson(responseData))
                .header("Content-Type", "application/json")
                .build();
    }

    private void setResponseMeta(ResponseData responseData) {
        responseData.getMeta().setRequestId(getRequestId());
        responseData.getMeta().setResponseId(generateResponseId());
    }

    private Uni<Response> handleFailure(Throwable t) {
        if (t instanceof AppException appException) {
            int errorCode = appException.getErrorCode();
            return error(errorCode, getMessage(errorCode));
        }
        logger.error("Error message: ", t);
        return systemError();
    }



    @Override
    public Uni<Response> error(int error, String message) {
        return Uni.createFrom().item(ResponseData.error(error, message))
                .onItem().transform(responseData -> {
                    responseData.getMeta().setRequestId(getRequestId());
                    responseData.getMeta().setResponseId(generateResponseId());

                    Response.ResponseBuilder builder = switch (error) {
                        case ErrorCodes.SYSTEM.UNAUTHORIZED -> Response.status(Response.Status.UNAUTHORIZED);
                        case ErrorCodes.SYSTEM.BAD_REQUEST -> Response.status(Response.Status.BAD_REQUEST);
                        case ErrorCodes.SYSTEM.PAGE_NOT_FOUND -> Response.status(Response.Status.NOT_FOUND);
                        case ErrorCodes.SYSTEM.FORBIDDEN -> Response.status(Response.Status.FORBIDDEN);
                        case ErrorCodes.SYSTEM.SYSTEM_ERROR -> Response.status(Response.Status.INTERNAL_SERVER_ERROR);
                        case ErrorCodes.SYSTEM.BAD_GATEWAY -> Response.status(Response.Status.BAD_GATEWAY);
                        default -> Response.ok();
                    };
                    return builder.entity(JsonHelper.toJson(responseData))
                            .header("Content-Type", "application/json")
                            .build();
                });
    }


    @Override
    public Uni<Response> systemError() {
        return error(ErrorCodes.SYSTEM.SYSTEM_ERROR, "SYSTEM ERROR");
    }

    private String getRequestId() {
        return UUID.randomUUID().toString();
    }

    private String generateResponseId() {
        return UUID.randomUUID().toString();
    }

    public String getMessage(int errorCode) {
        try {
            Locale currentLocale = Locale.getDefault();
            ResourceBundle bundle = ResourceBundle.getBundle("message_vi", currentLocale);
            return bundle.getString(String.valueOf(errorCode));
        } catch (Exception e) {
            logger.warn("Code " + errorCode + " is not yet registered");
            return "Message not available";
        }
    }
}
