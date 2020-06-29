package com.rendiyu.dao;

import com.github.pagehelper.Page;
import com.rendiyu.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmealDao {
    Page<Setmeal> findByCondition(String queryString);

    void addSetmeal(Setmeal setmeal);

    void addTSC(Integer last_id, Integer checkitemId);

    Setmeal findById(Integer id);

    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    void deletefromTSC(Integer id);

    void updateSetmeal(Setmeal setmeal);

    void deleteById(Integer id);

    List<Setmeal> findAll();

    Setmeal findSetmealByid(Integer id);

    List<Map<String, Object>> findSetmealCount();
}
