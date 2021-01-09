package com.zeek.security.securitydemo1.config;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author liweibo03 <liweibo03@kuaishou.com>
 * Created on 2021-01-09
 */
@Configuration
public class SecurityConfigTest extends WebSecurityConfigurerAdapter {

    @Resource
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 指定授权失败后404跳转页面
        http.exceptionHandling().accessDeniedPage("/unauth.html");

        http.formLogin() // 自定义登录页面
            .loginPage("/login.html") // 登录页面设置
            .loginProcessingUrl("/user/login") // 登录访问路径
            .defaultSuccessUrl("/test/index").permitAll()   // 登录成功后跳转路径
                /**
                 * 以下的内容来自'Spring Security实战'一书中
                 * HttpSecurity提供了很多配置相关的方法，分别对应命名空间配置中的子标签<http>。
                 * 例如， authorizeRequests（）、formLogin（）、httpBasic（）和 csrf（）
                 * 分别对应<intercept-url>、<formlogin>、<http-basic>和<csrf>标签。
                 * 调用这些方法之后，除非使用and（）方法结束当前标签，上下文 才会回到HttpSecurity，否则链式调用的上下文将自动进入对应标签域。
                 *
                 * authorizeRequests（）方法实际上返回了一个 URL 拦截注册器，
                 * 我们可以调用它提供的 anyanyRequest（）、antMatchers（）和regexMatchers（）等方法来匹配系统的URL，并为其指定安全 策略。
                 */
            .and()
                //
                .authorizeRequests()
                    // 可以这么理解: 1. 需要认证authorizeRequests的请求有/, /test/hello, /user/login, 对于其他的请求全部放行permitAll
                    .antMatchers("/", "/test/hello", "/user/login").permitAll() // 设置哪些路径可以直接访问, 不需要认证
                    // 当前登录用户, 只有具备admins权限此可以访问这个路径
//                    .antMatchers("/test/index").hasAuthority("admins,manager")
                    // 具备权限中的一个即可
                    .antMatchers("/test/index").hasAnyAuthority("admins,manager")
                    .antMatchers("/test/index").hasRole("sale")
                    // 2. 对于任何的请求需求进行认证.
                    .anyRequest().authenticated()
            .and().csrf().disable(); // 关闭csrf防护



    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
