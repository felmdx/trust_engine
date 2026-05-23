package com.trustengine.trust_engine.infrastructure.persistence.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RedisRateLimitAdapter {

    private final StringRedisTemplate redisTemplate;
    
    private static final int MAX_REQUESTS = 5;
    private static final Duration TIME_WINDOW = Duration.ofMinutes(1);

    public boolean isIpBloqueado(String ipAddress) {
        String redisKey = "rate_limit:login:" + ipAddress;

        Long currentRequests = redisTemplate.opsForValue().increment(redisKey);

        if (currentRequests != null && currentRequests == 1) {
            redisTemplate.expire(redisKey, TIME_WINDOW);
        }

        return currentRequests != null && currentRequests > MAX_REQUESTS;
    }
}