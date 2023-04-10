package com.timedeal.timedeal.order.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RedisLockRepository {

    private RedisTemplate<String, String> redisTemplate;

    public Boolean lock(String itemName) {
        return redisTemplate
                .opsForValue()
                .setIfAbsent(generateKey(itemName), "lock", Duration.ofMillis(3_000));
    }

    public Boolean unlock(String itemName) {
        return redisTemplate.delete(generateKey(itemName));
    }

    private String generateKey(String itemName) {
        return itemName.toString();
    }
}
