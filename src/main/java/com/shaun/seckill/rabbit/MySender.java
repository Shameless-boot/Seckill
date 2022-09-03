package com.shaun.seckill.rabbit;

import com.shaun.seckill.config.RabbitmqDirectConfig;
import com.shaun.seckill.config.RabbitmqFanoutConfig;
import com.shaun.seckill.config.RabbitmqHeaderConfig;
import com.shaun.seckill.config.RabbitmqTopicConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Shaun
 * @Date 2022/7/14 10:20
 * @Description: rabbitmq 生产者
 */

@Service
@Slf4j
public class MySender {
    @Autowired
    private RabbitTemplate rabbitTemplate;


    /*public void send(Object msg) {
        log.info("send : {}", msg);
        rabbitTemplate.convertAndSend(RabbitmqFanoutConfig.EXCHANGE,"", msg);
    }*/

    /*public void sendRed(Object msg) {
        log.info("send red : {}", msg);
        rabbitTemplate.convertAndSend(RabbitmqDirectConfig.EXCHANGE, RabbitmqDirectConfig.ROUTING_KEY_01, msg);
    }

    public void sendGreen(Object msg) {
        log.info("send green : {}", msg);
        rabbitTemplate.convertAndSend(RabbitmqDirectConfig.EXCHANGE, RabbitmqDirectConfig.ROUTING_KEY_02, msg);
    }

    public void send01(Object msg) {
        log.info("only send to queue02 : {}", msg);
        rabbitTemplate.convertAndSend(RabbitmqTopicConfig.EXCHANGE, "queue.misc.red", msg);
    }

    public void send02(Object msg) {
        log.info("send to queue01 and queue02 : {}", msg);
        rabbitTemplate.convertAndSend(RabbitmqTopicConfig.EXCHANGE, "red.queue.misc.java", msg);
    }

    public void send03(String msg) {
        log.info("send to queue01 and queue02 : {}", msg);
        MessageProperties mp = new MessageProperties();
        mp.setHeader("color", "red");
        mp.setHeader("speed", "slow");
        Message message = new Message(msg.getBytes(), mp);
        rabbitTemplate.convertAndSend(RabbitmqHeaderConfig.EXCHANGE, "", message);
    }

    public void send04(String msg) {
        log.info("send only to queue01 : {}", msg);
        MessageProperties properties = new MessageProperties();
        properties.setHeader("color", "red");
        properties.setHeader("speed", "normal");
        rabbitTemplate.convertAndSend(RabbitmqHeaderConfig.EXCHANGE, "", new Message(msg.getBytes(), properties));
    }*/

    public void sendSeckillMessage(String message) {
        log.info("发送秒杀信息 : {}", message);
        rabbitTemplate.convertAndSend(RabbitmqTopicConfig.EXCHANGE, "seckill.msg", message);
    }
}
