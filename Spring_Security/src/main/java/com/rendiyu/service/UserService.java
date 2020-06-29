package com.rendiyu.service;


import com.rendiyu.pojo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService implements UserDetailsService {
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    //模拟向数据库中插入数据
    public Map<String, UserInfo> map = new HashMap<>();

    public void init(){
        UserInfo u1 = new UserInfo();
        u1.setUsername("admin");
        u1.setPassword(bCryptPasswordEncoder.encode("admin"));
        UserInfo u2 = new UserInfo();
        u2.setUsername("sunny");
        u2.setPassword(bCryptPasswordEncoder.encode("123"));
        map.put(u1.getUsername(),u1);
        map.put(u2.getUsername(),u2);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        init();
        UserInfo admin = map.get("admin");
        System.out.println(admin);
        UserInfo sunny = map.get("sunny");
        System.out.println(sunny);
        System.out.println("username:"+username);
        //模拟从数据库中查询用户
        UserInfo userInfo = map.get(username);
        if(userInfo==null){
            return null;
        }
        //模拟查询数据库中用户的密码  去掉明文标识{noop}
        String password = userInfo.getPassword();
        List<GrantedAuthority> list = new ArrayList<>();
        //授权，后期需要改为查询数据库动态获得用户拥有的权限和角色
//        list.add(new SimpleGrantedAuthority("add"));
//        list.add(new SimpleGrantedAuthority("delete"));
//        list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        if(username.equals("admin")){
            list.add(new SimpleGrantedAuthority("add"));
            list.add(new SimpleGrantedAuthority("delete"));
            list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        User user = new User(username, password, list);
        return user;
    }
}