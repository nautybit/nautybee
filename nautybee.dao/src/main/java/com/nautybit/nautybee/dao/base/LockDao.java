package com.nautybit.nautybee.dao.base;

import org.apache.ibatis.annotations.Param;

public interface LockDao {

    int getLockById(@Param("id")Long id);

    int releaseLockById(@Param("id")Long id);
 
}
    
