package com.shaun.seckill.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author Shaun
 * @since 2022-07-11
 */
@Getter
@Setter
@TableName("t_seckill_goods")
public class SeckillGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 秒杀订单ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商品ID
     */
    @TableField("goods_id")
    private Long goodsId;

    /**
     * 秒杀价格
     */
    @TableField("seckill_price")
    private BigDecimal seckillPrice;

    /**
     * 库存数量
     */
    @TableField("stock_count")
    private Integer stockCount;

    /**
     * 秒杀开始时间
     */
    @TableField("start_date")
    private Date startDate;

    /**
     * 秒杀结束时间
     */
    @TableField("end_date")
    private Date endDate;


}
