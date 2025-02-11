# 1. 使用 JDK 17 作為基底映像
FROM openjdk:17-jdk-slim

# 2. 設定環境變數
ENV APP_HOME=/app
WORKDIR $APP_HOME

# 3. 複製打包後的 JAR
COPY target/ecommerce-0.0.1-SNAPSHOT.jar app.jar

# 4. 指定要使用的 application.yml
ENV SPRING_CONFIG_LOCATION=classpath:/application-docker.yml

# 5. 設定容器啟動時執行的指令
CMD ["java", "-jar", "app.jar"]