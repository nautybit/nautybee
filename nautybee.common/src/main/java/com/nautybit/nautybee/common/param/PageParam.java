package com.nautybit.nautybee.common.param;


/**
 * 分页参数
 * Created by Minutch
 */
public class PageParam {
  private Integer pageSize = 10;   //单页显示条数，默认10
  private Integer curPage = 1;     //当前页，默认为第一页
  private Integer start = 0;       //默认第0个开始
  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public Integer getCurPage() {
    return curPage;
  }

  public void setCurPage(Integer curPage) {
    this.curPage = curPage;
  }

  /**
   * 当前页从那条记录开始
   *
   * @return 记录开始的index
   */
  public Integer getStart() {
    if (start == null || start < 0) {
      if (pageSize == null || curPage == null || curPage <= 0) {
        return 0;
      }
      return pageSize * (curPage - 1);
    } else {
      return start;
    }
  }

  /**
   * 当前页到那条记录为止
   *
   * @return 记录结束的index
   */
  public Integer getEnd() {
    return pageSize;
  }

  public void setStart(Integer start) {
    this.start = start;
  }
  
  
  
  
  public Integer getOffset(){
	  return getStart();
  }
  
  public Integer getLimit(){
	  return pageSize;
  }
}
