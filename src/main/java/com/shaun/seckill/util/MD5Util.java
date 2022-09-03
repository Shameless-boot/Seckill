package com.shaun.seckill.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @Author Shaun
 * @Date 2022/7/9 20:57
 * @Description: MD5工具类
 */
public class MD5Util {

    /**
     * 给定一个字符串，返回一个加密的 MD5值
     * @param src 需要加密的字符串
     * @return 加密了的 MD5值
     */
    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    // 盐值，作为跟前端统一的固定盐
    private static final String salt = "1a2b3c4d";

    /**
     * 这里是在模拟前端使用固定盐加密的过程
     * 对明文进行第一次 MD5加密
     * @param inputPass 明文
     * @return MD5值
     */
    public static String inputPassToFormPass(String inputPass) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass
                + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    /**
     * 对密码进行第二次 MD5加密，这次要加上一个随机盐
     * @param formPass 密码
     * @param salt 随机盐
     * @return MD5值
     */
    public static String formPassToDBPass(String formPass, String salt) {
        String str = ""+salt.charAt(0)+salt.charAt(2) + formPass +salt.charAt(5)
                + salt.charAt(4);
        return md5(str);
    }

    /**
     * 对明文密码进行两次 MD5加密
     * @param inputPass 明文密码
     * @param saltDB 随机盐，第二次 MD5加密要用到
     * @return MD5值
     */
    public static String inputPassToDbPass(String inputPass, String saltDB) {
        String formPass = inputPassToFormPass(inputPass);
        String dbPass = formPassToDBPass(formPass, saltDB);
        return dbPass;
    }

    // 对上面3个方面进行测试
    public static void main(String[] args) {
        System.out.println(inputPassToFormPass("123456"));//d3b1294a61a07da9b49b6e22b2cbd7f9
        System.out.println(formPassToDBPass(inputPassToFormPass("123456"),
                "1a2b3c4d"));
        System.out.println(inputPassToDbPass("123456", "1a2b3c4d"));
    }
}
