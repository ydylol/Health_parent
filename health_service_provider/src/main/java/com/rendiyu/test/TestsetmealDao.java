package com.rendiyu.test;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rendiyu.dao.CheckGroupDao;
import com.rendiyu.dao.CheckItemDao;
import com.rendiyu.dao.SetmealDao;
import com.rendiyu.entity.QueryPageBean;
import com.rendiyu.pojo.CheckGroup;
import com.rendiyu.pojo.CheckItem;
import com.rendiyu.pojo.Setmeal;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TestsetmealDao {
    public SqlSession sqlSession;
    public CheckItemDao checkItemDao;
    CheckGroupDao checkGroupDao;
    SetmealDao setmealDao;
    @Before
    public void before() throws IOException {
        //1.启动mybatis
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfigDao.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        sqlSession = sqlSessionFactory.openSession();
         setmealDao = sqlSession.getMapper(SetmealDao.class);

    }
    @After
    public void after(){
        sqlSession.close();
    }

    /*测试mapper文件中的查询是否可行*/
    @Test
    public void TestCheckItemDao_selectByCondition() throws IOException {
        Setmeal setmealByid = setmealDao.findSetmealByid(12);
        System.out.println(setmealByid);

    }


}
