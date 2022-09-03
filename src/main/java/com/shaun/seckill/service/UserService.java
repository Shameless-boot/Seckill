package com.shaun.seckill.service;

import com.shaun.seckill.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shaun.seckill.vo.LoginVO;
import com.shaun.seckill.vo.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Shaun
 * @since 2022-07-09
 */
public interface UserService extends IService<User> {

    Result login(LoginVO loginVO, HttpServletRequest request, HttpServletResponse response);

    User getUserByCookie(String userTicket, HttpServletRequest request, HttpServletResponse response);
}
