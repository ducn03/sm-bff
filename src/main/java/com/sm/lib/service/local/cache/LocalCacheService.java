package com.sm.lib.service.local.cache;

/**
 * LocalCacheService là bộ nhớ cache cục bộ (in-memory) trên server. <br>
 * 📌 **Ưu điểm:** <br>
 * ✅ Tốc độ truy xuất cực nhanh vì dữ liệu được lưu trực tiếp trong RAM. <br>
 * ✅ Không có độ trễ network như Redis vì chạy trên cùng một instance. <br>
 * ✅ Dễ triển khai, không cần thiết lập server cache riêng. <br>
 * ✅ Hữu ích khi caching dữ liệu tạm thời có tần suất truy cập cao. <br>
 *
 * ⚠ **Nhược điểm:** <br>
 * ❌ Không bền vững, mất dữ liệu khi server restart. <br>
 * ❌ Chỉ hoạt động trên một instance, không thể chia sẻ cache giữa nhiều server. <br>
 * ❌ Dễ bị tràn bộ nhớ nếu không quản lý TTL hợp lý. <br>
 */

public interface LocalCacheService {
    void set(String key, Object value, long ttl);
    <T> T get(String key, Class<T> type);
    void delete(String key);
}
