package com.shaun.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.shaun.seckill.pojo.Orders;
import com.shaun.seckill.mapper.OrdersMapper;
import com.shaun.seckill.pojo.SeckillGoods;
import com.shaun.seckill.pojo.SeckillOrders;
import com.shaun.seckill.pojo.User;
import com.shaun.seckill.service.OrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shaun.seckill.service.SeckillGoodsService;
import com.shaun.seckill.service.SeckillOrdersService;
import com.shaun.seckill.vo.GoodsVO;
import com.shaun.seckill.vo.OrderDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Shaun
 * @since 2022-07-11
 */
@Service
public class OrdersServiceImp extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {
    @Autowired
    private SeckillGoodsService seckillGoodsService;
    @Autowired
    private SeckillOrdersService seckillOrdersService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private OrdersMapper ordersMapper;

    /**
     * 创建商品订单和秒杀订单
     * @param goodsVO 商品信息
     * @param user 用户信息
     * @return 订单信息
     */
    @Override
    @Transactional
    public Orders seckill(GoodsVO goodsVO, User user) {
        // 1、秒杀商品减少库存
        QueryWrapper<SeckillGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("goods_id", goodsVO.getId());
        SeckillGoods seckillGoods = seckillGoodsService.getOne(queryWrapper);
        // update语句是有加锁的，但是这里并没有设置库存>0的条件，因此会发生库存超卖现象
        // seckillGoods.setStockCount(seckillGoods.getStockCount() - 1);
        // seckillGoodsService.updateById(seckillGoods);
        UpdateWrapper<SeckillGoods> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("goods_id", goodsVO.getId());
        updateWrapper.gt("stock_count", 0);
        updateWrapper.setSql("stock_count = stock_count - 1");
        boolean update = seckillGoodsService.update(updateWrapper);
        if (!update) {
            log.debug("StockEmpty");
            redisTemplate.opsForValue().set("isStockEmpty:" + goodsVO.getId(), "0");
            // redisTemplate.opsForValue().set("sekillGoodsCount:" + seckillGoods.getGoodsId(), 0);
            return null;
        }

        // 2、生成商品订单
        Orders order = new Orders();
        order.setUserId(user.getId());
        order.setGoodsId(goodsVO.getId());
        order.setDeliveryAddrId(0L);
        order.setGoodsName(goodsVO.getGoodsName());
        order.setGoodsCount(1);
        order.setGoodsPrice(seckillGoods.getSeckillPrice());
        order.setOrderChannel(1);
        order.setStatus(0);
        order.setCreateDate(new Date());
        this.save(order);

        // 3、生成商品秒杀订单
        SeckillOrders seckillOrder = new SeckillOrders();
        seckillOrder.setUserId(user.getId());
        seckillOrder.setOrderId(order.getId());
        seckillOrder.setGoodsId(goodsVO.getId());
        seckillOrdersService.save(seckillOrder);

        // 4、将商品秒杀订单存储在redis缓存中
        redisTemplate.opsForValue().set("seckillOrder:" + user.getId() + ":" + goodsVO.getId(), seckillOrder);
        return order;
    }

    /**
     * 根据订单号，返回订单详细信息
     * @param orderId 订单号
     * @return 订单详情实体类
     */
    @Override
    public OrderDetailVO queryOrderDetailById(Long orderId) {
        return ordersMapper.getOrderDetailById(orderId);
    }
}
