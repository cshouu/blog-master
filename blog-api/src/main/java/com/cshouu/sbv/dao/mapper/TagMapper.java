package com.cshouu.sbv.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cshouu.sbv.dao.pojo.Tag;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagMapper extends BaseMapper<Tag> {

    List<Tag> findTagsByArticleId(Long articleId);

    List<Long> findHotTagIds(int limit);

    List<Tag> findTagsByTagIds(List<Long> tagIdList);
}
