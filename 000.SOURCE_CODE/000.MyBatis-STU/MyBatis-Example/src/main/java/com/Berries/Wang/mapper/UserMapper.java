package com.Berries.Wang.mapper;

import com.Berries.Wang.pos.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    public abstract User selectById(@Param("id") Long id);
}
