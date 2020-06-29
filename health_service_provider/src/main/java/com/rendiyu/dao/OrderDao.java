package com.rendiyu.dao;

import com.rendiyu.pojo.Order;

import java.util.List;
import java.util.Map;

public interface OrderDao {
    Order findByOrder(Order order);

    void add(Order order);

    Map findById(Integer id);

    Integer findOrderCountByDate(String today);

    Integer findOrderCountAfterDate(String thisWeekMonday);

    Integer findVisitsCountByDate(String today);

    Integer findVisitsCountAfterDate(String thisWeekMonday);

    List<Map> findHotSetmeal();
}
