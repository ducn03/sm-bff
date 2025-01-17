package com.sm.lib.service.controller;

import com.sm.lib.exception.AppException;
import com.sm.lib.exception.ErrorCodes;
import com.sm.lib.service.controller.dto.ResponseData;
import com.sm.lib.helper.JsonHelper;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import lombok.CustomLog;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.UUID;

@ApplicationScoped
@CustomLog
public class ControllerServiceImpl implements ControllerService {

    @Override
    public Uni<Response> success(Object data) {
        // Kiểm tra nếu data là Uni, nếu có thì đợi nó, nếu không thì trực tiếp trả về
        if (!(data instanceof Uni)) {
            log.info("response success:");
            log.info(JsonHelper.toJson(data));
            return Uni.createFrom().item(Response.ok(JsonHelper.toJson(ResponseData.success(data)))
                    .header("Content-Type", "application/json")
                    .build());
        }
        // Nếu data là Uni, xử lý theo cách của Uni
        return ((Uni<?>) data).onItem().transform(item -> {
            log.info("response success:");
            log.info(JsonHelper.toJson(item));
            return Response.ok(JsonHelper.toJson(ResponseData.success(item)))
                    .header("Content-Type", "application/json")
                    .build();
        })
        .onFailure().recoverWithUni(t -> {
            if (t instanceof AppException appException) {
                int errorCode = appException.getErrorCode();
                return error(errorCode, getMessage(errorCode));
            }
            log.error("Error message: ", t);
            return error(ErrorCodes.SYSTEM.SYSTEM_ERROR, "SYSTEM ERROR");
        });
    }

    public Uni<Response> error(int error, String message) {
        log.info(error + ": " + message);
        return Uni.createFrom().item(ResponseData.error(error, message))
                .onItem().transform(responseData -> {
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
        log.trace("response system error");
        return Uni.createFrom().item(Response.ok(JsonHelper.toJson(ResponseData.error(ErrorCodes.SYSTEM.SYSTEM_ERROR, "System Error")))
                .header("Content-Type", "application/json")
                .build());
    }

    public String getMessage(int errorCode) {
        try {
            Locale currentLocale = Locale.getDefault();
            ResourceBundle bundle = ResourceBundle.getBundle("message_vi", currentLocale);
            return bundle.getString(String.valueOf(errorCode));
        } catch (Exception e) {
            log.warn("Code " + errorCode + " is not yet registered");
            return "Message not available";
        }
    }
}
