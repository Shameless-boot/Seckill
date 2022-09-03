package com.shaun.seckill;

import com.shaun.seckill.mapper.GoodsMapper;
import com.shaun.seckill.mapper.OrdersMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest
class SeckillApplicationTests {
    // @Autowired
    // DataSource dataSource;

    @Autowired
    RedisTemplate redisTemplate;

    // @Test
    // void contextLoads() throws SQLException {
    //     System.out.println(dataSource.getConnection());
    // }

    @Test
    void test01() {
        redisTemplate.opsForValue().set("123", "234");
        System.out.println(redisTemplate.opsForValue().get("123"));
    }

    @Autowired
    GoodsMapper goodsMapper;

    @Test
    void test02() {
        System.out.println(goodsMapper.selectGoodsVO());
    }

    @Autowired
    OrdersMapper ordersMapper;

    @Test
    void test03() {
        System.out.println(ordersMapper.getOrderDetailById(466L));
    }
}
