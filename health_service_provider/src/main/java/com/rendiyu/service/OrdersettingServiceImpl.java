package com.rendiyu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.rendiyu.dao.OrdersettingDao;
import com.rendiyu.pojo.OrderSetting;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Service(interfaceClass = OrdersettingService.class)
public class OrdersettingServiceImpl implements OrdersettingService {
    @Autowired
    OrdersettingDao ordersettingDao;
    @Override
    public void add(List<OrderSetting> data) {

        if (data!=null&&data.size()>0) {
            for (OrderSetting orderSetting : data) {
                long countByOrderDate = ordersettingDao.findCountByOrderDate(orderSetting.getOrderDate());
                if(countByOrderDate > 0){
                    //已经进行了预约设置，执行更新操作
                    ordersettingDao.editNumberByOrderDate(orderSetting);
                }else {
                    //没有进行预约设置，执行插入操作
                    ordersettingDao.add(orderSetting);
                }
            }
        }
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        Date orderDate = orderSetting.getOrderDate();
        //根据日期查询是否已经进行了预约设置
        long count = ordersettingDao.findCountByOrderDate(orderDate);
        if(count > 0){
            //当前日期已经进行了预约设置，需要执行更新操作
            ordersettingDao.editNumberByOrderDate(orderSetting);
        }else{
            //当前日期没有就那些预约设置，需要执行插入操作
            ordersettingDao.add(orderSetting);
        }
    }

    @Override
    public List<Map> getOrderSettingByMonth(String date) {
        String start=date+"-1";
        String end=date+"-31";
        Map<String,String> map = new HashMap<>();
              map.put("start",start);
              map.put("end",end);
              List<OrderSetting> list = ordersettingDao.getOrderSettingByMonth(map);
        List<Map> result = new ArrayList<>();
        if(list != null && list.size() > 0){
            for (OrderSetting orderSetting : list) {
                Map<String,Object> m = new HashMap<>();
                m.put("date",orderSetting.getOrderDate().getDate());//获取日期数字（几号）
                m.put("number",orderSetting.getNumber());
                m.put("reservations",orderSetting.getReservations());
                result.add(m);
            }
        }
        return result;
    }
}
