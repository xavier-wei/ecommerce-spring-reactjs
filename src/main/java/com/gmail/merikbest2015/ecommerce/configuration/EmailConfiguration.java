package com.gmail.merikbest2015.ecommerce.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.util.Properties;

@Configuration
public class EmailConfiguration {

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.protocol:smtp}")
    private String protocol; // 默認為 SMTP

    @Value("${spring.mail.properties.mail.smtp.auth:true}")
    private boolean auth; // 使用 boolean 而不是 String

    @Value("${spring.mail.properties.mail.smtp.starttls.enable:true}")
    private boolean enableStartTls; // 使用 boolean

    @Value("${spring.mail.debug:false}")
    private boolean debug; // 使用 boolean

    @Bean
    @Primary
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties mailProperties = mailSender.getJavaMailProperties();
        mailProperties.setProperty("mail.transport.protocol", protocol);
        mailProperties.setProperty("mail.smtp.auth", String.valueOf(auth));
        mailProperties.setProperty("mail.smtp.starttls.enable", String.valueOf(enableStartTls));
        mailProperties.setProperty("mail.debug", String.valueOf(debug));

        return mailSender;
    }

    @Bean
    public ITemplateResolver thymeleafTemplateResolver() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("mail-templates/"); // 確保路徑正確
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine thymeleafTemplateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(thymeleafTemplateResolver());
        return templateEngine;
    }
}
