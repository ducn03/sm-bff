# Các lỗi phổ biến và cách tránh trong quarkus reactive

## 1. Lỗi gọi delete hay deleteById nhưng data trong db chưa được xóa:

- Không đăng ký Uni từ delete():<br>
delete() không tự động thực thi nếu bạn không kết hợp nó vào pipeline.

- Gọi flush() không đồng bộ:<br>
Sử dụng flush() không nằm trong reactive pipeline sẽ không đảm bảo tính đồng bộ và gây ra lỗi.

- Xử lý lỗi không đầy đủ:<br>
Mọi thao tác trong reactive flow cần có xử lý lỗi rõ ràng bằng recoverWithItem.

> **_example:_**
```java-code
public Uni<Boolean> deleteById(Long id) {
    return userRepository.deleteById(id)
    .onItem().transformToUni(user -> {
    return userRepository.flush().onItem().transform(flushed -> true);
    }).onFailure().recoverWithItem(false);
}
```

## 2. Khi làm việc với module, lỗi không inject được service của module này qua module cần được inject
> **_Truy cập vào link để lấy bản cập nhật mới_**
```dbn-psql
https://quarkus.io/guides/cdi-reference#bean_discovery
```
> **_Đặt code trong file pom.xml của modue phụ thuộc_**
```example
Ví dụ: module A inject service của module B
 thì đặt code trong file pom.xml của module B
```
```dbn-psql
<build>
  <plugins>
    <plugin>
      <groupId>io.smallrye</groupId>
      <artifactId>jandex-maven-plugin</artifactId>
      <version>3.2.3</version>
      <executions>
        <execution>
          <id>make-index</id>
          <goals>
            <goal>jandex</goal>
          </goals>
        </execution>
      </executions>
    </plugin>
  </plugins>
</build>
```


