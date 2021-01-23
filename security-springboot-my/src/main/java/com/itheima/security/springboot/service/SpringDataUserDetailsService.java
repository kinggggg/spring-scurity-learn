package com.itheima.security.springboot.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.itheima.security.springboot.dao.UserDao;
import com.itheima.security.springboot.model.PermissionDto;
import com.itheima.security.springboot.model.UserDto;

/**
 * @author liweibo03 <liweibo03@kuaishou.com>
 * Created on 2021-01-23
 */
@Service
public class SpringDataUserDetailsService implements UserDetailsService {

    @Autowired
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username:" + username);

        UserDto userDto = userDao.getUserByUsername(username);
        if (userDto == null) {
            // 返回null即可! 因为Spring Security会根据null做处理
            return null;
        }

        List<String> permissions = userDao.findPermissionsByUserId(userDto.getId());
        String[] permissionArray = new String[permissions.size()];
        permissions.toArray(permissionArray);
        UserDetails userDetails = User.withUsername(userDto.getUsername())
                .password(userDto.getPassword()).authorities(permissionArray).build();

        return userDetails;
    }
}
