package com.gmail.merikbest2015.ecommerce;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EcommerceApplication {

    public static void main(String[] args) {

        // 加載 .env 文件
        Dotenv dotenv = Dotenv.load();

        // 確保環境變數被加載
        System.setProperty("AWS_ACCESS_KEY", dotenv.get("AWS_ACCESS_KEY"));
        System.setProperty("AWS_SECRET_KEY", dotenv.get("AWS_SECRET_KEY"));
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
        System.out.println("AWS_ACCESS_KEY: " + System.getProperty("AWS_ACCESS_KEY"));
        System.out.println("AWS_SECRET_KEY: " + System.getProperty("AWS_SECRET_KEY"));
        System.out.println("DB_USERNAME: " + System.getProperty("DB_USERNAME"));
        System.out.println("DB_PASSWORD: " + System.getProperty("DB_PASSWORD"));
        SpringApplication.run(EcommerceApplication.class, args);
    }

}
