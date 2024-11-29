# Hướng dẫn cấu hình yml trong quarkus (Chỉ dành cho reactive)

## example:
### Cấu hình server
```server-config
quarkus:
  http:
    port: ${COM_SM_SERVER_BFF_PORT:5000}
    test-port: ${COM_SM_SERVER_BFF_TEST_PORT:5000}
    host: ${COM_SM_SERVER_BFF_HOST:localhost}
  profile:
    - dev
```

### Cấu hình ở file dev
```dev-config
"%dev":
  quarkus:
    redis:
      hosts:
        - redis://${COM_SM_REDIS_HOST:localhost}:${COM_HC_REDIS_PORT:6379}/${COM_SM_SERVER_REDIS_DATABASE:1}
      password: ${COM_SM_SERVER_REDIS_PASSWORD:}
    datasource:
      db-kind: ${COM_SM_DATABASE_DB_KIND:mysql}
      reactive:
        url: ${COM_SM_DATABASE_URL:mysql://localhost:3307/socialmedia}
        driver: ${COM_SM_DATABASE_DRIVER:com.mysql.cj.jdbc.Driver}
      username: ${COM_SM_DATABASE_USERNAME:root}
      password: ${COM_SM_DATABASE_PASSWORD:root}
      devservices:
        enabled: false
    hibernate-orm:
      database:
        generation: ${COM_HC_DATABASE_GENERATION:none}
      log:
        sql: true
        format-sql: true
    log:
      console:
        enable: true
      level: INFO
```