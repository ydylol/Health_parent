package com.rendiyu.service;

import com.rendiyu.entity.Result;

import java.util.Map;

public interface OrderService {
    Result addOrder(Map<String, String> map) throws Exception;

    Map findById(Integer id) throws Exception;
}
