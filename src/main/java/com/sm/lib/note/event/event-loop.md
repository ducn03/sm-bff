# Event Loop (Vòng lặp sự kiện)
## 1. Giải thích đơn giản

Hãy tưởng tượng bạn là một người phục vụ trong một quán cà phê. Công việc của bạn là liên tục:

- **Nghe ngóng:** Xem có khách hàng nào gọi đồ uống không (sự kiện xảy ra).
- **Phục vụ:** Nếu có, bạn ghi order, đưa cho pha chế và mang đồ uống ra cho khách (xử lý sự kiện).
- **Lặp lại:** Bạn cứ làm như vậy liên tục, vòng đi vòng lại.

**Event Loop** cũng tương tự như người phục vụ đó trong thế giới lập trình. 
Nó là một vòng lặp vô tận, liên tục kiểm tra xem có "sự kiện" nào xảy ra hay không. 
Nếu có sự kiện, nó sẽ "xử lý" sự kiện đó và sau đó lại tiếp tục vòng lặp, chờ sự kiện tiếp theo.

## 2. Chuyên sâu

**Event Loop** là một cơ chế lập trình **bất đồng bộ (asynchronous)**, 
thường được sử dụng trong các môi trường **đơn luồng (single-threaded)** như JavaScript (trong trình duyệt và Node.js), 
Python (với thư viện asyncio), và một số ngôn ngữ GUI. Nó cho phép chương trình thực hiện các tác vụ **không chặn (non-blocking)**, 
nghĩa là chương trình không cần phải chờ đợi một tác vụ hoàn thành trước khi tiếp tục thực hiện các tác vụ khác.

## 3. Cơ chế hoạt động

**Call Stack (Ngăn xếp lệnh gọi):** Nơi thực thi các hàm đồng bộ. 
Khi một hàm được gọi, nó được **đẩy** vào Call Stack và thực thi. 
Khi hàm **thực thi** xong, nó được **lấy** ra khỏi Call Stack.

**Event Queue (Hàng đợi sự kiện):** Nơi các sự kiện đã xảy ra được xếp hàng đợi
> ví dụ: click chuột, hoàn thành tải dữ liệu, hết thời gian chờ.

**Event Loop:**
- **Kiểm tra Call Stack:** Event Loop liên tục kiểm tra xem Call Stack có rỗng không.
- **Kiểm tra Event Queue:** Nếu Call Stack rỗng (tức là không có hàm đồng bộ nào đang thực thi), Event Loop sẽ lấy sự kiện đầu tiên trong Event Queue.
- **Đưa sự kiện vào Call Stack:** Event Loop lấy sự kiện từ Event Queue và đưa hàm xử lý sự kiện (Event Handler) tương ứng vào Call Stack để thực thi.
- **Lặp lại:** Quá trình này lặp lại liên tục.

4. Ưu điểm của Event Loop

**Hiệu suất:** Cho phép chương trình phản hồi nhanh chóng với các sự kiện, 
không bị "đứng hình" khi chờ các tác vụ dài hạn.
> **ví dụ:** tải dữ liệu từ server

**Tính tương tác:** Đặc biệt quan trọng trong giao diện người dùng (GUI) và ứng dụng web, 
nơi cần xử lý nhiều sự kiện người dùng một cách mượt mà.

**Đơn giản hóa lập trình bất đồng bộ:** Cung cấp một mô hình lập trình tương đối dễ hiểu và quản lý so với lập trình đa luồng trong một số trường hợp.

> __Ví dụ:__
> **Quán cà phê (ví dụ trên):** Người phục vụ (Event Loop) liên tục kiểm tra xem có khách gọi đồ (sự kiện) hay không. <br><br>
> **Hộp thư đến email:** Bạn (Event Loop) liên tục kiểm tra hộp thư đến (Event Queue) xem có email mới (sự kiện) không. 
Nếu có, bạn mở email và đọc (xử lý sự kiện). <br><br>
> **Bàn phím và chuột máy tính:** Hệ điều hành (Event Loop) liên tục theo dõi các thao tác trên bàn phím và chuột (sự kiện). Khi bạn nhấn phím hoặc click chuột, hệ điều hành sẽ gửi sự kiện đến ứng dụng đang chạy để xử lý.
