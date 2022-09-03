package com.shaun.seckill.mapper;

import com.shaun.seckill.pojo.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shaun.seckill.vo.OrderDetailVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Shaun
 * @since 2022-07-11
 */
@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {

    /**
     * 根据订单号，返回订单详细信息
     * @param orderId 订单号
     * @return 订单详情实体类
     */
    OrderDetailVO getOrderDetailById(Long orderId);
}
