# CompletionStage: Lập Trình Bất Đồng Bộ Trong Java

## 1. Giải thích đơn giản

**Hãy tưởng tượng bạn đang nấu ăn 🍳:**

- **Cách truyền thống (Blocking):** Bạn đợi nồi nước sôi rồi mới bắt đầu luộc rau, sau đó mới rán trứng. ⏳

- **Cách CompletionStage (Non-blocking):** Bạn bật bếp, trong khi nước sôi, bạn tranh thủ cắt rau, chuẩn bị trứng, rồi làm tất cả cùng lúc. 🔥

> 📌 CompletionStage giúp xử lý nhiều tác vụ song song mà không cần chờ đợi tác vụ trước hoàn thành.

## 2. Chuyên sâu

### 2.1 CompletionStage là gì?

**_CompletionStage<T>_** là một **interface** trong Java xử lý tác vụ bất đồng bộ mà không cần chờ đợi.

**📌 Đặc điểm chính của CompletionStage:**

- **Không chặn (Non-blocking):** Thực hiện các công việc mà không cần chờ đợi.

- **Xử lý chaining (chuỗi hành động):** Tự động xử lý kết quả khi một tác vụ hoàn thành.

- **Hỗ trợ xử lý lỗi:** Có thể tiếp tục chạy ngay cả khi có lỗi xảy ra.

### 2.2 Cách hoạt động

**CompletionStage** có thể chạy các tác vụ theo nhiều cách:

- **thenApply(Function<T, R>)** → Nhận kết quả và trả về giá trị mới.


- **thenCompose(Function<T, CompletionStage<R>>)** → Kết hợp nhiều CompletionStage.


- **handle(BiFunction<T, Throwable, R>)** → Xử lý lỗi nếu có.


- **exceptionally(Function<Throwable, T>)** → Bắt lỗi và cung cấp giá trị mặc định.


## 2.3 Ví dụ

**🎬 Hãy tưởng tượng bạn là đạo diễn của một bộ phim! 🎥**

- **thenApply()** = Khi quay xong cảnh đầu tiên, biên tập viên chỉnh màu cho nó.


- **thenCompose()** = Khi biên tập xong, gửi video cho chuyên gia âm thanh để thêm nhạc.


- **handle()** = Nếu cảnh quay bị lỗi, bạn sẽ quyết định quay lại hoặc sửa lỗi trong hậu kỳ.


- **exceptionally()** = Nếu diễn viên chính nghỉ, bạn phải tìm diễn viên thay thế ngay lập tức.

> 📌 **Tóm lại:** _CompletionStage_ giúp xử lý công việc theo từng giai đoạn mà không cần đợi toàn bộ quá trình hoàn thành trước.

## 2.4 Ví dụ chuyên sâu về luồng (Flow trong lập trình CompletionStage)

**🚀 Xử lý API bất đồng bộ với CompletionStage**

**📌 Bài toán:**
- Bạn có một API /user/{id} lấy thông tin người dùng từ database.
- Bạn sẽ dùng CompletionStage để tối ưu hiệu suất thay vì cách blocking thông thường.

**✅ Code sử dụng CompletionStage trong Quarkus:**
```java-code:
@GET
@Path("/user/{id}")
public CompletionStage<Response> getUser(@PathParam("id") Long id) {
    return userRepository.findByIdAsync(id) // Trả về CompletionStage<User>
        .thenApply(user -> {
            log.info("Dữ liệu nhận được: " + user);
            return user;
        })
        .thenCompose(user -> {
            if (user == null) {
                return CompletableFuture.completedFuture(Response.status(Response.Status.NOT_FOUND).build());
            }
            return CompletableFuture.completedFuture(Response.ok(user).build());
        })
        .handle((response, throwable) -> {
            if (throwable != null) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Lỗi hệ thống").build();
            }
            return response;
        })
        .exceptionally(ex -> Response.status(Response.Status.SERVICE_UNAVAILABLE).entity("Lỗi không xác định").build());
}
```

**🔍 Giải thích:**

1. **thenApply()**: Nhận kết quả từ database và log dữ liệu.
2. **thenCompose():** Nếu có dữ liệu, trả về Response.ok(user) dưới dạng **CompletionStage<Response>**.
3. **handle():** Nếu có lỗi trong quá trình xử lý, trả về lỗi **500 INTERNAL SERVER ERROR**.
4. **exceptionally():** Nếu xảy ra lỗi không mong muốn, trả về **503 SERVICE UNAVAILABLE**.

> 📌 **Tổng kết:** Khi nào dùng CompletionStage?
> 
| Use case                | Tại sao nên dùng CompletionStage?                                          |                                                                
|-------------------------|----------------------------------------------------------------------------|
| Gọi API từ DB           | Tránh chặn request, giúp ứng dụng phản hồi nhanh hơn.                      |
| Gọi nhiều API song song | Chạy các API đồng thời, không phải đợi từng cái hoàn thành.                |
| Xử lý Streaming         | Từng phần dữ liệu được xử lý khi sẵn sàng, không cần chờ toàn bộ dữ liệu.  |


