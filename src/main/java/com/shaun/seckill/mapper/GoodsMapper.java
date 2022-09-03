package com.shaun.seckill.mapper;

import com.shaun.seckill.pojo.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shaun.seckill.vo.GoodsVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Shaun
 * @since 2022-07-11
 */
@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {

    /**
     * 查询所有商品的属性，包括秒杀的属性
     * @return 商品VO
     */
    List<GoodsVO> selectGoodsVO();

    /**
     * 根据商品ID查询商品信息以及秒杀的信息
     * @param goodId 商品ID
     * @return 商品VO对象
     */
    GoodsVO selectGoodsVOByGoodId(long goodId);
}
