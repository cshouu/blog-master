package com.cshouu.sbv.service;

import com.cshouu.sbv.dao.pojo.SysUser;
import com.cshouu.sbv.vo.Result;
import com.cshouu.sbv.vo.UserVo;

public interface SysUserService {
    SysUser findUserById(Long id);

    SysUser findUser(String account, String password);

    Result findUserByToken(String token);

    SysUser findUserByAccount(String account);

    void save(SysUser sysUser);

    UserVo findUserVoById(Long authorId);
}
