package com.cshouu.sbv.service;

import com.cshouu.sbv.vo.Result;
import com.cshouu.sbv.vo.TagVo;
import org.springframework.stereotype.Component;

import java.util.List;

public interface TagService {
    List<TagVo> findTagsByArticleId(Long articleId);

    Result hots(int limit);

    Result findAll();

    Result findAllDetail();

    Result findDetailById(Long id);
}
