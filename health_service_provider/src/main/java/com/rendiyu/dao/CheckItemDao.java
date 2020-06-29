package com.rendiyu.dao;

import com.github.pagehelper.Page;
import com.rendiyu.pojo.CheckItem;

import java.util.List;

public interface CheckItemDao {
    List<CheckItem>  findCheckItemByCheckGroupId(Integer id);
    List<CheckItem> findAll();
    void addCheckItem(CheckItem checkItem);

    Page<CheckItem> findByCondition(String queryString);

    CheckItem findById(Integer id);

    void edit(CheckItem checkItem);

    void delete(Integer id);

    Integer findByIdFromTCC(Integer id);

    void deleteByIdFromTcc(Integer id);
}
