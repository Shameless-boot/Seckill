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

    /**
     * 验证用户发送的验证码是否跟数据库中的一样
     * @param goodsId 商品id
     * @param userId 用户 id
     * @param verifyCode 用户发送的验证码
     * @return 验证结果
     */
    boolean checkVerifyCode(Long goodsId, Long userId, String verifyCode);

    /**
     * 随机生成秒杀地址
     * @param goodsId 商品id
     * @param userId 用户id
     * @return 秒杀地址
     */
    String createSeckillPath(Long goodsId, Long userId);

    /**
     * 检查秒杀地址是否正确
     * @param goodId 商品id
     * @param userId 用户ID
     * @param path 秒杀地址
     * @return 验证结果
     */
    boolean checkPath(Long goodId, Long userId, String path);
}
