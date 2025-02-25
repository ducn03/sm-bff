# Event Handler (Bộ xử lý sự kiện)
## 1. Giải thích đơn giản

**Event Handler** giống như một "hướng dẫn" cho người phục vụ (Event Loop) biết phải làm gì khi có một loại "order" cụ thể (sự kiện) từ khách hàng. 

Ví dụ:
- **Sự kiện:** Khách hàng gọi "cà phê sữa đá".
- **Event Handler:** Hướng dẫn pha chế "cà phê sữa đá" và mang ra cho khách.

**Event Handler** là một đoạn mã (thường là một hàm) được viết để phản ứng lại một sự kiện cụ thể.

## 2. Chuyên sâu
**Event Handler** (hay còn gọi là **Event Listener, Callback function** trong ngữ cảnh sự kiện) là một hàm hoặc một đoạn mã được liên kết với một **sự kiện cụ thể**. 
Khi sự kiện đó xảy ra, **Event Loop** sẽ gọi **Event Handler** tương ứng để xử lý sự kiện.

## 3. Các loại sự kiện phổ biến
- Sự kiện người dùng (User Events):
  - click, mouseover, mouseout, keydown, keyup, submit, change (trên trình duyệt web)
  - Sự kiện chạm, cử chỉ (trên thiết bị di động)

- Sự kiện hệ thống (System Events):
  - load, DOMContentLoaded (trang web tải xong)
  - error (lỗi xảy ra)
  - timeout (hết thời gian chờ)
  - data, end (dữ liệu đến từ server)

- Sự kiện tùy chỉnh (Custom Events): Sự kiện do lập trình viên tự định nghĩa và phát ra.

## 4. Vai trò của Event Handler
- **Xử lý sự kiện:** Thực hiện các hành động cần thiết khi một sự kiện xảy ra (ví dụ: cập nhật giao diện, gửi dữ liệu lên server, hiển thị thông báo).
- **Tạo tính tương tác:** Giúp ứng dụng phản ứng lại các hành động của người dùng và các sự kiện hệ thống, làm cho ứng dụng trở nên động và tương tác.

## 5. Ví dụ
- Chuông cửa nhà bạn:
  - **Sự kiện:** Ai đó nhấn chuông cửa.
  - **Event Handler:** Bạn ra mở cửa.

- Công tắc đèn:
  - **Sự kiện:** Bạn bật công tắc đèn.
  - **Event Handler:** Đèn sáng lên.

- Nút "Like" trên Facebook:
  - **Sự kiện:** Bạn click vào nút "Like".
  - **Event Handler:** Số lượng "Likes" tăng lên, nút "Like" có thể thay đổi màu sắc, thông báo "Bạn đã thích bài viết này".