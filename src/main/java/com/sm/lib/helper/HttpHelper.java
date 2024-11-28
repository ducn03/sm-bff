package com.sm.lib.helper;

import com.sm.lib.dto.UserInfo;
import com.sm.lib.exception.ErrorCodes;
import com.sm.lib.utils.AppThrower;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class HttpHelper {
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
                AppThrower.ep(ErrorCodes.SYSTEM.BAD_REQUEST);
            }

            // Chuyển JsonObject thành đối tượng theo kiểu Class<T>
            return jsonObject.mapTo(clazz);
        } catch (Exception e) {
            AppThrower.ep(ErrorCodes.SYSTEM.BAD_REQUEST);
            return null; // Chỉ để làm hài lòng compiler
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
            AppThrower.ep(ErrorCodes.SYSTEM.BAD_REQUEST);
        }
        return (UserInfo) userInfo;
    }

    /**
     * Lấy danh sách số từ query param.
     *
     * @param context  RoutingContext chứa thông tin query
     * @param paramName Tên của query parameter
     * @return Danh sách số
     */
    public static List<Long> getListNumberFromQueryParam(RoutingContext context, String paramName) {
        List<String> queryParams = context.queryParam(paramName);
        if (queryParams == null) {
            return Collections.emptyList();
        }
        return queryParams.stream()
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }
}
