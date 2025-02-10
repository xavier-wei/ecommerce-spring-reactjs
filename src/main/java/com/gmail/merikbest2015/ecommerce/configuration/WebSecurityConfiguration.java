package com.gmail.merikbest2015.ecommerce.configuration;

import com.gmail.merikbest2015.ecommerce.security.oauth2.CustomOAuth2UserService;
import com.gmail.merikbest2015.ecommerce.security.JwtConfigurer;
import com.gmail.merikbest2015.ecommerce.security.oauth2.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration {

    private final JwtConfigurer jwtConfigurer;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final CustomOAuth2UserService oAuth2UserService;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // 暫時禁用 CSRF
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // 啟用 CORS 配置
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/v1/auth/**",
                                "/api/v1/auth/login",
                                "/api/v1/registration/**",
                                "/api/v1/perfumes/**",
                                "/api/v1/users/**",
                                "/api/v1/order/**",
                                "/api/v1/review/**",
                                "/api/v1/admin/**",
                                "/websocket", "/websocket/**",
                                "/img/**",
                                "/static/**",
                                "/auth/**", "/oauth2/**",
                                "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll() // 公開 API 路徑
                        .anyRequest().authenticated() // 其他請求需要驗證
                );
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:3000"); // 替換為 hostname 或其他允許的來源
        configuration.addAllowedMethod("HEAD");
        configuration.addAllowedMethod("OPTIONS");
        configuration.addAllowedMethod("GET");
        configuration.addAllowedMethod("POST");
        configuration.addAllowedMethod("PUT");
        configuration.addAllowedMethod("PATCH");
        configuration.addAllowedMethod("DELETE");
        configuration.addAllowedHeader("*"); // 允許的請求頭
        configuration.addExposedHeader("page-total-count"); // 暴露的響應頭
        configuration.addExposedHeader("page-total-elements"); // 暴露的響應頭
        configuration.setAllowCredentials(true); // 是否允許攜帶憑證 (Cookie)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 匹配所有路徑
        return source;
    }
}
