# Các lỗi phổ biến và cách tránh trong quarkus reactive

## 1. Lỗi gọi delete hay deleteById nhưng data trong db chưa được xóa:

- Không đăng ký Uni từ delete():<br>
delete() không tự động thực thi nếu bạn không kết hợp nó vào pipeline.

- Gọi flush() không đồng bộ:<br>
Sử dụng flush() không nằm trong reactive pipeline sẽ không đảm bảo tính đồng bộ và gây ra lỗi.

- Xử lý lỗi không đầy đủ:<br>
Mọi thao tác trong reactive flow cần có xử lý lỗi rõ ràng bằng recoverWithItem.

> **_example:_**
```java-code
public Uni<Boolean> deleteById(Long id) {
    return userRepository.deleteById(id)
    .onItem().transformToUni(user -> {
    return userRepository.flush().onItem().transform(flushed -> true);
    }).onFailure().recoverWithItem(false);
}
```


