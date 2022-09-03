package com.shaun.seckill.service;

import com.shaun.seckill.pojo.SeckillOrders;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shaun.seckill.pojo.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Shaun
 * @since 2022-07-11
 */
public interface SeckillOrdersService extends IService<SeckillOrders> {
    /**
     * 根据userId和goodsId查询秒杀结果
     * @param user 用户id
     * @param goodsId 商品id
     * @return orderId，orderId > 1 ： 秒杀成功， orderId = 0 ：排队中， orderId = -1 ： 秒杀失败
     */
    Long getResult(User user, Long goodsId);
}
