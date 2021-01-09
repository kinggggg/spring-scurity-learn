package com.zeek.security.securitydemo1.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liweibo03 <liweibo03@kuaishou.com>
 * Created on 2021-01-09
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/hello")
    public String hello() {
        return "hello security";
    }

    @RequestMapping("/index")
    public String index() {
        return "hello index";
    }

    // 测试注解：通过在方法上使用SS的注解来控制特定方法的访问权限
    @RequestMapping("/update")
    @ResponseBody
    @Secured({"ROLE_sale", "ROLE_manager"})
    public String update() {
        return "hello update";
    }

    // 测试注解：通过在方法上使用SS的注解来控制特定方法的访问权限
    @RequestMapping("/preAuthorize")
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('admins')")
    public String preAuthorize() {
        System.out.println("preAuthorize");
        return "preAuthorize";
    }

}
