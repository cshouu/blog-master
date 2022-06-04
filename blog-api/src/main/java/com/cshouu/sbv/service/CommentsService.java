package com.cshouu.sbv.service;

import com.cshouu.sbv.vo.Result;
import com.cshouu.sbv.vo.params.CommentParam;

public interface CommentsService {
    Result commentsByArticleId(Long id);

    Result comment(CommentParam commentParam);
}
