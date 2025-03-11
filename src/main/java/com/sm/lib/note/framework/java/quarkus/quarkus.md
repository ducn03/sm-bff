# Danh sách đầy đủ các thành phần trong Quarkus Reactive

## 1. Reactive Core (Cốt lõi của Reactive)
| Thành phần                                       | Thuật ngữ chuyên ngành               | Mô tả                                                             |
|--------------------------------------------------|--------------------------------------|-------------------------------------------------------------------|
| [Mutiny](./core/Mutiny.md)                       | Reactive Programming Library         | Thư viện reactive chính của Quarkus, hỗ trợ Uni<T> và Multi<T>.   | 
| [Uni & Multi](./core/Uni-Multi.md)               | Reactive Types                       | Uni<T> xử lý một kết quả, Multi<T> xử lý nhiều kết quả (stream).  |
| [CompletionStage](./core/CompletionStage.md)     | Future-based Asynchronous Processing | API bất đồng bộ dựa trên Java CompletableFuture.                  |
| [Publisher](./core/Publisher.md)                 | Reactive Streams Specification       | Định nghĩa cách xử lý dữ liệu stream theo chuẩn Reactive Streams. |
| [Multi<T>](./core/MultiT.md)                     | Vert.x                               | Hệ thống event loop giúp Quarkus vận hành non-blocking.           |


## 2. Reactive Database (Xử lý Database bất đồng bộ)
| Thành phần             | Thuật ngữ chuyên ngành              | Mô tả                                                                       |
|------------------------|-------------------------------------|-----------------------------------------------------------------------------|
| Hibernate Reactive     | Non-blocking ORM                    | Hibernate chạy theo mô hình non-blocking, hỗ trợ reactive query.            |
| Panache Reactive       | Reactive ORM Abstraction            | Đơn giản hóa thao tác với Hibernate Reactive, tối ưu syntax CRUD.           |
| Reactive SQL Clients   | Non-blocking Database Access        | Kết nối cơ sở dữ liệu PostgreSQL, MySQL theo mô hình non-blocking.          |
| Reactive Transactions  | Transactional Support for Reactive  | Hỗ trợ transaction trong Reactive, không giống như Hibernate truyền thống.  |

# 3. Reactive HTTP & REST (Xử lý request HTTP bất đồng bộ)
| Thành phần          | Thuật ngữ chuyên ngành            | Mô tả                                                   |
|---------------------|-----------------------------------|---------------------------------------------------------|
| Reactive RESTEasy   | Reactive HTTP Handling            | Xử lý REST API theo mô hình non-blocking.               |
| Reactive Routes     | Programmatic HTTP Routing         | Định nghĩa route API bằng code thay vì annotation.      |
| Quarkus WebSockets  | Reactive WebSocket Communication  | Hỗ trợ giao tiếp WebSocket theo mô hình event-driven.   |


## 4. Reactive Messaging (Giao tiếp dữ liệu theo mô hình Event-Driven)
| Thành phần                   | Thuật ngữ chuyên ngành       | Mô tả                                                             |
|------------------------------|------------------------------|-------------------------------------------------------------------|
| Reactive Messaging           | Asynchronous Caching         | Hỗ trợ giao tiếp với Kafka, AMQP, MQTT theo mô hình event-driven. |
| SmallRye Reactive Messaging  | Non-blocking Job Scheduling  | Xử lý stream dữ liệu lớn bằng cách tích hợp Kafka, RabbitMQ.      |


## 5. Reactive Caching & Scheduling (Lập lịch & Caching bất đồng bộ)
| Thành phần         | Thuật ngữ chuyên ngành        | Mô tả                                                             |
|--------------------|-------------------------------|-------------------------------------------------------------------|
| Quarkus Cache      | Event-Driven Messaging System | Hỗ trợ giao tiếp với Kafka, AMQP, MQTT theo mô hình event-driven. |
| Quarkus Scheduler  | Streaming Event Processing    | Xử lý stream dữ liệu lớn bằng cách tích hợp Kafka, RabbitMQ.      |

## 6. Reactive Security (Bảo mật trong Quarkus Reactive)
| Thành phần             | Thuật ngữ chuyên ngành        | Mô tả                                                 |
|------------------------|-------------------------------|-------------------------------------------------------|
| Quarkus Security       | Reactive Authentication       | Xử lý xác thực người dùng theo mô hình Reactive.      |
| OAuth2 & JWT Security  | Reactive Identity Management  | Hỗ trợ OAuth2, OpenID Connect và JWT để bảo mật API.  |


