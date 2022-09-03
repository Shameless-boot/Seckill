package com.shaun.seckill.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Shaun
 * @Date 2022/7/14 16:13
 * @Description: 秒杀信息实体类
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecKillMessage {
    private User user;
    private Long goodsId;
}
