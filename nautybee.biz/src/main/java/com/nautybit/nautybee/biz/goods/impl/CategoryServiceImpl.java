package com.nautybit.nautybee.biz.goods.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;
 
import com.nautybit.nautybee.biz.base.BaseServiceImpl;
import com.nautybit.nautybee.biz.goods.CategoryService;
import com.nautybit.nautybee.dao.goods.CategoryDao;
import com.nautybit.nautybee.entity.goods.Category;


@Service
public class CategoryServiceImpl extends BaseServiceImpl  implements CategoryService{

  @Autowired
  private CategoryDao categoryDao;

  public List<Category> getAll() {
    return super.getAll(categoryDao);
  }


  public Category getById(Long id) {
    return super.getById(categoryDao, id);
  }

  public boolean save(Category category) {
    return super.save(categoryDao, category);
  }

  public boolean deleteById(Long id) {
    return super.deleteById(categoryDao, id);
  }

  public int deleteByIds(Long[] ids) {
    return super.deleteByIds(categoryDao, ids);
  }
}
