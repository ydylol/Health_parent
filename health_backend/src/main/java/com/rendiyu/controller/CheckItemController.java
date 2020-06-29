package com.rendiyu.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.rendiyu.constant.MessageConstant;
import com.rendiyu.entity.PageResult;
import com.rendiyu.entity.QueryPageBean;
import com.rendiyu.entity.Result;
import com.rendiyu.pojo.CheckItem;
import com.rendiyu.service.CheckItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@Controller
//@ResponseBody
@RestController
@RequestMapping("/checkitem")
public class CheckItemController {
   @Reference
    CheckItemService checkItemService;

    /**
     * 新增检查项的方法
     */

    @RequestMapping("/add")
    @PreAuthorize("hasAnyAuthority('CHECKITEM_ADD')")
    public Result add(@RequestBody CheckItem checkItem){  // requestbody
        System.out.println(checkItem);
        try {
            System.out.println("123");
            checkItemService.addCheckItem(checkItem);
        } catch (Exception e) {
          return   new Result(false,MessageConstant.ADD_CHECKGROUP_FAIL);
        }
        return new Result(true,MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    /**
     * 进行分页查询
     * @param pageBean
     * @return
     */
    @PreAuthorize("hasAnyAuthority('CHECKITEM_QUERY')")
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean pageBean){
        System.out.println("查询条件:"+pageBean.getQueryString());
        PageResult page = checkItemService.findPage(pageBean);
        return page;

    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        System.out.println(id);
        try {
            CheckItem byid = checkItemService.findByid(id);
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,byid);
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }
    @PreAuthorize("hasAnyAuthority('CHECKITEM_EDIT')")
    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckItem checkItem ){
        try {
            checkItemService.edit(checkItem);
            return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_CHECKGROUP_FAIL);
        }

    }
    @PreAuthorize("hasAnyAuthority('CHECKITEM_DELETE')")
    @RequestMapping("/delete")
    public Result delete(Integer id ){
        try {
            checkItemService.deleteById(id);
            return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_CHECKGROUP_FAIL);
        }

    }
    @RequestMapping("/findAll")
    public Result findAll(){
        try {
            List<CheckItem> checkItems = checkItemService.findAll();
            return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS,checkItems);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_CHECKGROUP_FAIL);
        }

    }

}
