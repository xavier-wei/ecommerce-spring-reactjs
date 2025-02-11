package com.gmail.merikbest2015.ecommerce.mapper;

import com.gmail.merikbest2015.ecommerce.domain.User;
import com.gmail.merikbest2015.ecommerce.dto.PasswordResetRequest;
import com.gmail.merikbest2015.ecommerce.dto.RegistrationRequest;
import com.gmail.merikbest2015.ecommerce.dto.auth.AuthenticationRequest;
import com.gmail.merikbest2015.ecommerce.dto.auth.AuthenticationResponse;
import com.gmail.merikbest2015.ecommerce.dto.user.UserResponse;
import com.gmail.merikbest2015.ecommerce.exception.InputFieldException;
import com.gmail.merikbest2015.ecommerce.service.AuthenticationService;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AuthenticationMapper {

    private final AuthenticationService authenticationService;
    private final CommonMapper commonMapper;

    public AuthenticationResponse login(AuthenticationRequest request) {
        Map<String, Object> credentials = authenticationService.login(request.getEmail(), request.getPassword());
        AuthenticationResponse response = new AuthenticationResponse();
        response.setUser(commonMapper.convertToResponse(credentials.get("user"), UserResponse.class));
        response.setToken((String) credentials.get("token"));
        return response;
    }

    public String getEmailByPasswordResetCode(String code) {
        return authenticationService.getEmailByPasswordResetCode(code);
    }

    public String registerUser(String captcha, RegistrationRequest registrationRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InputFieldException(bindingResult);
        }
        User user = commonMapper.convertToEntity(registrationRequest, User.class);
        return authenticationService.registerUser(user, captcha, registrationRequest.getPassword2());
    }

    public String activateUser(String code) {
        return authenticationService.activateUser(code);
    }

    public String sendPasswordResetCode(String email) {
        return authenticationService.sendPasswordResetCode(email);
    }

    public String passwordReset(String email, PasswordResetRequest passwordReset) {
        return authenticationService.passwordReset(email, passwordReset.getPassword(), passwordReset.getPassword2());
    }

    public String passwordReset(String email, PasswordResetRequest passwordReset, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InputFieldException(bindingResult);
        } else {
            return authenticationService.passwordReset(email, passwordReset.getPassword(), passwordReset.getPassword2());
        }
    }


//    public static void main(String[] args) {
//        PasswordEncoder encoder = new BCryptPasswordEncoder();
//
//        // 模擬用戶輸入的密碼
//        String rawPassword = "admin123";
//
//        // 測試加密和匹配
//        String encodedPassword = encoder.encode(rawPassword);
//        System.out.println("Encoded password: " + encodedPassword);
//
//        // 使用 matches 方法檢查是否匹配
//        boolean isMatch = encoder.matches(rawPassword, encodedPassword);
//        System.out.println("Password match: " + isMatch);
//    }

//    public static void main(String[] args) {
//        SecretKey key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256); // 生成符合 HS256 的密鑰
//        String base64Key = Base64.getEncoder().encodeToString(key.getEncoded());
//        System.out.println("Generated Key (Base64): " + base64Key);
//    }

    public static void main(String[] args) {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String encodedPassword = encoder.encode("admin123");
        System.out.println(encodedPassword);
    }
}
