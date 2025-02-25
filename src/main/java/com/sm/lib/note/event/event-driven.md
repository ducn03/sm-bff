# Event-Driven là gì?
## 1. Giải thích đơn giản
Tưởng tượng bạn đang chơi trò chơi xếp hình. 
**Event-driven** giống như việc bạn phản ứng lại với những gì xảy ra trong trò chơi.

- **Ví dụ:**
- - **Sự kiện (Event):** Bạn nhặt được một mảnh ghép mới.
- - **Phản ứng (Driven):** Bạn suy nghĩ xem mảnh ghép này khớp với chỗ nào trên hình và đặt nó vào.

Thay vì cứ phải nghĩ "Bây giờ mình phải làm gì?", 
bạn chỉ cần chờ đợi **sự kiện** xảy ra (nhặt được mảnh ghép) và sau đó **phản ứng** lại (tìm chỗ đặt).

> **_Nói tóm lại:_**
Event-driven là kiểu hoạt động mà mọi thứ diễn ra khi có "sự kiện" kích hoạt, 
chứ không phải cứ chạy theo thứ tự từ trên xuống dưới.

## 2. Chuyên sâu
Event-driven programming (Lập trình hướng sự kiện) là một mô hình lập trình mà luồng chương trình được quyết định bởi các sự kiện (events) 
- ví dụ như hành động của người dùng (click chuột, nhấn phím), tin nhắn từ hệ thống, hoặc kết quả từ các cảm biến. 
Thay vì chương trình chạy theo một trình tự cố định, nó sẽ chờ đợi và phản ứng lại các sự kiện khi chúng xảy ra.


## 3. Các thành phần chính của mô hình Event-Driven

- **Sự kiện (Event):** Một tín hiệu hoặc thông báo cho biết một điều gì đó đã xảy ra. 
  - **Ví dụ:**
    - **Input từ người dùng:** Click chuột, nhấn phím, nhập liệu.
    - **Sự kiện hệ thống:** Hoàn thành tải dữ liệu, lỗi mạng, hết thời gian chờ.
    - **Tin nhắn từ các thành phần khác:** Thông báo trạng thái, yêu cầu xử lý.

- **Event Loop (Vòng lặp sự kiện):** Một cơ chế liên tục lắng nghe và chờ đợi các sự kiện xảy ra. 
Khi một sự kiện được phát hiện, Event Loop sẽ chuyển nó đến Event Handler tương ứng.

- **Event Handler (Bộ xử lý sự kiện):** Một đoạn mã (thường là một hàm hoặc phương thức) được liên kết với một loại sự kiện cụ thể. 
Khi một sự kiện xảy ra và được Event Loop chuyển đến, Event Handler sẽ được thực thi để xử lý sự kiện đó.

- **Event Queue (Hàng đợi sự kiện - tùy chọn):** Trong một số hệ thống, các sự kiện có thể được đưa vào một hàng đợi để xử lý theo thứ tự, đặc biệt khi có nhiều sự kiện xảy ra đồng thời.

## 4. So sánh với mô hình tuần tự (Procedural)

Trong lập trình tuần tự, chương trình thực hiện các lệnh theo thứ tự đã được định sẵn từ trên xuống dưới. 
Luồng chương trình được kiểm soát bởi chính mã chương trình.

Trong lập trình event-driven, luồng chương trình không được kiểm soát trực tiếp bởi mã chương trình mà phản ứng lại các sự kiện từ bên ngoài hoặc từ hệ thống.

## 5. Ưu điểm của Event-Driven

**Tính phản hồi cao (Responsiveness):** Chương trình phản ứng nhanh chóng với các tương tác của người dùng và sự kiện từ hệ thống.

**Tính tương tác cao (Interactivity):** Rất phù hợp cho các ứng dụng giao diện người dùng (GUI), trò chơi, ứng dụng web thời gian thực.

**Tính linh hoạt (Flexibility):** Dễ dàng thêm hoặc thay đổi hành vi của chương trình bằng cách thay đổi hoặc thêm Event Handler.

**Khả năng mở rộng (Scalability):** Đặc biệt trong các hệ thống server, event-driven giúp xử lý đồng thời nhiều kết nối và sự kiện một cách hiệu quả (ví dụ: Node.js).


# 6. Nhược điểm

**Khó theo dõi luồng chương trình:** Do luồng chương trình phụ thuộc vào sự kiện, việc theo dõi và gỡ lỗi có thể phức tạp hơn so với lập trình tuần tự.

**Quản lý trạng thái:** Cần quản lý trạng thái chương trình một cách cẩn thận giữa các sự kiện.

# 7. Ví dụ

- **Điện thoại di động:**
  - **Sự kiện:** Bạn chạm vào màn hình, nhận được tin nhắn, có cuộc gọi đến, pin yếu.
  - **Phản ứng:** Màn hình cảm ứng phản hồi, điện thoại rung/chuông, hiển thị thông báo, cảnh báo pin yếu. Điện thoại của bạn không "chạy" liên tục để kiểm tra xem bạn có chạm vào màn hình hay không, mà nó "chờ đợi" sự kiện chạm màn hình xảy ra.

- **Nồi cơm điện:**
  - **Sự kiện:** Bạn nhấn nút " nấu cơm".
  - **Phản ứng:** Nồi cơm bắt đầu quá trình nấu, khi cơm chín (sự kiện khác), nồi tự động chuyển sang chế độ giữ ấm.

- **Ngân hàng tự động ATM:**
  - **Sự kiện:** Bạn đút thẻ vào, chọn chức năng, nhập PIN, chọn số tiền rút.
  - **Phản ứng:** ATM hiển thị menu, yêu cầu nhập PIN, xác thực PIN, hiển thị số dư, nhả tiền. 
  ATM "chờ đợi" bạn thực hiện hành động (sự kiện) và sau đó phản ứng lại tương ứng.

- **Website/Ứng dụng Web:**
  - **Sự kiện:** Người dùng click vào nút, nhập liệu vào form, cuộn trang.
  - **Phản ứng:** Website/ứng dụng web xử lý yêu cầu, cập nhật giao diện, gửi dữ liệu lên server. Trang web không "chạy" liên tục để kiểm tra xem bạn có click nút hay không, mà nó "lắng nghe" sự kiện click chuột.


# 8. Luồng

> [Người dùng click nút] 
> --> [Sự kiện "click" được tạo] 
> --> [Event Queue] 
> --> [Event Loop lấy sự kiện] 
> --> [Event Handler "click" thực thi] 
> --> [Hiển thị "Đang xử lý...", Gửi request server] 
> --> (KHÔNG CHỜ ĐỢI) --> [Server xử lý xong, gửi phản hồi] 
> --> [Sự kiện "phản hồi" được tạo] 
> --> [Event Queue] --> [Event Loop lấy sự kiện] 
> --> [Event Handler "phản hồi" thực thi]
> --> [Ẩn "Đang xử lý...", Hiển thị "Xử lý thành công!"]

# 9. Điểm lưu ý

**Bất đồng bộ (Asynchronous):** Chương trình không bị "đứng" chờ đợi các hoạt động mất thời gian (như gửi request server). 
Nó tiếp tục xử lý các sự kiện khác trong khi chờ đợi.

**Event Loop là trung tâm:** Event Loop là trái tim của mô hình event-driven, nó quản lý và điều phối luồng sự kiện.


