package com.shaun.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shaun.seckill.pojo.SeckillOrders;
import com.shaun.seckill.mapper.SeckillOrdersMapper;
import com.shaun.seckill.pojo.User;
import com.shaun.seckill.service.SeckillOrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Shaun
 * @since 2022-07-11
 */
@Service
public class SeckillOrdersServiceImp extends ServiceImpl<SeckillOrdersMapper, SeckillOrders> implements SeckillOrdersService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 根据userId和goodsId查询秒杀结果
     * @param user 用户id
     * @param goodsId 商品id
     * @return orderId，orderId > 1 ： 秒杀成功， orderId = 0 ：排队中， orderId = -1 ： 秒杀失败
     */
    @Override
    public Long getResult(User user, Long goodsId) {
        QueryWrapper<SeckillOrders> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user.getId());
        queryWrapper.eq("goods_id", goodsId);
        SeckillOrders secKillOrder = this.getOne(queryWrapper);
        if (secKillOrder != null)
            return secKillOrder.getOrderId();
        else if (redisTemplate.hasKey("isStockEmpty:" + goodsId)) {
            return -1L;
        }else
            return 0L;
    }
}
