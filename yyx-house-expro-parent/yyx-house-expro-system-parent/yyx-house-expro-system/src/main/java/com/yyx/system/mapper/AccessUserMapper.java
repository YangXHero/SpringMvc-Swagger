package com.yyx.system.mapper;

import com.yyx.system.entity.AccessUserEntity;

import java.util.Map;


public interface AccessUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AccessUserEntity record);

    int insertSelective(AccessUserEntity record);

    AccessUserEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AccessUserEntity record);

    int updateByPrimaryKey(AccessUserEntity record);

	/*
	 * @fun 
	 * @param map
	 * @return
	 */
	AccessUserEntity selectByMap(Map<String, Object> map);
	
	/* 更新考核用户对象
	 * @author zhangya
	 * @param record
	 * @return
	 */  
	public int updateKHUser(AccessUserEntity record);
}