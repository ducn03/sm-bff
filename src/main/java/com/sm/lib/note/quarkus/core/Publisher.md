# Publisher

## 1. Giải thích đơn giản

**Publisher** trong Quarkus giống như một cái loa phát thanh. 
Khi có một sự kiện xảy ra, **Publisher** sẽ "hét to" thông tin đó lên, và bất kỳ ai (Subscriber - người nghe) quan tâm sẽ nghe thấy và phản hồi lại nếu cần.

**Ví dụ:** 
- Khi mẹ nấu cơm xong, mẹ sẽ hét lên: “Cơm chín rồi!”. Nếu bạn đói, bạn sẽ nghe thấy và chạy vào ăn. 
Nếu bạn không đói, bạn sẽ bỏ qua thông báo đó.

## 2. Giải thích chuyên sâu

Trong **Quarkus, Publisher** là một thành phần trong reactive programming, thường được dùng để phát ra một dòng dữ liệu (stream) có thể được tiêu thụ bất đồng bộ bởi các **Subscriber**.

**Quarkus** hỗ trợ **reactive stream** theo chuẩn **Reactive Streams (org.reactivestreams.Publisher)**. 
Trong đó, **Mutiny** là một thư viện **reactive** giúp đơn giản hóa xử lý bất đồng bộ và được Quarkus tích hợp sẵn.

- **Uni:** Phát ra một giá trị duy nhất (hoặc lỗi).
- **Multi:** Phát ra nhiều giá trị theo thời gian (hoặc lỗi).
> **Ví dụ:** _Uni_ giống như một người đưa thư chỉ gửi một lá thư, còn _Multi_ giống như một dịch vụ tin nhắn, cứ có tin mới là gửi đến liên tục.

### Cách hoạt động
1. **Tạo một Publisher** để phát ra dữ liệu.
2. **Có thể biến đổi dữ liệu** trước khi gửi đi.
3. **Có một hoặc nhiều Subscriber lắng nghe** và xử lý dữ liệu đó.

## 3. Ví dụ đơn giản

**📢 Thông báo điểm thi**

- **Publisher**: Nhà trường thông báo điểm thi.
- **Subscriber**: Học sinh nghe thông báo và xem điểm của mình.
- Nếu bạn quan tâm (đợi kết quả), bạn sẽ nghe thấy.
- Nếu bạn không quan tâm (bỏ học rồi), bạn sẽ không để ý.

## 4. Ví dụ sâu về luồng

Hãy tưởng tượng bạn đang xây dựng một hệ thống giao đồ ăn online, 
nơi mà khi khách hàng đặt món, hệ thống sẽ cập nhật trạng thái đơn hàng theo từng bước:

- Nhận đơn hàng
- Nhà hàng bắt đầu chuẩn bị món ăn
- Shipper nhận đơn và đến lấy hàng
- Shipper đang giao hàng
- Giao hàng thành công

Chúng ta sẽ sử dụng **Multi (Publisher phát nhiều sự kiện)** để liên tục gửi cập nhật trạng thái đơn hàng cho khách hàng.

### Code triển khai trong Quarkus
#### 4.1. Dịch vụ xử lý đơn hàng
```java-code:
import io.smallrye.mutiny.Multi;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.Duration;
import java.util.List;

@ApplicationScoped
public class OrderService {

    public Multi<String> trackOrder(String orderId) {
        List<String> statusUpdates = List.of(
            "✅ Đơn hàng " + orderId + " đã được nhận",
            "🍳 Nhà hàng đang chuẩn bị món ăn",
            "🚗 Shipper đang đến lấy hàng",
            "🏍️ Shipper đang giao hàng",
            "🎉 Đơn hàng đã được giao thành công!"
        );

        return Multi.createFrom().iterable(statusUpdates)
    .onItem().delayIt().by(Duration.ofSeconds(2));
    }
}
```

**🔹 Giải thích:**
- **Multi.createFrom().iterable(statusUpdates):** Tạo một Publisher phát lần lượt từng trạng thái đơn hàng.
- **onItem().call(...):** Thêm delay 2 giây giữa các trạng thái để giả lập quá trình xử lý thực tế.

#### 4.2. API để theo dõi trạng thái đơn hàng
```java-code: 
import io.smallrye.mutiny.Multi;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/orders")
@Produces(MediaType.SERVER_SENT_EVENTS)  // Gửi dữ liệu theo sự kiện
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    @Inject
    OrderService orderService;

    @GET
    @Path("/{orderId}/track")
    public Multi<String> trackOrder(@PathParam("orderId") String orderId) {
        return orderService.trackOrder(orderId);
    }
}
```

**🔹 Giải thích:**
- **@Path("/{orderId}/track"):** API cho phép khách hàng theo dõi đơn hàng theo ID.
- **@Produces(MediaType.SERVER_SENT_EVENTS):** Sử dụng SSE (Server-Sent Events) để gửi dữ liệu theo luồng liên tục.
- **Multi<String>:** Dữ liệu sẽ được gửi theo từng phần, thay vì trả về một lần duy nhất.

#### 4.3. Gửi yêu cầu từ client

Nếu dùng **cURL**, bạn có thể thử như sau:
```bash
curl -N http://localhost:8080/orders/123/track
```

**🔹 Output sau từng giây:**
```bash
✅ Đơn hàng 123 đã được nhận
🍳 Nhà hàng đang chuẩn bị món ăn
🚗 Shipper đang đến lấy hàng
🏍️ Shipper đang giao hàng
🎉 Đơn hàng đã được giao thành công!
```

#### 4.4. Mô tả luồng hoạt động
- **Người dùng gọi API** `/orders/123/track`. 
- **OrderService** bắt đầu phát ra các trạng thái đơn hàng.
- **Client** nhận từng cập nhật theo thời gian thực qua **Server-Sent Events (SSE)**.
- Khi trạng thái cuối cùng của đơn hàng được gửi đi, luồng dữ liệu (Multi) sẽ hoàn thành và kết nối SSE sẽ tự động đóng.

> **👉 Tóm lại:**
>- **Publisher** là thành phần phát ra dữ liệu.
>- **Subscriber** lắng nghe và xử lý dữ liệu.
>- Quarkus dùng **Mutiny (Uni, Multi)** để quản lý các dòng dữ liệu bất đồng bộ.
>- Giống như loa phát thanh, người nghe có thể tiếp nhận hoặc bỏ qua thông tin.

> **Kết luận** <br>
>- 🚀 Với cách tiếp cận này, chúng ta có thể xây dựng một hệ thống giao tiếp bất đồng bộ mạnh mẽ và tối ưu:
>- ✅ Không cần polling liên tục từ client.
>- ✅ Tiết kiệm tài nguyên server do chỉ gửi dữ liệu khi có sự kiện mới.
>- ✅ Trải nghiệm người dùng mượt mà vì dữ liệu được cập nhật theo thời gian thực.
