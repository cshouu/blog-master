package com.cshouu.sbv.service;

import com.cshouu.sbv.dao.pojo.SysUser;
import com.cshouu.sbv.vo.Result;
import com.cshouu.sbv.vo.params.LoginParams;

public interface LoginService {
    Result login(LoginParams loginParams);

    SysUser checkToken(String token);

    Result logout(String token);

    Result register(LoginParams params);
}
