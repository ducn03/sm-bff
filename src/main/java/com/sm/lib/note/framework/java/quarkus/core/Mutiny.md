# Mutiny

## 1. Giải thích đơn giản
Hãy tưởng tượng bạn đang ở trong một quán trà sữa 🍹:

- **Cách truyền thống (Blocking):** Bạn đặt hàng, đứng đợi nhân viên làm xong, sau đó mới nhận trà sữa và rời đi.
- **Cách Reactive (Mutiny):** Bạn đặt hàng, nhận số thứ tự, đi làm việc khác, khi trà sữa xong nhân viên sẽ gọi bạn ra lấy.

> 📌 Mutiny giúp ứng dụng của bạn không phải đứng chờ kết quả từ database hay API, mà có thể tiếp tục làm việc khác và phản hồi khi dữ liệu sẵn sàng.



## 2. Chuyên sâu
### 2.1 Mutiny là gì?

Mutiny là một **Reactive Programming Library** dành riêng cho **Quarkus**, 
giúp xử lý tác vụ **bất đồng bộ** (non-blocking) với API đơn giản, dễ sử dụng hơn so với các thư viện reactive khác như RxJava hay Project Reactor.

### 2.2 📌 Mục tiêu của Mutiny

- ✅ Viết code reactive dễ đọc hơn, không bị callback hell.
- ✅ Giúp ứng dụng xử lý nhiều request cùng lúc mà không bị nghẽn CPU.
- ✅ Hỗ trợ Lazy Execution: Chỉ chạy khi có subscriber.
- ✅ Tích hợp tốt với Quarkus, Vert.x, Hibernate Reactive, Kafka, REST Client,...

### 3. Các thành phần chính của Mutiny
## 3.1 Mutiny có 2 loại đối tượng chính

| Đối tượng | Chức năng                                                   | Ví dụ                                                                |
|-----------|-------------------------------------------------------------|----------------------------------------------------------------------|
| Uni<T>    | Đại diện cho một giá trị duy nhất trong tương lai.          | Khi bạn gọi API lấy dữ liệu từ DB, bạn nhận được một Uni<User>.      |
| Multi<T>  | Đại diện cho một luồng dữ liệu liên tục, giống như Stream.  | Khi bạn lắng nghe dữ liệu từ Kafka, bạn nhận được một Multi<Event>.  |

## 3.2 Ví dụ
**_Uni<T>_** 🥤: Pha một ly trà sữa

> Một khách đặt trà sữa → Bạn làm trà sữa → Khi xong bạn đưa cho họ → Xong! <br>
**Tương đương:** API gọi database và nhận một kết quả duy nhất.


**_Multi<T>_** 🎶: Danh sách phát nhạc (Playlist)

> Bạn là DJ, bật một danh sách bài hát → Nhạc phát liên tục → Người nghe thưởng thức từng bài một. <br>
**Tương đương:** Streaming dữ liệu từ Kafka hoặc WebSocket, nhận nhiều kết quả theo thời gian.

📌 Sự khác biệt lớn nhất:

- Uni<T> = Trà sữa, khi pha xong thì kết thúc.
- Multi<T> = Playlist nhạc, dữ liệu cứ tiếp tục stream.




