package com.shaun.seckill.service.impl;

import com.shaun.seckill.pojo.Goods;
import com.shaun.seckill.mapper.GoodsMapper;
import com.shaun.seckill.service.GoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shaun.seckill.vo.GoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Shaun
 * @since 2022-07-11
 */
@Service
public class GoodsServiceImp extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;


    @Override
    public List<GoodsVO> queryGoodsVO() {
        return goodsMapper.selectGoodsVO();
    }

    /**
     * 根据商品ID查询商品信息以及秒杀的信息
     * @param goodId 商品ID
     * @return 商品VO对象
     */
    @Override
    public GoodsVO queryByGoodId(long goodId) {
        return goodsMapper.selectGoodsVOByGoodId(goodId);
    }
}
