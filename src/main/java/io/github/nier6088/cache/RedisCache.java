package io.github.nier6088.cache;


import io.github.nier6088.util.RedisCacheUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class RedisCache {

    @Autowired
    private RedisCacheUtil redisCacheUtil;


}
