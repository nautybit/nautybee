package com.nautybit.nautybee.dao.recommend;

import com.nautybit.nautybee.dao.base.BaseDao;
import com.nautybit.nautybee.dao.common.MyBatisRepository;
import com.nautybit.nautybee.entity.recommend.Recommend;
import com.nautybit.nautybee.view.recommend.RecommendView;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisRepository
public interface RecommendDao extends BaseDao<Recommend> {

    Recommend selectByToUser(String toUser);
    List<RecommendView> selectByFromUser(String fromUser);

}
