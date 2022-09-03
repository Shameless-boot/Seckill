package com.shaun.seckill.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shaun.seckill.pojo.SecKillMessage;
import com.shaun.seckill.pojo.SeckillGoods;
import com.shaun.seckill.pojo.SeckillOrders;
import com.shaun.seckill.pojo.User;
import com.shaun.seckill.rabbit.MySender;
import com.shaun.seckill.service.GoodsService;
import com.shaun.seckill.service.OrdersService;
import com.shaun.seckill.service.SeckillGoodsService;
import com.shaun.seckill.service.SeckillOrdersService;
import com.shaun.seckill.vo.Result;
import com.shaun.seckill.vo.ResultCode;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Shaun
 * @Date 2022/7/11 15:36
 * @Description: 秒杀控制层
 */

@Controller()
@RequestMapping("/seckill")
public class SeckillController implements InitializingBean {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private SeckillOrdersService seckillOrdersService;
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private SeckillGoodsService seckillGoodsService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    // 内存标记
    private Map<Long, Boolean> emptyStockMap;
    @Autowired
    private MySender mySender;

    /**
     * Window 优化前 qps：525
     * 页面缓存 500
     * 接口优化 1852 qps
     * Linux 优化前 qps：174
     * @param goodId
     * @param model
     * @param user
     * @return
     */
    @RequestMapping("/doSeckill/{goodId}")
    @ResponseBody
    public Result doSeckill(@PathVariable("goodId") Long goodId, Model model, User user, HttpServletResponse response) {
        if (user == null) {
            try {
                response.sendRedirect("/login/toLogin");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /*// 1、判断库存数量，不能通过前端传参来判断库存数量，因为库存数量可能会被修改
        GoodsVO goodsVO = goodsService.queryByGoodId(goodId);
        // Integer stockCount = (Integer) redisTemplate.opsForValue().get("sekillGoodsCount:" + goodId);
        if (goodsVO.getStockCount() < 1) {
            return Result.Error(ResultCode.EMPTY_STOCK);
        }

        // 2、判断是否存在用户重复购买
        // QueryWrapper<SeckillOrders> queryWrapper = new QueryWrapper<>();
        // queryWrapper.eq("user_id", user.getId());
        // queryWrapper.eq("goods_id", goodsVO.getId());
        // // 去商品秒杀订单表中查询是否存在重复记录，以用户id为索引查询
        // SeckillOrders seckillOrder = seckillOrdersService.getOne(queryWrapper);
        // 从redis读取秒杀订单信息，如果读取得到表示用户已经购买过商品了
        SeckillOrders seckillOrder = (SeckillOrders) redisTemplate.opsForValue().get("seckillOrder:" + user.getId() + ":" + goodId);
        if (seckillOrder != null) {
            return Result.Error(ResultCode.REPEAT_PURCHASE);
        }
        // 3、创建商品记录和秒杀记录
        // GoodsVO goodsVO = goodsService.queryByGoodId(goodId);
        Orders order = ordersService.seckill(goodsVO, user);

        if (order == null) {
            return Result.Error(ResultCode.SECKILL_FAIL);
        }

        return Result.success(order);*/

        ValueOperations<String, Object> ops = redisTemplate.opsForValue();

        // 3、优化：使用内存标记，只要redis中该商品库存为0了，就在map中设置该商品为false，这样就不会走redis，进一步优化效率
        if (emptyStockMap.get(goodId))
            return Result.Error(ResultCode.EMPTY_STOCK);

        // 1、优化：将系统初始化阶段，将秒杀商品库存存储在redis中，从redis读取库存提高效率
        // decrement()是一个原子操作
        Long stockCount = ops.decrement("seckillGoodsStock:" + goodId);
        if (stockCount < 0) {
            // 内存标记该商品库存为0了
            emptyStockMap.put(goodId, true);
            ops.increment("seckillGoodsStock:" + goodId);
            return Result.Error(ResultCode.EMPTY_STOCK);
        }

        // 2、优化：将用户购买记录存储在redis中，通过访问redis中的记录判断用户是否重复购买，避免直接访问mysql
        SeckillOrders seckillOrders = (SeckillOrders) ops.get("seckillOrder:" + user.getId() + ":" + goodId);
        if (seckillOrders != null)
            return Result.Error(ResultCode.REPEAT_PURCHASE);

        SecKillMessage message = new SecKillMessage(user, goodId);
        ObjectMapper objectMapper = new ObjectMapper();
        String msg = null;
        try {
            msg = objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        mySender.sendSeckillMessage(msg);

        return Result.success(0);
    }

    @RequestMapping("/getResult/{goodsId}")
    @ResponseBody
    public Result getResult(@PathVariable("goodsId") Long goodsId, User user, HttpServletResponse response) {
        if (user == null) {
            try {
                response.sendRedirect("/login/toLogin");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Long orderId = seckillOrdersService.getResult(user, goodsId);
        return Result.success(orderId);
    }


    /**
     * 在初始化阶段，将数据库中秒杀商品的库存提前存储在redis中。
     */
    @Override
    public void afterPropertiesSet(){
        List<SeckillGoods> list = seckillGoodsService.list();
        if (CollectionUtils.isEmpty(list))
            return;

        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        emptyStockMap = new HashMap<>(list.size());

        list.forEach(goods -> {
            ops.set("seckillGoodsStock:" + goods.getGoodsId(), goods.getStockCount());
            emptyStockMap.put(goods.getGoodsId(), false);
        });
    }
}
