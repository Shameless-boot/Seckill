package com.shaun.seckill.controller;

import com.shaun.seckill.pojo.User;
import com.shaun.seckill.rabbit.MySender;
import com.shaun.seckill.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author Shaun
 * @Date 2022/7/12 14:45
 * @Description:
 */

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private MySender mySender;

    @RequestMapping("/info")
    @ResponseBody
    public Result info(User user) {
        return Result.success(user);
    }


    /*@RequestMapping("/send")
    @ResponseBody
    public void send() {
        mySender.send("hello");
    }*/

   /* @RequestMapping("/direct/red")
    @ResponseBody
    public void sendRed() {
        mySender.sendRed("hello, red");
    }

    @RequestMapping("/direct/green")
    @ResponseBody
    public void sendGreen() {
        mySender.sendGreen("hello, green");
    }

    @RequestMapping("/topic/send01")
    @ResponseBody
    public void send01() {
        mySender.send01("hello, red");
    }

    @RequestMapping("/topic/send02")
    @ResponseBody
    public void send02() {
        mySender.send02("hello, green");
    }

    @RequestMapping("/headers/send03")
    @ResponseBody
    public void send03() {
        mySender.send03("hello, red and slow");
    }

    @RequestMapping("/headers/send04")
    @ResponseBody
    public void send04() {
        mySender.send04("hello, red and normal");
    }*/
}
