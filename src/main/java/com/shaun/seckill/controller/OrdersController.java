package com.shaun.seckill.controller;

import com.shaun.seckill.pojo.User;
import com.shaun.seckill.service.OrdersService;
import com.shaun.seckill.vo.OrderDetailVO;
import com.shaun.seckill.vo.Result;
import com.shaun.seckill.vo.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Shaun
 * @since 2022-07-11
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    @RequestMapping("/detail/{orderId}")
    @ResponseBody
    public Result getOrderDetailById(@PathVariable("orderId") Long orderId, User user, HttpServletResponse response) {
        if (user == null) {
            try {
                response.sendRedirect("/login/toLogin");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        OrderDetailVO orderDetailVO = ordersService.queryOrderDetailById(orderId);
        if (orderDetailVO == null)
            return Result.Error(ResultCode.ORDER_IS_NOT_EXIST);

        log.info("orderDetails : {}", orderDetailVO);
        return Result.success(orderDetailVO);
    }
}
