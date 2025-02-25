# Event Queue (Hàng đợi sự kiện - tùy chọn)

## 1. Giải thích đơn giản
**Event Queue** giống như một "bảng ghi order" trong quán cà phê. 
Khi có nhiều khách hàng gọi đồ cùng lúc (nhiều sự kiện xảy ra gần nhau), order của họ sẽ được ghi vào bảng theo thứ tự đến (FIFO - First-In, First-Out). 
Người phục vụ (Event Loop) sẽ lấy order từ bảng theo thứ tự và phục vụ từng order một.

**Event Queue** là một hàng đợi, nơi các sự kiện đã xảy ra nhưng **chưa được xử lý** sẽ được xếp hàng chờ đợi.

## 2. Chuyên sâu

**Event Queue** (hay còn gọi là **Message Queue**) là một cấu trúc dữ liệu **hàng đợi (Queue)**, thường là **FIFO (First-In, First-Out)**, được sử dụng để lưu trữ các **sự kiện** đã xảy ra nhưng **chưa được xử lý** bởi **Event Loop**.

## 3. Vai trò của Event Queue
- **Đảm bảo thứ tự xử lý sự kiện:** Các sự kiện được xử lý theo thứ tự mà chúng xảy ra (FIFO).
- **Xử lý đồng thời nhiều sự kiện:** Khi nhiều sự kiện xảy ra gần như đồng thời (ví dụ: nhiều click chuột liên tiếp, nhiều dữ liệu đến từ server), Event Queue giúp quản lý các sự kiện này và đảm bảo chúng được xử lý lần lượt.
- **Tách biệt quá trình phát sinh sự kiện và xử lý sự kiện:** Các sự kiện có thể được phát sinh từ nhiều nguồn khác nhau (người dùng, hệ thống, mạng), và Event Queue đóng vai trò trung gian, đảm bảo các sự kiện này được xử lý một cách có tổ chức bởi Event Loop.

## 4. Ví dụ
- **Hàng đợi thanh toán tại siêu thị:** Khi có nhiều người cùng đến thanh toán (nhiều sự kiện "mua hàng" xảy ra), họ sẽ xếp hàng đợi (Event Queue). Nhân viên thu ngân (Event Loop) sẽ phục vụ từng người theo thứ tự hàng đợi.
- **Danh sách việc cần làm (To-do list):** Bạn ghi các việc cần làm vào danh sách (Event Queue). Khi bạn có thời gian, bạn sẽ lấy từng việc từ đầu danh sách (Event Loop) và thực hiện.
- **Hàng đợi cuộc gọi hỗ trợ khách hàng:** Khi có nhiều người gọi điện đến tổng đài hỗ trợ (nhiều sự kiện "yêu cầu hỗ trợ" xảy ra), cuộc gọi của họ sẽ được xếp vào hàng đợi (Event Queue). 
Nhân viên hỗ trợ (Event Loop) sẽ trả lời từng cuộc gọi theo thứ tự hàng đợi.