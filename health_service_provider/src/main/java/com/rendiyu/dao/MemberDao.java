package com.rendiyu.dao;

import com.rendiyu.pojo.Member;

public interface MemberDao {
    Member findByTelephone(String telephone);

    void add(Member member);

    Member findMemberBytelephone(Member member);

    Integer findMemberCountBeforeDate(String m);
}
