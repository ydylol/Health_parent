package com.rendiyu.service;

import com.rendiyu.entity.PageResult;
import com.rendiyu.entity.QueryPageBean;
import com.rendiyu.pojo.CheckGroup;

import java.util.List;

public interface CheckGroupService {
    public PageResult findPage(QueryPageBean pageBean);

    void addCheckGroup(CheckGroup checkGroup, Integer[] checkitemIds);

    CheckGroup findById(Integer id);

    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    void edit(CheckGroup checkGroup, Integer[] checkitemIds);

    void deleteByid(Integer id);

    List<CheckGroup> findAll();
}
