package com.rendiyu.service;

import com.rendiyu.entity.PageResult;
import com.rendiyu.entity.QueryPageBean;
import com.rendiyu.pojo.CheckItem;

import java.util.List;

public interface CheckItemService {
   public void addCheckItem(CheckItem checkItem);
   public PageResult findPage(QueryPageBean pageBean);
   public CheckItem findByid(Integer id);
   void edit(CheckItem checkItem);

   void deleteById(Integer id);

    List<CheckItem> findAll();
}
