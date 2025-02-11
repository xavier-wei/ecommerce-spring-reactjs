package com.gmail.merikbest2015.ecommerce;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class EcommerceApplication {

    public static void main(String[] args) {

        // 檢查 .env 是否存在
        File envFile = new File(".env");
        if (envFile.exists()) {
            Dotenv dotenv = Dotenv.load();
            System.setProperty("AWS_ACCESS_KEY", dotenv.get("AWS_ACCESS_KEY"));
            System.setProperty("AWS_SECRET_KEY", dotenv.get("AWS_SECRET_KEY"));
            System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
            System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
            System.setProperty("DB_USERNAME_DOCKER", dotenv.get("DB_USERNAME_DOCKER"));
            System.setProperty("DB_PASSWORD_DOCKER", dotenv.get("DB_PASSWORD_DOCKER"));
        } else {
            // 如果 .env 不存在，則使用環境變數
            System.setProperty("AWS_ACCESS_KEY", System.getenv("AWS_ACCESS_KEY"));
            System.setProperty("AWS_SECRET_KEY", System.getenv("AWS_SECRET_KEY"));
            System.setProperty("DB_USERNAME", System.getenv("DB_USERNAME"));
            System.setProperty("DB_PASSWORD", System.getenv("DB_PASSWORD"));
            System.setProperty("DB_USERNAME_DOCKER", System.getenv("DB_USERNAME_DOCKER"));
            System.setProperty("DB_PASSWORD_DOCKER", System.getenv("DB_PASSWORD_DOCKER"));
        }

        SpringApplication.run(EcommerceApplication.class, args);
    }

}
