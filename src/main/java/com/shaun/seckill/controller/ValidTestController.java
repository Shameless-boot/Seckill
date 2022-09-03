package com.shaun.seckill.controller;

import com.shaun.seckill.vo.ValidVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Author Shaun
 * @Date 2022/7/10 21:57
 * @Description:
 */

@RestController
@RequestMapping("/valid")
public class ValidTestController {

    @RequestMapping("/test01")
    public ValidVO test01(@Validated ValidVO validVO) {
        return validVO;
    }
}
