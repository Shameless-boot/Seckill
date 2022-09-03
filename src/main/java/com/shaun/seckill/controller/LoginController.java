package com.shaun.seckill.controller;

import com.shaun.seckill.service.UserService;
import com.shaun.seckill.vo.LoginVO;
import com.shaun.seckill.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * <p>
 *  登录控制层
 * </p>
 *
 * @author Shaun
 * @since 2022-07-09
 */
@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {
    @Autowired
    private UserService userService;

    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/doLogin")
    @ResponseBody
    public Result doLogin(@Valid LoginVO loginVO, HttpServletRequest request, HttpServletResponse response, Model model) {
        return userService.login(loginVO, request, response);
    }
}
