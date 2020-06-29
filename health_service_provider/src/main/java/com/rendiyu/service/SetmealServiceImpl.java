package com.rendiyu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rendiyu.constant.RedisConstant;
import com.rendiyu.dao.SetmealDao;
import com.rendiyu.entity.QueryPageBean;
import com.rendiyu.pojo.CheckItem;
import com.rendiyu.pojo.Setmeal;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Map;

@Service(interfaceClass = com.rendiyu.service.SetmealService.class)
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    SetmealDao setmealDao;

    @Autowired
    JedisPool jedisPool;

    @Override
    public void addSetmeal(Setmeal setmeal, Integer[] checkgroupIds) {
        setmealDao.addSetmeal(setmeal);
        Integer id = setmeal.getId();
        this.setRelation(id,checkgroupIds);
        String fileName = setmeal.getImg();
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,fileName);


    }

    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
        return setmealDao.findCheckItemIdsByCheckGroupId(id);
    }

    @Override
    public List<Map<String, Object>> findSetmealCount() {
        return setmealDao.findSetmealCount();
    }

    @Override
    public Setmeal findSetmealByid(Integer id) {
      return   setmealDao.findSetmealByid(id);
    }

    @Override
    public List<Setmeal> findAll() {
      return   setmealDao.findAll();
    }

    @Override
    public void deleteByid(Integer id) {
        setmealDao.deletefromTSC(id);
        Setmeal setmeal = this.findById(id);
        System.out.println(setmeal.getImg());
        jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());


        setmealDao.deleteById(id);
    }

    @Override
    public void update(Setmeal setmeal, Integer[] checkgroupIds) {
        setmealDao.updateSetmeal(setmeal);
        setmealDao.deletefromTSC(setmeal.getId());
        this.setRelation(setmeal.getId(),checkgroupIds);


    }

    @Override
    public Setmeal findById(Integer id) {
        return setmealDao.findById(id);
    }

    private void setRelation(Integer last_id, Integer[] checkitemIds) {
        if(checkitemIds!=null){
            for (Integer checkitemId : checkitemIds) {
                    setmealDao.addTSC(last_id,checkitemId);
                //  Map<Integer,Integer> map =new HashMap<>();
                // map.put()
            }
        }
    }

    @Override
    public Page<Setmeal> findPage(QueryPageBean pageBean) {
        Integer currentPage = pageBean.getCurrentPage();
        String queryString = pageBean.getQueryString();
        Integer pageSize = pageBean.getPageSize();
        PageHelper.startPage(currentPage,pageSize);
        Page<Setmeal> checkItems = setmealDao.findByCondition(queryString);
        return checkItems;
    }
}
