package com.shaun.seckill.vo;

import com.shaun.seckill.pojo.User;
import lombok.Data;

/**
 * @Author Shaun
 * @Date 2022/7/13 12:27
 * @Description:
 */

@Data
public class DetailVO {
    private User user;
    private GoodsVO goodsVO;
    private int seckillStatus;
    private long remainSeconds;
}
