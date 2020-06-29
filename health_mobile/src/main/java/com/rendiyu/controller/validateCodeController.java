package com.rendiyu.controller;

import com.aliyuncs.exceptions.ClientException;
import com.rendiyu.constant.MessageConstant;
import com.rendiyu.constant.RedisMessageConstant;
import com.rendiyu.entity.Result;
import com.rendiyu.utils.SMSUtils;
import com.rendiyu.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("validateCode")
public class validateCodeController {
    @Autowired
    JedisPool jedisPool;
    @RequestMapping("/send4Orde")
    public Result getValidateCode(String telephone){
        System.out.println(telephone);
        Integer code = ValidateCodeUtils.generateValidateCode(4);
//        try {
//            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone, code.toString());
//        } catch (ClientException e) {
//            e.printStackTrace();
//            return  new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
//
//        }
        jedisPool.getResource().setex(telephone+ RedisMessageConstant.SENDTYPE_ORDER,5*60,code.toString());
        System.out.println(code.toString());
        return  new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS,code.toString());
    }

    @RequestMapping("/send4Login")
    public Result getValidateCodeforlogin(String telephone){
        System.out.println(telephone);
        Integer code = ValidateCodeUtils.generateValidateCode(4);
//        try {
//            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone, code.toString());
//        } catch (ClientException e) {
//            e.printStackTrace();
//            return  new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
//
//        }
        jedisPool.getResource().setex(telephone+ RedisMessageConstant.SENDTYPE_LOGIN,5*60,code.toString());
        System.out.println(code.toString());
        return  new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS,code.toString());
    }



}
