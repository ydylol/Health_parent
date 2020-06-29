package com.rendiyu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rendiyu.dao.CheckGroupDao;
import com.rendiyu.entity.PageResult;
import com.rendiyu.entity.QueryPageBean;
import com.rendiyu.pojo.CheckGroup;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service()
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    CheckGroupDao checkGroupDao;

    @Override
    public List<CheckGroup> findAll() {
     return    checkGroupDao.findAll();
    }

    @Override
    public void deleteByid(Integer id) {
        checkGroupDao.deleteRelation(id);
        checkGroupDao.deleteRelation1(id);
        checkGroupDao.deleteByid(id);
    }

    @Override
    public void edit(CheckGroup checkGroup, Integer[] checkitemIds) {
        //删除联系
        checkGroupDao.deleteRelation(checkGroup.getId());
        checkGroupDao.edit(checkGroup);
        this.setRelation(checkGroup.getId(),checkitemIds);
    }

    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
       return checkGroupDao.findCheckItemIdsByCheckGroupId(id);
    }

    @Override
    public CheckGroup findById(Integer id) {
        return checkGroupDao.findById(id);
    }

    @Override
    public void addCheckGroup(CheckGroup checkGroup, Integer[] checkitemIds) {
        // 创建检查项
        checkGroupDao.addCheckGroup(checkGroup);
        Integer last_Id = checkGroup.getId();
        System.out.println(last_Id);
        this.setRelation(last_Id,checkitemIds);
        //关联检查项和检查组
    }

    private void setRelation( Integer last_id,Integer[] checkitemIds) {
        if(checkitemIds!=null){
            for (Integer checkitemId : checkitemIds) {
                checkGroupDao.addTCC(last_id,checkitemId);
              //  Map<Integer,Integer> map =new HashMap<>();
               // map.put()
            }
        }
    }


    @Override
    public PageResult findPage(QueryPageBean pageBean) {
        Integer currentPage = pageBean.getCurrentPage();
        Integer pageSize = pageBean.getPageSize();
        String queryString = pageBean.getQueryString();
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckGroup> byCondition = checkGroupDao.findByCondition(queryString);
        return  new PageResult(byCondition.getTotal(),byCondition);
    }
}
