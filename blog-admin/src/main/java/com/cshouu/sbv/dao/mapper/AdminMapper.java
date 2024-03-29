package com.cshouu.sbv.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cshouu.sbv.dao.pojo.Admin;
import com.cshouu.sbv.dao.pojo.Permission;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminMapper extends BaseMapper<Admin> {
    @Select("select * from permission where id in (select permission_id from admin_permission where admin_id=#{adminId})")
    List<Permission> findPermissionByAdminId(Long adminId);
}
