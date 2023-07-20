package com.bokdung2;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED) // 실제 DB 사용하고 싶을때 NONE 사용
public class RedisBasicTest {
  @Autowired
  RedisTemplate<String, String> redisTemplate;

  @Test
  void redisConnectionTest() {
    final String key = "a";
    final String data = "1";

    final ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
    valueOperations.set(key, data);

    final String s = valueOperations.get(key);
    assertThat(s).isEqualTo(data);
  }

  @Test
  void redisExpireTest() throws InterruptedException {
    final String key = "a";
    final String data = "1";

    final ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
    valueOperations.set(key, data);
    final Boolean expire = redisTemplate.expire(key, 5, TimeUnit.SECONDS);
    Thread.sleep(6000);
    final String s = valueOperations.get(key);
    assertThat(expire).isTrue();
    assertThat(s).isNull();
  }
}
