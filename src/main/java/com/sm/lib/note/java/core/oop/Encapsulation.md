# Encapsulation (Đóng gói)

## 1. Khái niệm đơn giản
**Encapsulation** là tính đóng gói. Những **properties** và **methods** liên quan tới cùng một đối tượng thì được gói vào thành một **class**.
Nó thể hiện qua từ khóa **class**.

## 2. Encapsulation và Data Hiding
- **Encapsulation** là tính đóng gói dữ liệu.
- **Private** thể hiện tính **Data Hiding** (ẩn dữ liệu).
- **Data Hiding** là một tính chất phụ giúp Encapsulation hoàn hảo hơn.
- Tuy nhiên, **private không thuộc về một tính chất nào riêng trong OOP** mà chỉ là một công cụ hỗ trợ.

## 3. Mục đích
Encapsulation giúp **gói nhóm các tính chất liên quan thành một đối tượng**.  
Có **hai cách tiếp cận** khi thiết kế đối tượng:
1. **Gom các tính chất thành đối tượng**.
2. **Xác định đối tượng trước, sau đó tìm tính chất phù hợp**.

## 4. Vai trò của Encapsulation trong OOP
- **Tính đóng gói giúp lập trình viên tư duy để tạo nên một đối tượng.**
- **Đối tượng là đơn vị cốt lõi trong lập trình hướng đối tượng (OOP), đặc biệt là trong Java.**

## 5. Ví dụ thực tế
### Ví dụ: Chiếc điều khiển TV 🖥️
Hãy tưởng tượng bạn có một chiếc điều khiển TV. Bạn chỉ cần **nhấn nút** để bật/tắt TV, tăng/giảm âm lượng mà không cần biết bên trong nó hoạt động thế nào.

Trong lập trình, chiếc điều khiển chính là một **class**, các nút bấm là **methods**, và cách hoạt động bên trong là **private data** được ẩn đi.

```java
class RemoteControl {
    private boolean isOn = false;

    public void pressPowerButton() {
        isOn = !isOn;
        System.out.println("TV " + (isOn ? "bật" : "tắt"));
    }
}

public class Main {
    public static void main(String[] args) {
        RemoteControl remote = new RemoteControl();
        remote.pressPowerButton(); // TV bật
        remote.pressPowerButton(); // TV tắt
    }
}
```
📌 **Kết luận:** Người dùng chỉ có thể điều khiển TV qua `pressPowerButton()`, không thể trực tiếp thay đổi trạng thái `isOn`, giúp bảo vệ dữ liệu và đảm bảo tính nhất quán.

