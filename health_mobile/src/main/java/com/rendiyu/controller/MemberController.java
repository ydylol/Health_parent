package com.rendiyu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.rendiyu.constant.MessageConstant;
import com.rendiyu.constant.RedisMessageConstant;
import com.rendiyu.entity.Result;
import com.rendiyu.pojo.Member;
import com.rendiyu.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("member")
public class MemberController {
    @Autowired
    JedisPool jedisPool;

    @Reference
    MemberService memberService;
    @RequestMapping("/login")
    public Result loing(@RequestBody Map<String,String >map, HttpServletResponse response){
        System.out.println(map);
        String telephone = map.get("telephone");
        String validateCode = map.get("validateCode");
        String codeInRedis = jedisPool.getResource().get(telephone+ RedisMessageConstant.SENDTYPE_ORDER);
        if(codeInRedis==null){
            return new Result(false, "验证码已过期");
        }else {

            Member member = new Member();
            member.setPhoneNumber(telephone);
            Member member1 = memberService.findMemberBytelephone(member);
            if(member1==null){
                member = new Member();
                member.setPhoneNumber(telephone);
                member.setRegTime(new Date());
                memberService.add(member);
            }
            //登录成功
            //写入cookie 跟踪用户
            Cookie cookie = new Cookie("login_member_telephone",telephone);
            cookie.setPath("/");//设置cookie的有效路径
            cookie.setMaxAge(60*60*24*30);//有效期30天
            response.addCookie(cookie);//发送cookie
            //保存会员信息到redis中
            String json = JSON.toJSON(member).toString();
            jedisPool.getResource().setex(telephone,60*30,json);
            return new Result(true,MessageConstant.LOGIN_SUCCESS);
        }

    }





}
