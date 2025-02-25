## 0. Giới Thiệu - Quarkus Reactive là gì?
**Quarkus Reactive** là kiểu lập trình "càng nhanh càng tốt" - tất cả đều **non-blocking**
(không chờ đợi). Nếu bạn từng đợi ly cà phê ở quán đông khách, bạn sẽ thấy sự khác biệt:
- **Blocking**: Bạn đứng đợi tới lượt pha cà phê. Chậm, chán, mất thời gian! ⏳
- **Non-blocking**: Bạn order, lấy số, đi tám chuyện, lát sau quay lại lấy cà phê. Tối ưu thời gian! 🚀

**Quarkus Reactive** cũng vậy! Nó tận dụng **Mutiny** để xử lý **Reactive Streams**,
giúp hệ thống chạy mượt mà mà không bị "kẹt" như quán cà phê chật chội. 😆

## 0.1 Nguyên Lý Hoạt Động Của Quarkus Reactive
### 0.1.1 Reactive là gì?
- **Reactive** là mô hình lập trình **non-blocking**, nơi mọi thứ diễn ra bất đồng bộ.
- **Ví dụ thực tế**: Khi đặt đồ ăn qua app:
- - **Blocking**: Bạn gọi món, đợi bếp nấu xong rồi mới làm việc khác. ⏳
- - **Non-blocking**: Bạn gọi món, tiếp tục xem phim, đến khi xong hệ thống báo cho bạn. 🎬
### 0.1.2 Cách Quarkus xử lý request
**Quarkus Reactive** tận dụng **Vert.x**, **Mutiny** và **Hibernate Reactive** để xử lý **request** theo mô hình **event-driven**:
- 1. Người dùng gửi **request** (ví dụ: GET /users).
- 2. **Quarkus** nhận **request**, nhưng thay vì **"đứng đợi"** database phản hồi, nó sẽ chuyển sang xử lý tác vụ khác.
- 3. Database **trả** kết quả -> Quarkus gửi **phản hồi** về cho người dùng ngay lập tức.
- 4. Mọi thứ diễn ra bất đồng bộ! 🚀
> **_Tóm gọn:_**
Quarkus giống như một anh bartender siêu nhanh, không đứng đợi ly trà sữa mà luôn chuyển sang làm việc khác!
