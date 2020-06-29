package com.rendiyu.service;

import com.rendiyu.entity.Result;
import com.rendiyu.pojo.Member;

import java.util.List;

public interface MemberService {
    Member findMemberBytelephone(Member member);

    void add(Member member);

    List<Integer> findMemberCountByMonth(List<String> list);
}
