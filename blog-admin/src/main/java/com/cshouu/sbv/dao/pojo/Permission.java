package com.cshouu.sbv.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Permission {
    @TableId(type = IdType.AUTO) //自增id
    private Long id;
    private String name;
    private String path;
    private String description;
}
