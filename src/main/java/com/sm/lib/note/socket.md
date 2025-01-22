> Client kết nối đến /webSocket endpoint.

> SocketConnector nhận kết nối và gọi onOpen trong SocketListener (thông qua SocketManager).

> SocketServer nhận thông báo kết nối, lưu session.

> Khi client gửi tin nhắn, SocketConnector gọi onMessage.

> SocketServer xử lý tin nhắn (ví dụ: gửi trả tin nhắn).

> Khi có thay đổi ở giỏ hàng, SocketServer dùng Redis để lấy danh sách các client trong socket room, tạo message (thông qua NotifyMessage), convert message sang JSON, và gửi thông báo đến các client đó.

> Client nhận và xử lý tin nhắn.

> Client ngắt kết nối, SocketConnector gọi onClose, SocketServer xóa session.