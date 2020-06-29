package com.rendiyu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.rendiyu.constant.MessageConstant;
import com.rendiyu.entity.Result;
import com.rendiyu.service.MemberService;
import com.rendiyu.service.ReportService;
import com.rendiyu.service.SetmealService;
import com.rendiyu.utils.DateUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("report")
public class ReportController {

    @Reference
    MemberService memberService;
    @Reference
    SetmealService setmealService;
    @Reference
    ReportService  reportService;

    @RequestMapping("/getMemberReport")
    public Result getMemberReport(){
        //获取当前日期的日历实例
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-12);//获取当前日期的前12个月的日期
        List<String> list = new ArrayList<>();
        for(int i = 0;i<12;i++){
            calendar.add(Calendar.MONTH,1);
            list.add(new SimpleDateFormat("yyyy-MM").format(calendar.getTime()));
        }
        Map<String,Object> map = new HashMap<>();
        map.put("months",list);
        List<Integer> memberCount = memberService.findMemberCountByMonth(list);
        map.put("memberCount",memberCount);
        return  new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
    }

    /*统计预约套餐*/
    @RequestMapping("/getSetMealReport")
    public Result getSetmealReport(){
        List<Map<String,Object>> list = setmealService.findSetmealCount();
        //统计预约的套餐数量
        Map<String,Object> map = new HashMap();
        map.put("setmealCount",list);
        List<String> setmealNames = new ArrayList<>();
        for(Map<String,Object> m:list){
            String name = (String)m.get("name");
            setmealNames.add(name);
        }
        map.put("setmealNames",setmealNames);
        return new Result(true,MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,map);
    }

    @RequestMapping("/getBusinessReportData")
    public Result getBusinessReportData() {

        try {
            Map<String, Object> map = reportService.getBusinessReportData();
            return new Result(true, MessageConstant.GET_BUSINESS_REPORT_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }

    }
}
