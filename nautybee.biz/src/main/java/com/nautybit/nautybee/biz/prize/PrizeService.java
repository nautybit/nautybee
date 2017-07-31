package com.nautybit.nautybee.biz.prize;

import java.util.List;
import java.util.Map;

import com.nautybit.nautybee.entity.prize.Prize;

public interface PrizeService {
  List<Prize> getAll();

  Prize getById(Long id);

  boolean save(Prize prize);

  boolean deleteById( Long id);

  int deleteByIds(Long[] ids);


}
