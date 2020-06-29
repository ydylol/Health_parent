package com.rendiyu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.rendiyu.constant.MessageConstant;
import com.rendiyu.constant.RedisMessageConstant;
import com.rendiyu.entity.Result;
import com.rendiyu.pojo.Order;
import com.rendiyu.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("order")
public class OrderController {
    @Reference
    OrderService orderService;
    @Autowired
    JedisPool jedisPool;

    @RequestMapping("/sumit")
    public Result submit(@RequestBody Map<String,String > map){
        System.out.println(map);
        String  setmealId = map.get("setmealId");
        String idCard = map.get("idCard");
        String sex = map.get("sex");
        String validateCode = map.get("validateCode");
        String telephone = map.get("telephone");
        String orderDate = map.get("orderDate");
        //从redis中查找出的验证码
        String codeInRedis = jedisPool.getResource().get(telephone+ RedisMessageConstant.SENDTYPE_ORDER);
        //验证验证码
        System.out.println("验证码是:"+codeInRedis);
        if(codeInRedis==null|| !codeInRedis.equals(validateCode)){
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
       // System.out.println(orderdate);
        Result result = null;
        try {
            map.put("orderType", Order.ORDERTYPE_WEIXIN);
            result = orderService.addOrder(map);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return  result;
    }
    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            Map map = orderService.findById(id);
            //查询预约信息成功
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            //查询预约信息失败
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }
    }

}
