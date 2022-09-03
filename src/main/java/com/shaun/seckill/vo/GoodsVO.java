package com.shaun.seckill.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.shaun.seckill.pojo.Goods;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author Shaun
 * @Date 2022/7/11 10:59
 * @Description: 商品VO实体类
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsVO{
    /**
     * 商品ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商品名称
     */
    @TableField("goods_name")
    private String goodsName;

    /**
     * 商品标题
     */
    @TableField("goods_title")
    private String goodsTitle;

    /**
     * 商品图片
     */
    @TableField("goods_img")
    private String goodsImg;

    /**
     * 商品描述
     */
    @TableField("goods_detail")
    private String goodsDetail;

    /**
     * 商品价格
     */
    @TableField("goods_price")
    private BigDecimal goodsPrice;

    /**
     * 商品库存，-1表示没有限制
     */
    @TableField("goods_stock")
    private Integer goodsStock;
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
