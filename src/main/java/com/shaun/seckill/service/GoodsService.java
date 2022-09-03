package com.shaun.seckill.service;

import com.shaun.seckill.pojo.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shaun.seckill.vo.GoodsVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Shaun
 * @since 2022-07-11
 */
public interface GoodsService extends IService<Goods> {

    /**
     * 查询所有商品的信息，包括秒杀的信息
     * @return 商品VO对象
     */
    List<GoodsVO> queryGoodsVO();

    /**
     * 根据商品ID查询商品信息以及秒杀的信息
     * @param goodId 商品ID
     * @return 商品VO对象
     */
    GoodsVO queryByGoodId(long goodId);
}
