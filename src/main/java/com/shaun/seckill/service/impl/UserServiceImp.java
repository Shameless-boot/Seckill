package com.shaun.seckill.service.impl;

import com.shaun.seckill.global.GlobalException;
import com.shaun.seckill.pojo.User;
import com.shaun.seckill.mapper.UserMapper;
import com.shaun.seckill.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shaun.seckill.util.CookieUtil;
import com.shaun.seckill.util.MD5Util;
import com.shaun.seckill.util.UUIDUtil;
import com.shaun.seckill.util.ValidatorUtil;
import com.shaun.seckill.vo.LoginVO;
import com.shaun.seckill.vo.Result;
import com.shaun.seckill.vo.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Shaun
 * @since 2022-07-09
 */
@Service
@Slf4j
public class UserServiceImp extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 对用户登录进行验证
     * @param loginVO 前端用户的vo实体类
     * @return 返回验证结果
     */
    @Override
    public Result login(LoginVO loginVO, HttpServletRequest request, HttpServletResponse response) {
        String mobile = loginVO.getMobile();
        String password = loginVO.getPassword();
        // if (mobile == null || password == null)
        //     return Result.Error(ResultCode.LOGIN_ERROR);
        //
        // if (!ValidatorUtil.isMobile(mobile))
        //     return Result.Error(ResultCode.MOBILE_ERROR);

        User user = userMapper.selectById(mobile);
        if (user == null)
            throw new GlobalException(ResultCode.LOGIN_ERROR);

        if (!MD5Util.formPassToDBPass(password, user.getSalt()).equals(user.getPasword()))
            throw new GlobalException(ResultCode.LOGIN_ERROR);

        // 生成cookie
        String ticket = UUIDUtil.uuid();
        // request.getSession().setAttribute(ticket, user);
        redisTemplate.opsForValue().set("user:" + ticket, user);
        CookieUtil.setCookie(request, response, "UserTicket", ticket);
        return Result.success(ticket);
    }

    @Override
    public User getUserByCookie(String userTicket, HttpServletRequest request, HttpServletResponse response) {
        if (!StringUtils.hasLength(userTicket))
            return null;


        User user = (User) redisTemplate.opsForValue().get("user:" + userTicket);
        if (user != null) // 刷新cookie的时间
            CookieUtil.setCookie(request, response, "UserTicket", userTicket);

        return user;
    }

}
