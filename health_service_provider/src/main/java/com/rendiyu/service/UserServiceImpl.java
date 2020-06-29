package com.rendiyu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.rendiyu.dao.PermissionDao;
import com.rendiyu.dao.RoleDao;
import com.rendiyu.dao.UserDao;
import com.rendiyu.pojo.Permission;
import com.rendiyu.pojo.Role;
import com.rendiyu.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

@Service(interfaceClass = UserService.class)
public class UserServiceImpl  implements UserService{
    @Autowired
    UserDao userDao;
    @Autowired
    RoleDao roleDao;
    @Autowired
    PermissionDao permissionDao;

    @Override
    public User findUserByUsername(String username) {
        User user = userDao.findUserByUsername(username);
        if(user==null){
            return null;
        }
        Integer userId = user.getId();
        Set<Role> roles = roleDao.selectByUserid(userId);
        if(roles!=null||roles.size()>0){
            for (Role role : roles) {
                Integer roleId = role.getId();
                Set<Permission> permisstions = permissionDao.findPermisstionByRoleId(roleId);
                if(permisstions!=null||permisstions.size()>0){
                    role.setPermissions(permisstions);
                }
            }

            user.setRoles(roles);
        }
        return user;
    }

}
