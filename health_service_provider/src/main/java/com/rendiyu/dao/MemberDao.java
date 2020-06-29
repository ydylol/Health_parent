package com.rendiyu.dao;

import com.rendiyu.pojo.Member;

public interface MemberDao {
    Member findByTelephone(String telephone);

    void add(Member member);

    Member findMemberBytelephone(Member member);

    Integer findMemberCountBeforeDate(String m);

    /**
     * //今日新增会员数
     * @param today
     * @return
     */
    Integer findMemberCountByDate(String today);

    /**
     * 总会员数
     * @return
     */
    Integer findMemberTotalCount();

    Integer findMemberCountAfterDate(String thisWeekMonday);
}
