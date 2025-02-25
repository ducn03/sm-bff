# Uni và Multi trong Mutiny
### 0.2.1 Uni và Multi trong Mutiny
Mutiny cung cấp hai loại đối tượng chính để xử lý lập trình bất đồng bộ trong Quarkus:

| Cú pháp     | Mô tả                                                        |
|-------------|--------------------------------------------------------------|
| Uni<T>      | Trả về một giá trị duy nhất (ví dụ: lấy thông tin một user). |
| Multi<T>    | Trả về nhiều giá trị (ví dụ: stream dữ liệu từ database).    |

### 0.2.2 Uni<T> - Một-và-Chỉ-Một (One-and-Only Dish)
#### 0.2.2.1 Giải thích đơn giản.
**Uni<T>** giống như bạn order một món **duy nhất** trong nhà hàng.
Bạn chỉ nhận được **một** kết quả (hoặc là món ăn, hoặc là... báo hết món!).
Nó đại diện cho một **hoạt động bất đồng bộ** mà bạn mong đợi chỉ trả về **một giá trị** thành công, hoặc một lỗi nếu có vấn đề xảy ra.

#### 0.2.2.2 Ví dụ:

- **Gọi điện thoại cho crush:** Bạn gọi điện cho crush (hoạt động bất đồng bộ - bạn phải chờ đợi). Bạn chỉ mong đợi một kết quả:
  - **Thành công:** Crush bắt máy và "say hello" (giá trị duy nhất!).
  - **Thất bại:** Thuê bao quý khách vừa gọi hiện không liên lạc được (lỗi).
  - **Không có chuyện "hơi hơi thành công" hay "thành công nhiều lần" ở đây**. Chỉ có một kết quả cuối cùng.


- **Xin sếp tăng lương:** Bạn trình bày với sếp (hoạt động bất đồng bộ - hồi hộp chờ đợi).
  Kết quả chỉ có một:
  - **Thành công:** Sếp duyệt tăng lương (giá trị duy nhất - mức lương mới!).
  - **Thất bại:** Sếp từ chối thẳng thừng (lỗi - "ngân sách năm nay có hạn").
  - **Không có chuyện "tăng lương từ từ" hay "tăng lương nhiều đợt"**. Quyết định là cuối cùng.


- **Bốc thăm trúng thưởng:** Bạn mua vé số (hy vọng bất đồng bộ - chờ đợi kết quả xổ số).
  Bạn chỉ có thể trúng một giải đặc biệt (nếu may mắn):
  - **Thành công:** Trúng giải độc đắc (giá trị duy nhất - giải thưởng!).
  - **Thất bại:** "Chúc bạn may mắn lần sau" (không có giá trị, coi như lỗi "đen đủi").

#### 0.2.2.3 Chuyên sâu

- _Uni_ trong Mutiny tuân theo nguyên tắc **Reactive Streams**, nhưng được tối ưu hóa cho trường hợp một giá trị duy nhất.


- Nó tương tự như _CompletableFuture_ trong Java hoặc _Promise_ trong JavaScript, nhưng được tích hợp sâu hơn vào hệ sinh thái Reactive của Mutiny.


- _Uni_ rất hữu ích cho các thao tác **lấy dữ liệu đơn lẻ, gọi API, thực thi một tác vụ và chờ kết quả cuối cùng.**


- Xử lý lỗi trong _Uni_ rất quan trọng. Bạn có thể sử dụng các toán tử như _onFailure(), recoverWithItem(), orElse()_ để xử lý các trường hợp lỗi một cách linh hoạt.

> **__Tóm tắt về Uni:__** <br>
> - **Một và chỉ một:** Luôn trả về tối đa một giá trị.
> - **Hoạt động đơn lẻ:** Phù hợp cho các tác vụ trả về một kết quả duy nhất.
> - **Thành công hoặc thất bại:** Kết thúc bằng một giá trị thành công hoặc một lỗi.
> - **Ví dụ:** Lấy thông tin người dùng, gọi API trả về một đối tượng, lưu một bản ghi vào database (khi chỉ quan tâm đến việc thành công/thất bại).

> **__Tổng kết__** <br>
> Uni<T> trong Mutiny là một Reactive Stream Publisher (nhà phát hành luồng phản ứng) được thiết kế đặc biệt để đại diện cho một kết quả bất đồng bộ duy nhất. 
> Nó hứa hẹn sẽ phát ra tối đa một item (giá trị) kiểu T, hoặc một tín hiệu lỗi (failure), hoặc tín hiệu hoàn thành (completion) mà không có giá trị nào.

### 0.2.3 Multi<T> - Buffet Dữ Liệu (Data Buffet) - "Đại Tiệc Buffet Dữ Liệu" - All-You-Can-Eat Data Stream!
#### 0.2.3.1 Giải thích đơn giản

Hãy tưởng tượng bạn có một **ống nước. Multi** giống như cái ống nước đó.

- **Nước** (dữ liệu) chảy ra từ ống **từng giọt, từng dòng, hoặc liên tục.**
- Bạn có thể **hứng nước** (xử lý dữ liệu) khi nó chảy ra.
- **Ống nước có thể chảy mãi** (stream dữ liệu vô tận), hoặc **chảy hết nước rồi thôi** (stream dữ liệu hữu hạn).

> _Multi_ đơn giản
> - là một cách để xử lý **dữ liệu chảy liên tục**, không phải chỉ một cục duy nhất.
> - là cách để bạn làm việc với **dữ liệu chảy liên tục** như vậy trong lập trình.

#### 0.2.3.2 Ví dụ

