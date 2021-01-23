package com.itheima.security.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @author Administrator
 * @version 1.0
 **/
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //定义用户信息服务（查询用户信息）
//    @Bean
//    public UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.withUsername("zhangsan").password("123").authorities("p1").build());
//        manager.createUser(User.withUsername("lisi").password("456").authorities("p2").build());
//        return manager;
//    }

    //密码编码器
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //安全拦截机制（最重要）
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/r/r1").hasAuthority("p1")
                .antMatchers("/r/r2").hasAuthority("p2")
                .antMatchers("/r/**").authenticated()//所有/r/**的请求必须认证通过
                // 顺序不能颠倒!! Spring Security是按照配置的权限认证从上到下执行的! 因此需要把范围更大的放到最下边
                .anyRequest().permitAll()//除了/r/**，其它的请求可以访问
                .and()
                .formLogin()//允许表单登录
                .loginPage("/login-view")
                .loginProcessingUrl("/login")
                .successForwardUrl("/login-success")//自定义登录成功的页面地址
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login-view")
                .and()
                // 当设置为 STATELESS 即, 在任何情况下Spring Security都不会创建Session, 这就意味着就算登录成功后,
                // 并且当前登录成功的用户具备权限 p1 的话, 此时若访问/r/r1也会跳转到登录页!
                // 因为在服务器端此时没有与cookie对应的Session信息, Spring Security此时也就无法获取之前登录成功的用户信息
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 默认值为 IF_REQUIRED 即, 如果需要就创建一个Session（默认）登录时
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);


    }
}
