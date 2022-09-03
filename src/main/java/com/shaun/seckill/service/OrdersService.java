package com.shaun.seckill.service;

import com.shaun.seckill.pojo.Orders;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shaun.seckill.pojo.User;
import com.shaun.seckill.vo.GoodsVO;
import com.shaun.seckill.vo.OrderDetailVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Shaun
 * @since 2022-07-11
 */
public interface OrdersService extends IService<Orders> {

    /**
     * 创建商品订单和秒杀订单
     * @param goodsVO 商品信息
     * @param user 用户信息
     * @return 订单信息
     */
    Orders seckill(GoodsVO goodsVO, User user);

    /**
     * 根据订单号，返回订单详细信息
     * @param orderId 订单号
     * @return 订单详情实体类
     */
    OrderDetailVO queryOrderDetailById(Long orderId);
}
