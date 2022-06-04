package com.cshouu.sbv.controller;

import com.cshouu.sbv.service.LoginService;
import com.cshouu.sbv.vo.Result;
import com.cshouu.sbv.vo.params.LoginParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public Result login(@RequestBody LoginParams loginParams){
        return loginService.login(loginParams);
    }
}
