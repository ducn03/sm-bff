package com.sm.lib.helper;

import com.sm.lib.dto.UserInfo;
import com.sm.lib.exception.ErrorCodes;
import com.sm.lib.utils.AppThrower;
import com.sm.lib.utils.StringUtils;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import lombok.CustomLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@CustomLog
public class HttpHelper {
    private static final Logger logger = LoggerFactory.getLogger(HttpHelper.class);
    /**
     * Lấy body của request và chuyển đổi sang một đối tượng cụ thể.
     *
     * @param context RoutingContext chứa request
     * @param clazz   Lớp cần chuyển đổi
     * @param <T>     Kiểu của đối tượng
     * @return Đối tượng được chuyển đổi
     */
    public static <T> T body(RoutingContext context, Class<T> clazz) {
        try {
            // Lấy body dưới dạng JsonObject
            JsonObject jsonObject = context.body().asJsonObject();
            if (jsonObject == null) {
                return null;
            }

            // Chuyển JsonObject thành đối tượng theo kiểu Class<T>
            return jsonObject.mapTo(clazz);
        } catch (Exception e) {
            logger.warn("Exception error: " + e.getMessage());
            return null;
        }
    }

    public static String getHeaderAuthorization(RoutingContext context) {
        return getHeader(context, "Authorization");
    }

    public static String getHeaderLang(RoutingContext context) {
        return getHeader(context, "hc-lang");
    }

    public static String getHeader(RoutingContext context, String headerName) {
        try {
            String token = context.request().getHeader(headerName);
            if (StringUtils.isNullOrEmpty(token)) {
                return null;
            }
            return token;
        } catch (Exception e) {
            logger.warn("Exception error: " + e.getMessage());
            return null;
        }
    }


    /**
     * Lấy thông tin UserInfo từ RoutingContext.
     *
     * @param context RoutingContext chứa thông tin user
     * @return Đối tượng UserInfo
     */
    public static UserInfo getUserInfo(RoutingContext context) {
        Object userInfo = context.get("user-info");
        if (userInfo == null) {
            return null;
        }
        return (UserInfo) userInfo;
    }
}
