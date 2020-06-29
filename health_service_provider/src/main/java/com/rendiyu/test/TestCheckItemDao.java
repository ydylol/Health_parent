package com.rendiyu.test;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.rendiyu.dao.CheckGroupDao;
import com.rendiyu.dao.CheckItemDao;
import com.rendiyu.entity.QueryPageBean;
import com.rendiyu.pojo.CheckGroup;
import com.rendiyu.pojo.CheckItem;
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

public class TestCheckItemDao {
    public SqlSession sqlSession;
    public CheckItemDao checkItemDao;
    CheckGroupDao checkGroupDao;
    @Before
    public void before() throws IOException {
        //1.启动mybatis
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfigDao.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        sqlSession = sqlSessionFactory.openSession();
        checkItemDao = sqlSession.getMapper(CheckItemDao.class);
       checkGroupDao = sqlSession.getMapper(CheckGroupDao.class);
    }
    @After
    public void after(){
        sqlSession.close();
    }

    /*测试mapper文件中的查询是否可行*/
    @Test
    public void TestCheckItemDao_selectByCondition() throws IOException {
        Page<CheckItem> checkItems = checkItemDao.findByCondition("");
        for (CheckItem checkItem : checkItems) {
            System.out.println(checkItem);
        }
    }

    /*测试CheckItemService的findPage查询是否可行*/
    @Test
    public void TestCheckItemService_findPage(){
        QueryPageBean queryPageBean=new QueryPageBean();
        queryPageBean.setCurrentPage(1);
        queryPageBean.setPageSize(5);
        queryPageBean.setQueryString("");
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<CheckItem> checkItemPage = checkItemDao.findByCondition("");
        System.out.println(checkItemPage.toString());
    }

    /*测试添加检查项是否正确*/
    @Test
    public void TestCheckItemDao_addItem(){
        CheckItem checkItem=new CheckItem();
        checkItem.setCode("0066");
        checkItem.setName("视力");
        checkItem.setSex("男");
        checkItem.setAge("15");
        checkItem.setRemark("");
        checkItem.setAttention("");
        checkItem.setPrice(0.0F);
        checkItem.setType("2");
        checkItemDao.addCheckItem(checkItem);
        sqlSession.commit();
    }
    @Test
    public void deletebyid(){
        Integer count = checkItemDao.findByIdFromTCC(28);
        System.out.println(count);
        if(count>0){
            checkItemDao.deleteByIdFromTcc(28);
            checkItemDao.delete(28);
        }
        sqlSession.commit();
    }
    @Test
    public void findAll(){
        List<CheckItem> all = checkItemDao.findAll();
        System.out.println(all);
    }
    @Test
    public void  edit(){
        CheckItem checkItem=new CheckItem();
        checkItem.setId(123);
        checkItem.setCode("0066");
        checkItem.setName("qweasd");
        checkItem.setSex("男");
        checkItem.setAge("15");
        checkItem.setRemark("dsadsad");
        checkItem.setAttention("qweqweqwe");
        checkItem.setPrice(0.0F);
        checkItem.setType("2");
        checkItemDao.edit(checkItem);
        sqlSession.commit();

    }
    @Test
    public void editCheckdsakl(){
        CheckGroup checkGroup = new CheckGroup();
        checkGroup.setCheckItems(new ArrayList<CheckItem>());
        checkGroup.setId(5);
        checkGroup.setCode("0001");
        checkGroup.setName("核酸检测");
        checkGroup.setHelpCode("ABCD");
        checkGroup.setSex("0");
        checkGroup.setRemark("无");
        checkGroup.setAttention("无");
        checkGroupDao.edit(checkGroup);
        sqlSession.commit();
    }

}
