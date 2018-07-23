package com.imooc.controller;

import com.imooc.vo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    @RequestMapping(value = "/subLogin",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public  String subLogin(User user) {
        // 1. 获得主体
        Subject subject = SecurityUtils.getSubject();

        // 2. 主体提交请求
        UsernamePasswordToken toke = new UsernamePasswordToken(user.getUsername(),user.getPasssword());
        try {
            subject.login(toke);
        } catch (AuthenticationException e) {
            return  e.getMessage();
        }

        if (subject.hasRole("admin")) {
            return "有admin权限";
        }

        return "无admin权限";

    }
}
