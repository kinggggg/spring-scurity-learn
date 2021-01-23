package com.itheima.security.springboot.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author liweibo03 <liweibo03@kuaishou.com>
 * Created on 2021-01-23
 */
@Service
public class SpringDataUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("username:" + username);
        UserDetails userDetails = User.withUsername("zhangsan").password("$2a$10$RrYkowHQJGfnK.WBVwiqGemON9rV9JOuf.YK/7TxocnVcXrMqqjEG").authorities("p1").build();
        return userDetails;
    }
}
