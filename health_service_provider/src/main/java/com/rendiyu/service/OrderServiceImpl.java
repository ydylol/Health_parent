package com.rendiyu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.rendiyu.constant.MessageConstant;
import com.rendiyu.dao.MemberDao;
import com.rendiyu.dao.OrderDao;
import com.rendiyu.dao.OrdersettingDao;
import com.rendiyu.entity.Result;
import com.rendiyu.pojo.Member;
import com.rendiyu.pojo.Order;
import com.rendiyu.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;

@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderDao orderDao;
    @Autowired
    OrdersettingDao orderSettingDao;
    @Autowired
    MemberDao memberDao;

    @Override
    public Map findById(Integer id) throws Exception {
        Map map = orderDao.findById(id);
        if (map!=null){
            Date orderDate = (Date)map.get("orderDate");
            map.put("orderDate",DateUtils.parseDate2String(orderDate));
        }
        return map;

    }

    @Override
    public Result addOrder(Map<String, String> map) throws Exception {
        String orderDate = map.get("orderDate");
        Date date = DateUtils.parseString2Date(orderDate);
        long count = orderSettingDao.findCountByOrderDate(date);
        if(count<=0){
            //预约日期不存在，不能预约
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        int number = orderSettingDao.getNumber(date);//可预约人数
        int reservations = orderSettingDao.getReservations(date);//已预约人数
        //预约人数等于甚至超过可预约人数时
        if(reservations>=number){
            return new Result(false,MessageConstant.ORDER_FULL);
        }
        String telephone =  map.get("telephone");
        Member member = memberDao.findByTelephone(telephone);
        if(member!=null){
            //该会员是否在同一个时间同一个套餐进行了预约
            Integer memberId = member.getId();
            String setmealId = map.get("setmealId");
            Order order = new Order(memberId, date, Integer.parseInt(setmealId));
            Order order1=orderDao.findByOrder(order);
            if(order1!=null){
                return new Result(false,MessageConstant.HAS_ORDERED);
            }
            //可以预约  设置预约人数+1

        }
        orderSettingDao.updateReservations(date);
        if (member==null){
            member = new Member();
            member.setName(map.get("name"));
            member.setPhoneNumber(telephone);
            member.setIdCard( map.get("idCard"));
            member.setSex(map.get("sex"));
            member.setRegTime(new Date());
            memberDao.add(member);


        }

        Order order = new Order(member.getId(),date,map.get("orderType"),Order.ORDERSTATUS_NO,Integer.parseInt((String)map.get("setmealId")));
        orderDao.add(order);
        return new Result(true,MessageConstant.ORDER_SUCCESS,order.getId());
    }
}
