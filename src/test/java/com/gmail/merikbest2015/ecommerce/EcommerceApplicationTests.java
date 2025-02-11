package com.gmail.merikbest2015.ecommerce;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class EcommerceApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void main() {
        EcommerceApplication.main(new String[] {});
    }
}
