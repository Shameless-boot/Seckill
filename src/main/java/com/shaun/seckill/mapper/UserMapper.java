package com.shaun.seckill.mapper;

import com.shaun.seckill.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Shaun
 * @since 2022-07-09
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
