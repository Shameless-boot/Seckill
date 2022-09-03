package com.shaun.seckill.util;

import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

/**
 * @Author Shaun
 * @Date 2022/7/9 22:23
 * @Description:
 */
public class ValidatorUtil {
    private static final Pattern mobile_pattern = Pattern.compile("[1]([3-9])[0-9]{9}$");

    public static boolean isMobile(String mobile) {
        if (!StringUtils.hasLength(mobile))
            return false;

        return mobile_pattern.matcher(mobile).matches();
    }

    public static void main(String[] args) {
        System.out.println(isMobile("13000000121"));
    }
}
