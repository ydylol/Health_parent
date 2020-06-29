package com.rendiyu.dao;

import com.github.pagehelper.Page;
import com.rendiyu.pojo.CheckGroup;

import java.util.List;


public interface CheckGroupDao {

    List<CheckGroup> findCheckGroupBySetmealId(Integer id);

    public Page<CheckGroup> findByCondition(String string);

    void addCheckGroup(CheckGroup checkGroup);

    void addTCC(Integer last_id, Integer checkitemId);

    CheckGroup findById(Integer id);

    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    void edit(CheckGroup checkGroup);

    void deleteRelation(Integer id);

    void deleteByid(Integer id);

    void deleteRelation1(Integer id);

    List<CheckGroup> findAll();

}
