package com.shaun.seckill.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author Shaun
 * @Date 2022/7/13 15:56
 * @Description: 订单详情实体类
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailVO {
    private Integer id;
    private String goodsName;
    private BigDecimal goodsPrice;
    private String goodsImg;
    private Date createDate;
    private Integer status;
}
