package com.cshouu.sbv.service;

import com.cshouu.sbv.vo.CategoryVo;
import com.cshouu.sbv.vo.Result;

import java.util.List;

public interface CategoryService {
    CategoryVo findCategoryById(Long categoryId);

    Result findAll();

    Result findAllDetail();

    Result categoriesDetailById(Long id);
}
