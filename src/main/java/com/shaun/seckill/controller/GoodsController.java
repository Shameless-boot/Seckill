package com.shaun.seckill.controller;

import com.shaun.seckill.pojo.User;
import com.shaun.seckill.service.GoodsService;
import com.shaun.seckill.service.UserService;
import com.shaun.seckill.vo.DetailVO;
import com.shaun.seckill.vo.GoodsVO;
import com.shaun.seckill.vo.Result;
import com.shaun.seckill.vo.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @Author Shaun
 * @Date 2022/7/10 10:21
 * @Description:
 */

@Controller
@RequestMapping("/goods")
@Slf4j
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    /**
     * windows 优化前：474 qps
     * windows 页面缓存 1603 qps
     * linux 优化前 217 qps
     * @param model 返回给前端的数据
     * @param user 用户
     * @return 页面地址
     */
    @RequestMapping(value = "/toList", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String toLogin(Model model, User user, HttpServletRequest request, HttpServletResponse response) {
        // if (user == null)
        //     return "login";
        // 1、从redis中读取页面缓存
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        String html = (String) ops.get("goodsListHtml");
        if (StringUtils.hasLength(html))
            return html;

        // 2、向model中添加数据
        model.addAttribute("user", user);
        model.addAttribute("goodsList", goodsService.queryGoodsVO());

        // 3、不存在缓存，使用thymeleaf模板引擎渲染html页面
        WebContext webContext = new WebContext(request, response, request.getServletContext(),
                request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goodsList", webContext);
        // 4、添加页面缓存到redis中
        if (StringUtils.hasText(html)) {
            // 设置60秒超时时间
            ops.set("goodsListHtml", html, 60, TimeUnit.SECONDS);
        }
        return html;
    }

    @RequestMapping(value = "/toDetail2/{goodId}", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String toDetail2(@PathVariable(value = "goodId") long goodId, Model model, User user,
                            HttpServletRequest request, HttpServletResponse response) {
        if (user == null)
            return "login";

        // 1、查找页面缓存，存在就返回页面缓存
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        String html = (String) ops.get("goodDetail:" + goodId);
        if (StringUtils.hasText(html)) {
            log.info("走缓存了");
            return html;
        }

        // 2、准备数据
        model.addAttribute("user", user);
        GoodsVO goodsVO = goodsService.queryByGoodId(goodId);

        int seckillStatus = 0;
        long remainSeconds = 0;
        // 获取秒杀状态：0秒杀还未开始，1:秒杀进行中 2:秒杀结束了
        Date startDate = goodsVO.getStartDate();
        Date endDate = goodsVO.getEndDate();
        Date nowDate = new Date();

        // 秒杀还没开始
        if (nowDate.before(startDate)) {
            seckillStatus = 0;
            // 计算倒计时
            remainSeconds = (startDate.getTime() - nowDate.getTime()) / 1000;
        }else if (nowDate.after(endDate)) {
            seckillStatus = 2;
            remainSeconds = -1;
        } // 秒杀已结束了
        else{
            // 秒杀进行中
            seckillStatus = 1;
        }

        model.addAttribute("goods", goodsVO);
        model.addAttribute("seckillStatus", seckillStatus);
        model.addAttribute("remainSeconds", remainSeconds);

        // 3、创建页面缓存
        WebContext webContext = new WebContext(request, response, request.getServletContext(),
                request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goodsDetail", webContext);
        if (StringUtils.hasText(html))
            ops.set("goodDetail:" + goodId, html, 60, TimeUnit.SECONDS);

        return html;
    }


    @RequestMapping(value = "/toDetail/{goodId}")
    @ResponseBody
    public Result toDetail(@PathVariable(value = "goodId") long goodId, User user, HttpServletResponse response, HttpServletRequest request) {
        if (user == null) {
            // try {
                // request.getRequestDispatcher("/login/toLogin").forward(request, response);
                return Result.Error(ResultCode.NOT_LOGIN);
            // } catch (IOException | ServletException e) {
            //     e.printStackTrace();
            // }
        }

        // 2、准备数据
        GoodsVO goodsVO = goodsService.queryByGoodId(goodId);

        int seckillStatus = 0;
        long remainSeconds = 0;
        // 获取秒杀状态：0秒杀还未开始，1:秒杀进行中 2:秒杀结束了
        Date startDate = goodsVO.getStartDate();
        Date endDate = goodsVO.getEndDate();
        Date nowDate = new Date();

        // 秒杀还没开始
        if (nowDate.before(startDate)) {
            seckillStatus = 0;
            // 计算倒计时
            remainSeconds = (startDate.getTime() - nowDate.getTime()) / 1000;
        }else if (nowDate.after(endDate)) {
            seckillStatus = 2;
            remainSeconds = -1;
        } // 秒杀已结束了
        else{
            // 秒杀进行中
            seckillStatus = 1;
        }


        DetailVO detailVO = new DetailVO();
        detailVO.setUser(user);
        detailVO.setGoodsVO(goodsVO);
        detailVO.setSeckillStatus(seckillStatus);
        detailVO.setRemainSeconds(remainSeconds);

        return Result.success(detailVO);
    }
}
