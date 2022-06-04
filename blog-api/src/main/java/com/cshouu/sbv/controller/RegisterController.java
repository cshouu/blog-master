package com.cshouu.sbv.controller;

import com.cshouu.sbv.service.LoginService;
import com.cshouu.sbv.vo.Result;
import com.cshouu.sbv.vo.params.LoginParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/register")
public class RegisterController {
    @Autowired
    private LoginService loginService;
    @PostMapping
    public Result register(@RequestBody LoginParams params){
        return loginService.register(params);
    }
}
