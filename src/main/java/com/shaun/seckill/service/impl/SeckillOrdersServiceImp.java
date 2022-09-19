package com.shaun.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shaun.seckill.pojo.SeckillOrders;
import com.shaun.seckill.mapper.SeckillOrdersMapper;
import com.shaun.seckill.pojo.User;
import com.shaun.seckill.service.SeckillOrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shaun.seckill.util.MD5Util;
import com.shaun.seckill.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

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

    /**
     * 验证用户发送的验证码是否跟数据库中的一样
     * @param goodsId 商品id
     * @param userId 用户 id
     * @param verifyCode 用户发送的验证码
     * @return 验证结果
     */
    @Override
    public boolean checkVerifyCode(Long goodsId, Long userId, String verifyCode) {
        if (goodsId == null || userId == null || !StringUtils.hasText(verifyCode))
            return false;

        String key = "captacha:" + userId + ":" + goodsId;
        return verifyCode.equals(redisTemplate.opsForValue().get(key));
    }

    /**
     * 随机生成秒杀地址
     * @param goodsId 商品id
     * @param userId 用户id
     * @return 秒杀地址
     */
    @Override
    public String createSeckillPath(Long goodsId, Long userId) {
        // 1、使用MD5随机生成秒杀地址
        String seckillPath = MD5Util.md5(UUIDUtil.uuid() + "123456");
        // 2、将秒杀地址存入在 Redis 中，设置失效时间为 60秒
        redisTemplate.opsForValue().set("SeckillPath:" + userId + ":" + goodsId, seckillPath,
                60, TimeUnit.SECONDS);
        // 3、返回秒杀地址
        return seckillPath;
    }

    @Override
    public boolean checkPath(Long goodId, Long userId, String path) {
        if (goodId == null || userId == null || !StringUtils.hasText(path))
            return false;

        return path.equals(redisTemplate.opsForValue().get("SeckillPath:" + userId + ":" + goodId));
    }
}
