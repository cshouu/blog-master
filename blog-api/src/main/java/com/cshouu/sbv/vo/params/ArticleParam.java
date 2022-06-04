package com.cshouu.sbv.vo.params;

import com.cshouu.sbv.vo.CategoryVo;
import com.cshouu.sbv.vo.TagVo;
import lombok.Data;

import java.util.List;

@Data
public class ArticleParam {
    private Long id;
    private ArticleBodyParam body;
    private CategoryVo category;
    private String summary;
    private List<TagVo> tags;
    private String title;
}
