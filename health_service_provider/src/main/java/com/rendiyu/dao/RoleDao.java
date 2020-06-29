package com.rendiyu.dao;


import com.rendiyu.pojo.Role;

import java.util.Set;

public interface RoleDao {
    Set<Role> selectByUserid(Integer Userid);
}
