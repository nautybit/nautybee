package com.nautybit.nautybee.common.param.wx;

import lombok.Data;

import java.util.List;

/**
 * Created by UFO on 17/7/15.
 */
@Data
public class ArticleMessage extends BaseMessage{
    private Integer ArticleCount;
    private List<ArticleItem> Articles;
}
