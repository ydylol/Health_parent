package com.rendiyu.dao;

import com.rendiyu.pojo.Permission;

import java.util.Set;

public interface PermissionDao {

    Set<Permission> findPermisstionByRoleId(Integer RoleId);
}
