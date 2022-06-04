package com.cshouu.sbv.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cshouu.sbv.dao.mapper.AdminMapper;
import com.cshouu.sbv.dao.pojo.Admin;
import com.cshouu.sbv.dao.pojo.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private AdminMapper adminMapper;

    public Admin findAdminByUsername(String username){
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getUsername,username);
        queryWrapper.last("limit 1");
        return adminMapper.selectOne(queryWrapper);
    }

    public List<Permission> findPermissionByAdminId(Long id) {
        return adminMapper.findPermissionByAdminId(id);
    }
}
