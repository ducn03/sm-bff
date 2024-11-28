package com.sm.lib.helper;

import io.smallrye.mutiny.Uni;

public class UniHelper {
    public static <T> Uni<T> toObject(Uni<T> uni, Class<T> clazz) {
        return uni.onItem().transform(item -> {
            // Kiểm tra kiểu dữ liệu nếu cần hoặc thực hiện các thao tác thêm trước khi trả về
            if (item != null) {

            }
            return item;
        });
    }
    // Phương thức chuyển từ T thành Uni<T>
    public static <T> Uni<T> fromObject(T object) {
        return Uni.createFrom().item(object);
    }

    // Phương thức giữ lại Uni mà không chuyển đổi sang đồng bộ
    public static <T> Uni<T> toUni(Uni<T> uni) {
        return uni; // Trả về Uni mà không thay đổi
    }
}
