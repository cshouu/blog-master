package com.cshouu.sbv.controller;

import com.cshouu.sbv.dao.pojo.Permission;
import com.cshouu.sbv.service.PermissionService;
import com.cshouu.sbv.vo.Result;
import com.cshouu.sbv.vo.param.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private PermissionService permissionService;

    @PostMapping("permission/permissionList")
    public Result listPermission(@RequestBody PageParam pageParam){
        return permissionService.listPermission(pageParam);
    }

    @PostMapping("permission/add")
    public Result add(@RequestBody Permission permission){
        return permissionService.add(permission);
    }

    @PostMapping("permission/update")
    public Result update(@RequestBody Permission permission){
        return permissionService.update(permission);
    }

    @GetMapping("permission/delete/{id}")
    public Result delete(@PathVariable("id") Long id){
        return permissionService.delete(id);
    }
}
