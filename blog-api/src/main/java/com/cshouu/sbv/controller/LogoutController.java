package com.cshouu.sbv.controller;

import com.cshouu.sbv.service.LoginService;
import com.cshouu.sbv.vo.Result;
import com.cshouu.sbv.vo.params.LoginParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/logout")
public class LogoutController {

    @Autowired
    private LoginService loginService;

    @GetMapping
    public Result logout(@RequestHeader("Authorization") String token){
        return loginService.logout(token);
    }
}
