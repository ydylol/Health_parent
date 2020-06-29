package com.rendiyu.dao;

import com.rendiyu.pojo.Order;

import java.util.Map;

public interface OrderDao {
    Order findByOrder(Order order);

    void add(Order order);

    Map findById(Integer id);
}
