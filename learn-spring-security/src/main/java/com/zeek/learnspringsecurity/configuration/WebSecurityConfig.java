package com.zeek.learnspringsecurity.configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author liweibo03 <liweibo03@kuaishou.com>
 * Created on 2020-09-11
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage("/myLogin.html") // 自定义登录页面
                    .loginProcessingUrl("/login") // 处理登录请求的路由。这里只是定制化路由的地址，这里定制化了后，在myLogin.html表单action地址中也要与这里一致
                    .successHandler(new AuthenticationSuccessHandler() { // 认证成功后的处理逻辑
                        @Override
                        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                            response.setContentType("application/json;charset=utf-8");
                            PrintWriter out = response.getWriter();
                            out.write("{\n" +
                                    "    \"code\": 0,\n" +
                                    "    \"message\": \"success\"\n" +
                                    "}");
                        }
                    })
                    .failureHandler(new AuthenticationFailureHandler() {
                        @Override
                        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                            response.setContentType("application/json;charset=utf8");
                            PrintWriter out = response.getWriter();
                            out.write("{\n" +
                                    "    \"code\": 1,\n" +
                                    "    \"message\": \"failure\"\n" +
                                    "}");
                        }
                    })
                    .permitAll()
                .and()
                    .csrf().disable();
    }
}
