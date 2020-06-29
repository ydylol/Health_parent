package com.rendiyu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.rendiyu.dao.MemberDao;
import com.rendiyu.entity.Result;
import com.rendiyu.pojo.Member;
import com.rendiyu.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Service(interfaceClass = MemberService.class)
public class MemberSerivceImpl implements MemberService {
    @Autowired
    MemberDao memberDao;

    @Override
    public List<Integer> findMemberCountByMonth(List<String> month) {
        List<Integer> list = new ArrayList<>();
        for(String m : month){
            m = m + "-31";//格式：2019.04.31
            Integer count = memberDao.findMemberCountBeforeDate(m);
            list.add(count);
        }
        return list;
    }

    @Override
    public void add(Member member) {
        if(member.getPassword()!=null){
            member.setPassword(MD5Utils.md5(member.getPassword()));
        }
        memberDao.add(member);
    }

    @Override
    public Member findMemberBytelephone(Member member) {
        Member member1 = memberDao.findMemberBytelephone(member);
        return  member1;
    }
}
