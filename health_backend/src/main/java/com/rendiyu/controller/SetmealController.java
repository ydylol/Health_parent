package com.rendiyu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.Page;
import com.rendiyu.constant.MessageConstant;
import com.rendiyu.constant.RedisConstant;
import com.rendiyu.entity.PageResult;
import com.rendiyu.entity.QueryPageBean;
import com.rendiyu.entity.Result;
import com.rendiyu.pojo.CheckGroup;
import com.rendiyu.pojo.Setmeal;
import com.rendiyu.service.SetmealService;
import com.rendiyu.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("setmeal")
public class SetmealController {
    @Reference
    SetmealService setmealService;
    @Autowired
    JedisPool jedisPool;

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean pageBean){

            Page<Setmeal> page= setmealService.findPage(pageBean);
            return  new PageResult(page.getTotal(),page);


    }
    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile")MultipartFile imgFile){
        System.out.println(imgFile);
        //获取原始的文件上传的名字,主要用来截取文件后缀
        String originalFilename = imgFile.getOriginalFilename();
        int index = originalFilename.lastIndexOf(".");
        String extention = originalFilename.substring(index - 1);//.jpg
        String fileName = UUID.randomUUID().toString()+extention;//文件上传之后的名称
        try {
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);

        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
        return new Result(true,MessageConstant.PIC_UPLOAD_SUCCESS,fileName);

    }

    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds){
        try {
            setmealService.addSetmeal(setmeal,checkgroupIds);
            return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }
    }

    @RequestMapping("/findById")
    public Result findByid(Integer id){
        try {
            Setmeal setmeal = setmealService.findById(id);
            return  new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(true,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
    @RequestMapping("/findCheckGroupIdsByCheckGroupId")
    public Result findCheckGroupIdsByCheckGroupId(Integer id){
        try {
            List<Integer> list= setmealService.findCheckItemIdsByCheckGroupId(id);
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }
    @RequestMapping("edit")
    public Result edit(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){
        try {
            setmealService.update(setmeal,checkgroupIds);
            return new Result(true,"编辑成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"编辑失败");
        }

    }
    @RequestMapping("/delete")
    public Result delete(Integer id){
        try {
            setmealService.deleteByid(id);
            return new Result(true,"删除套餐成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,"删除套餐失败");
        }
    }
}
