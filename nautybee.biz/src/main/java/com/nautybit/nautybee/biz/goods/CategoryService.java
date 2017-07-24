package com.nautybit.nautybee.biz.goods;

import java.util.List;
import java.util.Map;

import com.nautybit.nautybee.entity.goods.Category;

public interface CategoryService {
  List<Category> getAll();

  Category getById(Long id);

  boolean save(Category category);

  boolean deleteById( Long id);

  int deleteByIds(Long[] ids);


}
