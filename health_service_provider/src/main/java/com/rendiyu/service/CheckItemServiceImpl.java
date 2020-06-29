package com.rendiyu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rendiyu.dao.CheckItemDao;
import com.rendiyu.entity.PageResult;
import com.rendiyu.entity.QueryPageBean;
import com.rendiyu.pojo.CheckItem;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(interfaceClass =com.rendiyu.service.CheckItemService.class) //制定interfaceClass 实现checkItemService
public class CheckItemServiceImpl implements CheckItemService {
    @Autowired
    CheckItemDao checkItemDao;

    @Override
    public void addCheckItem(CheckItem checkItem) {
        checkItemDao.addCheckItem(checkItem);
    }


    @Override
    public List<CheckItem> findAll() {
        List<CheckItem> checkItems = checkItemDao.findAll();
        return checkItems;
    }

    @Override
    public void deleteById(Integer id) {
        Integer count = checkItemDao.findByIdFromTCC(id);
        if(count>0){
            checkItemDao.deleteByIdFromTcc(id);
            checkItemDao.delete(id);
        }

    }

    @Override
    public void edit(CheckItem checkItem) {
        checkItemDao.edit(checkItem);
    }

    @Override
    public CheckItem findByid(Integer id) {
        CheckItem checkItem = checkItemDao.findById(id);
        return checkItem;
    }

    @Override
    public PageResult findPage(QueryPageBean pageBean) {
        Integer currentPage = pageBean.getCurrentPage();
        Integer pageSize = pageBean.getPageSize();
        String queryString = pageBean.getQueryString();
        System.out.println("currentPage:"+currentPage);
        System.out.println("pageSize:"+pageSize);
        System.out.println("queryString:"+queryString);
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckItem> checkItems = checkItemDao.findByCondition(queryString);
        System.out.println("page"+checkItems.getPageNum());
        System.out.println("pagebean"+pageBean.getCurrentPage());

        return new PageResult(checkItems.getTotal(),checkItems);

    }
}
