FROM openjdk:17-jdk-slim

# 設置工作目錄
WORKDIR /app

# 複製 JAR 檔案
COPY target/ecommerce-0.0.1-SNAPSHOT.jar app.jar

# 暴露應用程式的埠
EXPOSE 8080

# 啟動 Spring Boot 應用程式
ENTRYPOINT ["java", "-jar", "app.jar"]