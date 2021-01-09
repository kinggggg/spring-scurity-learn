package com.zeek.security.securitydemo1.controller;

import org.springframework.web.bind.annotation.RequestMapping;
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

}
