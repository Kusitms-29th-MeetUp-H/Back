package com.kusitms29.backendH.infra.external.fcm.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FCMTokenRepository {
    private final StringRedisTemplate tokenRedisTemplate;

    public void saveToken(String userId, String fcmToken) {
        tokenRedisTemplate.opsForValue()
                .set(userId, fcmToken);
    }

    public String getToken(String userId) {
        return tokenRedisTemplate.opsForValue().get(userId);
    }

    public void deleteToken(String userId) {
        tokenRedisTemplate.delete(userId);
    }

    public boolean hasKey(String userId) {
        return tokenRedisTemplate.hasKey(userId);
    }
}
