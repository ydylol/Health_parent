package com.rendiyu.service;

import com.github.pagehelper.Page;
import com.rendiyu.entity.PageResult;
import com.rendiyu.entity.QueryPageBean;
import com.rendiyu.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmealService {


    Page<Setmeal> findPage(QueryPageBean pageBean);

    void addSetmeal(Setmeal setmeal,Integer[] checkgroupIds);

    Setmeal findById(Integer id);

    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    void update(Setmeal setmeal, Integer[] checkgroupIds);

    void deleteByid(Integer id);

    List<Setmeal> findAll();

    Setmeal findSetmealByid(Integer id);

    List<Map<String, Object>> findSetmealCount();
}
