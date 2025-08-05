package com.depinx.data;

import com.depinx.data.entity.DataEntity;
import com.depinx.data.entity.QueryRequest;
import com.depinx.data.service.CexService;
import com.depinx.data.service.LarkService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@Slf4j
@SpringBootTest
class DataProjectsApplicationTests {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private LarkService larkService;
    @Resource
    private CexService cexService;

    @Test
    void contextLoads() {
        redisTemplate.opsForValue().set("test_key", 1000);
        System.out.println("after set value");
        Object o = redisTemplate.opsForValue().get("test_key");
        System.out.println(o);
    }

    @Test
    void testFetchLark() {
        larkService.fetchLark();
    }

    @Test
    void testCexData() {
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.setCoinName("METIS");
        DataEntity dataEntity = cexService.getDataEntity(queryRequest);
        log.info("dataEntity:{}", dataEntity);
    }
}
