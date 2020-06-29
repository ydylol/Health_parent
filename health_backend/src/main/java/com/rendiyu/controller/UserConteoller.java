package com.rendiyu.controller;

import com.rendiyu.constant.MessageConstant;
import com.rendiyu.entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserConteoller {
    @RequestMapping("/getUsername.do")
    public Result getUsername(){
        //当Spring security完成认证后，会将当前用户信息保存到框架提供的上下文对象
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(user);
        if(user != null){
            String username = user.getUsername();
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,username);
        }

        return new Result(false, MessageConstant.GET_USERNAME_FAIL);
    }
}
