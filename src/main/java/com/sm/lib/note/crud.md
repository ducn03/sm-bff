# Hướng dẫn sử dụng crud trong quarkus reactive
# Những điểm cần lưu ý
# Các lỗi hay gặp phải

**Quarkus Reactive** cung cấp cách tiếp cận **non-blocking** để xây dựng ứng dụng,
tận dụng các thư viện như **Mutiny** để xử lý **Reactive Streams**.
Dưới đây là hướng dẫn xây dựng **CRUD**
trong **Quarkus Reactive** với các điểm cần lưu ý và cách giải quyết các lỗi thường gặp.

## 1. CRUD cơ bản trong Quarkus Reactive
### 1.1 Lấy tất cả người dùng
**Mô tả**: Sử dụng findAll() từ repository Reactive để trả về danh sách người dùng.
```java-code:
public Uni<List<User>> getAll() {
    return userRepository.findAll().list();
}
```
> **_Lưu ý:_**
Kết quả trả về là Uni<List<User>>.
Đảm bảo rằng class User đã được annotate đúng với JPA Entity.

### 1.2 Thêm người dùng mới
**Mô tả**: Thêm bản ghi (record) mới vào database và flush ngay lập tức.
```java-code: 
public Uni<User> create(User user) {
    return userRepository.persistAndFlush(user);
}
```
> **_Lưu ý:_**
Dùng persistAndFlush để đảm bảo dữ liệu được ghi xuống database ngay lập tức.
Kiểm tra kỹ các trường hợp lỗi (ví dụ: vi phạm ràng buộc).

### 1.3 Cập nhật người dùng
**Mô tả**: Tìm bản ghi (record) theo ID, cập nhật dữ liệu và lưu lại.
```java-code:
public Uni<User> update(Long id, User updatedUser) {
    return userRepository.findById(id)
            .onItem().transformToUni(user -> {
                setUserData(user, updatedUser);
                return userRepository.persistAndFlush(user).replaceWith(user);
            });
}

private void setUserData(User user, User updatedUser) {
    user.setUsername(updatedUser.getUsername());
    user.setPassword(updatedUser.getPassword());
    user.setPhone(updatedUser.getPhone());
    user.setEmail(updatedUser.getEmail());
    user.setFullname(updatedUser.getFullname());
}
```
> **_Lưu ý:_**
Kiểm tra null để đảm bảo bản ghi (record) tồn tại.
Đảm bảo các phương thức setter trong entity User đầy đủ.

### 1.4 Xóa người dùng
**Mô tả**: Xóa một bản ghi bằng ID.
```java-code:
public Uni<Boolean> delete(Long id) {
    return userRepository.findById(id)
            .onItem().transformToUni(user -> {
                if (user == null) {
                    return Uni.createFrom().item(false);
                }
                return userRepository.delete(user)
                        .onItem().transformToUni(deleted -> userRepository.flush().onItem().transform(flushed -> true));
            });
}
```
> **_Lưu ý:_**
Kiểm tra null trước khi gọi delete.
Đảm bảo gọi flush() để ghi lại thay đổi.

### 1.5 Xóa bằng ID
**Mô tả**: Xóa bản ghi (record) bằng ID trực tiếp từ repository.
```java-code
public Uni<Boolean> deleteById(Long id) {
    return userRepository.deleteById(id)
            .onItem().transformToUni(deleted -> userRepository.flush().onItem().transform(flushed -> true))
            .onFailure().recoverWithItem(false);
}
```
> **_Lưu ý:_**
deleteById sẽ trả về false nếu không tìm thấy bản ghi (record).

### 2. Những điểm cần lưu ý
- Reactive Pipeline:
```
Luôn sử dụng Uni hoặc Multi cho các thao tác Reactive.
Sử dụng transform và transformToUni để xử lý logic liên tiếp.
```

- Transaction:
```
Đảm bảo các thao tác liên quan đến database được thực hiện trong transaction nếu cần.
```

- Entity Validation:
```
Sử dụng Hibernate Validator hoặc @Valid để đảm bảo dữ liệu hợp lệ trước khi lưu.
```

- Error Handling:
```
Luôn sử dụng onFailure() để xử lý lỗi và phục hồi nếu cần.
```

> **_Ví dụ:_**
``` java-code
.onFailure().recoverWithItem(false);
```

- Performance:
``` java-code:
Sử dụng batch operations nếu cần xử lý nhiều bản ghi (record).
Tối ưu hóa các truy vấn phức tạp bằng cách dùng Native Query.
```

### 3. Các lỗi thường gặp và cách khắc phục
- Lỗi NullPointerException
**Nguyên nhân**: Bản ghi không tồn tại nhưng không kiểm tra trước khi thao tác.
**Cách sửa:**
```java-code:
if (user == null) {
    return Uni.createFrom().item(false);
}
```
**or**
```java-code
if (user == null) {
    AppThrown.ep(ErrorCodes.SYSTEM.BAD_REQUEST);   
}
```

- Lỗi LazyInitializationException <br>
**Nguyên nhân:** Fetch dữ liệu với Lazy nhưng không mở transaction.<br>
**Cách sửa:** Dùng @Fetch(FetchMode.JOIN) hoặc chuyển sang EAGER nếu phù hợp.

- Lỗi Database Locking <br>
**Nguyên nhân**: Quá nhiều thao tác ghi cùng lúc. <br>
**Cách sửa:**
```note-db-locking
Sử dụng transaction.
Giảm số lượng thao tác đồng thời.
```

- Lỗi không lưu dữ liệu sau khi gọi delete <br>
**Nguyên nhân**: Không gọi flush() sau khi xóa. <br>
**Cách sửa**: Đảm bảo gọi flush():
```java-code
return userRepository.flush().onItem().transform(flushed -> true);
```
