package com.rendiyu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.rendiyu.dao.MemberDao;
import com.rendiyu.dao.OrderDao;
import com.rendiyu.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = ReportService.class)
public class ReportServiceImpl implements ReportService{
    @Autowired
    MemberDao memberDao;

    @Autowired
    OrderDao orderDao;

    @Override
    public Map<String, Object> getBusinessReportData() throws Exception {
        //获取当前日期
        String today = DateUtils.parseDate2String(DateUtils.getToday());
        //获得本周一的日期
        String thisWeekMonday = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());
        //获取本月的第一天
        String firstDay4ThisMonth = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());
        //今日新增会员数
        Integer todayNewMember = memberDao.findMemberCountByDate(today);
        //总会员数         
        Integer totalMember = memberDao.findMemberTotalCount();
        //本周新增会员数         
        Integer thisWeekNewMember = memberDao.findMemberCountAfterDate(thisWeekMonday);
        //本月新增会员数         
        Integer thisMonthNewMember = memberDao.findMemberCountAfterDate(firstDay4ThisMonth);
        //今日预约数
        Integer todayOrderNumber = orderDao.findOrderCountByDate(today);
        //本周预约数         
        Integer thisWeekOrderNumber = orderDao.findOrderCountAfterDate(thisWeekMonday);
        //本月预约数         
        Integer thisMonthOrderNumber = orderDao.findOrderCountAfterDate(firstDay4ThisMonth);
        //今日到诊数
        Integer todayVisitsNumber = orderDao.findVisitsCountByDate(today);
        //本周到诊数         
        Integer thisWeekVisitsNumber = orderDao.findVisitsCountAfterDate(thisWeekMonday);
        //本月到诊数
        Integer thisMonthVisitsNumber = orderDao.findVisitsCountAfterDate(firstDay4ThisMonth);
        //热门套餐（取前4）         
        List<Map> hotSetmeal = orderDao.findHotSetmeal();
        Map<String,Object> map = new HashMap<>();
        map.put("reportDate",today);
        map.put("todayNewMember",todayNewMember);
        map.put("totalMember",totalMember);
        map.put("thisWeekNewMember",thisWeekNewMember);
        map.put("thisMonthNewMember",thisMonthNewMember);
        map.put("todayOrderNumber",todayOrderNumber);
        map.put("thisWeekOrderNumber",thisWeekOrderNumber);
        map.put("thisMonthOrderNumber",thisMonthOrderNumber);
        map.put("todayVisitsNumber",todayVisitsNumber);
        map.put("thisWeekVisitsNumber",thisWeekVisitsNumber);
        map.put("thisMonthVisitsNumber",thisMonthVisitsNumber);
        map.put("hotSetMeal",hotSetmeal);
        return map;
    }

    public static void main(String[] args) throws Exception {
        //获取当前日期
        String today = DateUtils.parseDate2String(DateUtils.getToday());
        //获得本周一的日期
        String thisWeekMonday = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());
        //获取本月的第一天
        String firstDay4ThisMonth = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());
        System.out.println(today);
        System.out.println(thisWeekMonday);
        System.out.println(firstDay4ThisMonth);
    }
}
