package com.shaun.seckill.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shaun.seckill.pojo.User;
import com.shaun.seckill.service.UserService;
import com.shaun.seckill.util.CookieUtil;
import com.shaun.seckill.vo.Result;
import com.shaun.seckill.vo.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.method.HandlerMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/**
 * @Author Shaun
 * @Date 2022/9/14 23:22
 * @Description: 通过限流接口拦截器
 */
@Component
public class AccessLimitInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 拦截方法
        if (handler instanceof HandlerMethod) {
            // 获取登录对象
            User user = getUser(request, response);
            // 将登录对象保存到ThreadLocal中
            UserContext.setUser(user);

            HandlerMethod hm = (HandlerMethod) handler;
            // 1、获取方法上的 @AccessLimit 接口
            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
            // 2、如果方法上没有该接口，直接放行
            if (accessLimit == null) {
                System.out.println("1111");
                return true;
            }

            // 3、获取限流接口参数
            int seconds = accessLimit.seconds();
            int maxCount = accessLimit.maxCount();
            String key = request.getRequestURI();
            if (accessLimit.needLogin()) {
                if (user == null) {
                    // 用户为空，响应错误信息
                    render(response, ResultCode.NOT_LOGIN);
                    return false;
                }
                // 如果需要登录，在 key 后面拼接上用户id
                key += ":" + user.getId();
            }

            // 5、查询redis数据库，获取用户请求次数
            ValueOperations<String, Object> ops = redisTemplate.opsForValue();
            Integer count = (Integer) ops.get(key);

            if (count == null) {
                // 第一次发送请求
                ops.set(key, 1, seconds, TimeUnit.SECONDS);
            }else if (count < maxCount)
                // 如果请求次数还未超过最大次数，递增计数
                ops.increment(key);
            else {
                render(response, ResultCode.ACCESS_LIMIT_ERROR);
                // 驳回请求
                return false;
            }
        }
        return true;
    }

    private void render(HttpServletResponse response, ResultCode respBeanEnum) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Result respBean = Result.Error(respBeanEnum);
        out.write(new ObjectMapper().writeValueAsString(respBean));
        out.flush();
        out.close();
    }

    public User getUser(HttpServletRequest request, HttpServletResponse response) {
        String ticket = CookieUtil.getCookieValue(request, "UserTicket");
        if (!StringUtils.hasLength(ticket))
            return null;

        return userService.getUserByCookie(ticket, request, response);
    }
}
