package com.rendiyu.service;

import com.rendiyu.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrdersettingService {
    void add(List<OrderSetting> data);

    List<Map> getOrderSettingByMonth(String date);

    void editNumberByDate(OrderSetting orderSetting);
}
