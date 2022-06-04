package com.cshouu.sbv.dao.pojo;

import lombok.Data;

@Data
public class Article {
    public static final int Article_TOP = 1;
    public static final int Article_Common = 0;

    private Long id;
    private Integer commentCounts;
    private Long createDate;
    private String summary;
    private String title;
    private Integer viewCounts; //不能使用int基本类型，它有默认值0，mybatisplus的update会把不是null的内容更新到数据库
    private Integer weight; //同样不能有默认值
    private Long authorId;
    private Long bodyId;
    private Long categoryId;
}
