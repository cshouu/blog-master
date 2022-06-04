package com.cshouu.sbv.service.impl;

import com.alibaba.fastjson.JSON;
import com.cshouu.sbv.dao.pojo.SysUser;
import com.cshouu.sbv.service.LoginService;
import com.cshouu.sbv.service.SysUserService;
import com.cshouu.sbv.utils.JWTUtils;
import com.cshouu.sbv.vo.ErrorCode;
import com.cshouu.sbv.vo.Result;
import com.cshouu.sbv.vo.params.LoginParams;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    private static final String salt = "cshouu!@#";

    @Override
    public Result login(LoginParams loginParams) {
        /**
         * 1.检查参数是否合法
         * 2.根据用户名密码去user表中查询是否存在
         * 3.如果不存在，登录失败
         * 4.如果存在，使用jwt生成token
         * 5.将token放入redis中，token：用户信息，设置过期时间
         * （登录认证的时候，先认证token字符串是否合法，去redis认证是否存在）
         */
        String account = loginParams.getAccount();
        String password = loginParams.getPassword();
        if(StringUtils.isBlank(account) || StringUtils.isBlank(password)){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
        }
        password = DigestUtils.md5Hex(password + salt);
        SysUser sysUser = sysUserService.findUser(account,password);
        if(sysUser==null){
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(),ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        String token = JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        return Result.success(token);
    }

    @Override
    public SysUser checkToken(String token) {
        if(StringUtils.isBlank(token)){
            return null;
        }
        Map<String,Object> stringObjectMap = JWTUtils.checkToken(token);
        if(stringObjectMap==null) {
            return null;
        }
        String userJson = redisTemplate.opsForValue().get("TOKEN_"+token);
        if(StringUtils.isBlank(userJson)){
            return null;
        }
        return JSON.parseObject(userJson, SysUser.class);
    }

    @Override
    public Result logout(String token) {
        redisTemplate.delete("TOKEN_"+token);
        return Result.success(null);
    }

    @Override
    public Result register(LoginParams params) {
        /**
         * 1.检查参数是否合法
         * 2.判断账户是否存在，存在返回账户已经被注册
         * 3.不存在，注册用户
         * 4.生成token
         * 5.存入redis，并返回
         * 6.注册过程加上事务
         */
        String account = params.getAccount();
        String password = params.getPassword();
        String nickname = params.getNickname();
        if(StringUtils.isBlank(account)
                || StringUtils.isBlank(password)
                || StringUtils.isBlank(nickname)){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
        }
        SysUser sysUser = sysUserService.findUserByAccount(account);
        if(sysUser!=null){
            return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(),ErrorCode.ACCOUNT_EXIST.getMsg());
        }
        sysUser = new SysUser();
        sysUser.setAccount(account);
        sysUser.setPassword(DigestUtils.md5Hex(password+salt));
        sysUser.setNickname(nickname);
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setAvatar("/static/img/logo.b3a48c0.png");
        sysUser.setAdmin(1); //true
        sysUser.setDeleted(0); //false
        sysUser.setSalt("");
        sysUser.setStatus("");
        sysUser.setEmail("");
        sysUserService.save(sysUser);

        String token = JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        return Result.success(token);
    }
}
