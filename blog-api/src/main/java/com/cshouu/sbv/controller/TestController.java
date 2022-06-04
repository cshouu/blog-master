package com.cshouu.sbv.controller;

import com.cshouu.sbv.utils.UserThreadLocal;
import com.cshouu.sbv.vo.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/test")
public class TestController {
    @GetMapping
    public Result test(){
        System.out.println(UserThreadLocal.get());
        return Result.success(null);
    }
}
