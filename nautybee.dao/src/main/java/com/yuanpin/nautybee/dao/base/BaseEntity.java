package com.yuanpin.nautybee.dao.base;

import java.util.Date;

public abstract class BaseEntity extends IdEntity {

    /**
     * isDeleted字段是否删除，标记为Y，已删除
     */
    public static final String IS_DELETE_ENABLE = "Y";

    /**
     * isDeleted字段是否删除，标记为N，没删除
     */
    public static final String IS_DELETE_UNENABLE = "N";

    protected String isDeleted;
    protected Date gmtCreate;
    protected Long creator;
    protected Date gmtModified;
    protected Long modifier;

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Long getModifier() {
        return modifier;
    }

    public void setModifier(Long modifier) {
        this.modifier = modifier;
    }

    public void setDefaultBizValue() {
        if (id == null) {
            // 创建
            if (gmtCreate == null) {
                gmtCreate = new Date();
            }
            if (creator == null) {
                creator = 1L;
            }
            if (isDeleted == null) {
                isDeleted = IS_DELETE_UNENABLE;
            }
        }
        // 修改
        if (modifier == null) {
            modifier = 1L;
        }
        gmtModified = new Date();
    }

    public void setDefaultBizValue(Long userId) {

        if (userId == null) {
            setDefaultBizValue();
            return;
        }

        if (id == null) {
            // 创建
            if (gmtCreate == null) {
                gmtCreate = new Date();
            }
            if (creator == null) {
                creator = userId;
            }
            if (isDeleted == null) {
                isDeleted = IS_DELETE_UNENABLE;
            }
        }
        // 修改
        modifier = userId;
        gmtModified = new Date();
    }
}
