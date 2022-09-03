package com.shaun.seckill.rabbit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shaun.seckill.config.RabbitmqDirectConfig;
import com.shaun.seckill.config.RabbitmqFanoutConfig;
import com.shaun.seckill.config.RabbitmqHeaderConfig;
import com.shaun.seckill.config.RabbitmqTopicConfig;
import com.shaun.seckill.pojo.SecKillMessage;
import com.shaun.seckill.pojo.User;
import com.shaun.seckill.service.GoodsService;
import com.shaun.seckill.service.OrdersService;
import com.shaun.seckill.vo.GoodsVO;
import com.shaun.seckill.vo.Result;
import com.shaun.seckill.vo.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Author Shaun
 * @Date 2022/7/14 10:22
 * @Description: rabbitmq 消费者
 */

@Service
@Slf4j
public class MyReceiver {

    /*@RabbitListener(queues = RabbitmqFanoutConfig.QUEUE01)
    public void receive(Object msg) {
        log.info("received queue01 : {}", msg);
    }

    @RabbitListener(queues = RabbitmqFanoutConfig.QUEUE02)
    public void receive02(Object msg) {
        log.info("received queue02: {}", msg);
    }*/

    /*@RabbitListener(queues = RabbitmqDirectConfig.QUEUE01)
    public void receiveRed(Object msg) {
        log.info("receive red : {}", msg);
    }

    @RabbitListener(queues = RabbitmqDirectConfig.QUEUE02)
    public void receiveGreen(Object msg) {
        log.info("receive green : {}", msg);
    }

    @RabbitListener(queues = RabbitmqTopicConfig.QUEUE01)
    public void receive01(Object msg) {
        log.info("receive msg from queue01 : {}", msg);
    }

    @RabbitListener(queues = RabbitmqTopicConfig.QUEUE02)
    public void receive02(Object msg) {
        log.info("receive msg from queue02 : {}", msg);
    }

    @RabbitListener(queues = RabbitmqHeaderConfig.QUEUE01)
    public void receive03(Message msg) {
        log.info("receive msg from queue01 : {}", msg);
        log.info("data : {}", new String(msg.getBody()));
    }

    @RabbitListener(queues = RabbitmqHeaderConfig.QUEUE02)
    public void receive04(Message msg) {
        log.info("receive msg from queue02 : {}", msg);
        log.info("data : {}", new String(msg.getBody()));
    }*/

    @Autowired
    private OrdersService ordersService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @RabbitListener(queues = RabbitmqTopicConfig.SECKILL_QUEUE)
    public void receiveSeckillMessage(String message) {
        log.info("接收秒杀信息 : {}", message);
        // 1、将json的字符串转换为java对象
        ObjectMapper objectMapper = new ObjectMapper();
        SecKillMessage secKillMessage = null;
        try {
            secKillMessage = objectMapper.readValue(message.getBytes(), SecKillMessage.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        User user = secKillMessage.getUser();
        Long goodsId = secKillMessage.getGoodsId();
        // 2、查询商品信息
        GoodsVO goodsVO = goodsService.queryByGoodId(goodsId);

        // 3、判断库存是否足够
        if (goodsVO.getStockCount() < 1) {
            return;
        }
        // 4、判断是否重复抢购
        if (redisTemplate.opsForValue().get("seckillOrder:" + user.getId() + ":" + goodsId) != null) {
            return;
        }

        // 进行秒杀
        ordersService.seckill(goodsVO, user);
    }
}
