package com.kusitms29.backendH.domain.user.application.service;

import com.kusitms29.backendH.domain.user.application.controller.dto.response.EmailVerificationResponseDto;
import com.kusitms29.backendH.domain.user.domain.User;
import com.kusitms29.backendH.domain.user.repository.UserRepository;
import com.kusitms29.backendH.global.error.exception.ConflictException;
import com.kusitms29.backendH.global.error.exception.InvalidValueException;
import com.kusitms29.backendH.infra.config.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;

import static com.kusitms29.backendH.global.error.ErrorCode.DUPLICATE_USER;
import static com.kusitms29.backendH.global.error.ErrorCode.INVALID_AUTH_CODE;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EmailVerificationService {
    private static final String AUTH_CODE_PREFIX = "AuthCode ";
    private final UserRepository userRepository;
    private final RedisService redisService;

    @Value("${mail.auth-code-expiration-millis}")
    private String authCodeExpirationMills;
    private final EmailService emailService;

    public void sendCodeToEmail(String toEmail) {
        this.checkDuplicatedEmail(toEmail);
        String title = "Sync 이메일 인증 번호";
        String authCode = this.createCode();
        emailService.sendEmail(toEmail, title, authCode);
        redisService.setValuesWithTimeout(AUTH_CODE_PREFIX + toEmail, authCode, Long.parseLong(this.authCodeExpirationMills));
    }

    private void checkDuplicatedEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()) {
            throw new ConflictException(DUPLICATE_USER);
        }
    }
    private String createCode() {
        Random rand = new Random();
        StringBuffer authCodeSB = new StringBuffer();
        for(int i=0; i<9; i++) {
            authCodeSB.append((char)(rand.nextInt(26) + 65));
        }
        return authCodeSB.toString();
    }

    public EmailVerificationResponseDto verifiedCode(String email, String authCode) {
        this.checkDuplicatedEmail(email);
        String redisAuthCode = redisService.getValues(AUTH_CODE_PREFIX + email);
        boolean authResult = redisAuthCode != null && redisAuthCode.equals(authCode);
        if(!authResult) {
            throw new InvalidValueException(INVALID_AUTH_CODE);
        }
        return EmailVerificationResponseDto.of(true);
    }

}