- **Live stream game trên Twitch/YouTube:** Bạn xem live stream game (dữ liệu video và âm thanh liên tục).
  - **Dòng stream:** Hình ảnh, âm thanh, chat của người xem, thông báo donate... tuôn trào liên tục.
  - **Không giới hạn:** Stream có thể kéo dài vài tiếng, thậm chí cả ngày.
  - **Có thể "lag":** Mạng yếu, stream bị giật (lỗi giữa dòng stream).
  - **Kết thúc:** Streamer tắt live, dòng stream kết thúc.


- **Đọc truyện tranh online "cuộn trang vô tận":** Bạn đọc truyện tranh trên web (hình ảnh truyện tải dần dần khi bạn cuộn xuống).
  - **Dòng trang:** Trang 1, trang 2, trang 3... tải xuống liên tục khi bạn cuộn.
  - **Vô tận (gần như):** Truyện có thể rất dài, bạn cuộn mãi không hết trang.
  - **Có thể "lỗi hình":** Hình ảnh bị lỗi, không tải được (lỗi trong dòng stream trang truyện).
  - **Kết thúc (tạm thời):** Bạn đọc hết truyện, hoặc đóng trình duyệt, dòng trang truyện kết thúc.

#### 0.2.3.3 Chuyên sâu

**Multi<T> trong Mutiny** là một **Reactive Stream Publisher** (nhà phát hành luồng phản ứng) đại diện cho một **luồng dữ liệu bất đồng bộ** có thể phát ra **không giới hạn số lượng item** (giá trị) kiểu T.

#### 0.2.3.4 Các đặc điểm cốt lõi của Multi

- **Dòng dữ liệu (Data Stream):** Multi không chỉ là một giá trị đơn lẻ, mà là một **chuỗi các giá trị** theo thời gian.
  Các giá trị này có thể đến **liên tục** hoặc **theo từng đợt**.


- **Bất đồng bộ (Asynchronous):** Việc phát ra và xử lý dữ liệu trong Multi diễn ra **không đồng bộ**.
  Điều này có nghĩa là chương trình không cần phải chờ đợi một giá trị được phát ra hoặc xử lý xong trước khi tiếp tục các công việc khác.
- Nó cho phép thực hiện các tác vụ **song song** và **không chặn luồng chính**.


- **Nhiều giá trị (Multiple Items):** Khác với Uni (chỉ một giá trị), Multi được thiết kế để xử lý **nhiều giá trị** trong một luồng.
  Số lượng giá trị có thể từ 0 đến vô hạn.


- **Hoàn thành và Lỗi (Completion and Error):** Một luồng Multi có thể kết thúc theo hai cách:

  - **Hoàn thành (Completion):** Luồng phát ra tất cả các giá trị (nếu có) và sau đó kết thúc thành công.
    Không có giá trị nào được phát ra sau khi hoàn thành.
  - **Lỗi (Error):** Trong quá trình phát luồng, có thể xảy ra lỗi.
    Khi lỗi xảy ra, luồng sẽ kết thúc và thông báo lỗi cho subscriber (người đăng ký nhận dữ liệu).
    Không có giá trị nào được phát ra sau khi lỗi xảy ra.


- **Backpressure (Áp lực ngược):** Đây là một tính năng quan trọng của Reactive Streams, và Multi cũng hỗ trợ nó.
  **Backpressure** là cơ chế cho phép **subscriber (người nhận dữ liệu)** thông báo cho **publisher (người phát dữ liệu)** biết tốc độ mà nó có thể xử lý dữ liệu.
  Nếu subscriber bị quá tải, nó có thể yêu cầu publisher **chậm lại** hoặc **ngừng gửi dữ liệu** để tránh tràn bộ nhớ hoặc giảm hiệu suất.
  Điều này đặc biệt quan trọng khi xử lý các luồng dữ liệu lớn hoặc từ các nguồn chậm.

#### 0.2.3.5 Khi nào nên sử dụng Multi

- **Xử lý luồng dữ liệu liên tục:** Ví dụ: dữ liệu cảm biến, log hệ thống, sự kiện người dùng, thông tin thị trường chứng khoán real-time.


- **Stream dữ liệu từ nguồn dữ liệu:** Ví dụ: đọc dữ liệu từ database theo stream (cursor-based pagination), đọc file lớn theo từng dòng, nhận dữ liệu từ API dạng stream (Server-Sent Events, WebSockets).


- **Xây dựng hệ thống phản ứng sự kiện (Event-Driven):** Ví dụ: xử lý sự kiện từ message queue (Kafka, RabbitMQ), sự kiện từ hệ thống, sự kiện người dùng trên giao diện.


- **Cập nhật giao diện người dùng theo thời gian thực:** Ví dụ: hiển thị tiến trình tải file, biểu đồ real-time, thông báo cập nhật liên tục.

#### 0.2.3.6 Ví dụ

Hãy tưởng tượng một nhà máy sản xuất kẹo, có một dây chuyền sản xuất kẹo.

- **Nguyên liệu** (dữ liệu) được đưa vào dây chuyền.
- **Dây chuyền** (Multi) liên tục tạo ra **từng viên kẹo** (giá trị) và **đưa ra ngoài**.
- **Công nhân** (subscriber) đứng ở cuối dây chuyền để **đóng gói kẹo** (xử lý dữ liệu).
- **Dây chuyền có thể chạy liên tục** (stream dữ liệu vô tận - nếu có đủ nguyên liệu), hoặc chạy hết nguyên liệu rồi dừng (stream dữ liệu hữu hạn).
- Nếu dây chuyền bị **kẹt** hoặc **hỏng** (lỗi), việc sản xuất kẹo sẽ dừng lại.
- Nếu công nhân **đóng gói không kịp** (subscriber xử lý chậm), dây chuyền có thể chậm lại (backpressure) để tránh kẹo bị tràn ra.
