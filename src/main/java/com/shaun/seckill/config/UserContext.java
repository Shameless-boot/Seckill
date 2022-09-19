package com.shaun.seckill.config;

import com.shaun.seckill.pojo.User;

/**
 * @Author Shaun
 * @Date 2022/9/14 23:30
 * @Description: 保存当前登录用户
 */
public class UserContext {
    private static ThreadLocal<User> userHolder = new ThreadLocal<>();

    public static void setUser(User user) {
        userHolder.set(user);
    }

    public static User getUser() {
        return userHolder.get();
    }
}
