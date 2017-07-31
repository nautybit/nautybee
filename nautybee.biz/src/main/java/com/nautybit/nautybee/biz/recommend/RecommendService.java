package com.nautybit.nautybee.biz.recommend;

import java.util.List;
import java.util.Map;

import com.nautybit.nautybee.entity.recommend.Recommend;

public interface RecommendService {
  List<Recommend> getAll();

  Recommend getById(Long id);

  boolean save(Recommend recommend);

  boolean deleteById( Long id);

  int deleteByIds(Long[] ids);


}
