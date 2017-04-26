package com.yyx.system.intf.service;

import com.yyx.system.entity.AccessUserEntity;

import java.util.Map;

public interface AccessUserService {
	/*
	 * @fun 根据id删除
	 * @param id
	 * @return
	 */
	public int deleteByPrimaryKey(Long id);
	/*
	 * @fun 插入
	 * @param record
	 * @return
	 */
	public int insertSelective(AccessUserEntity record);
	/*
	 * @fun 根据id查询
	 * @param id
	 * @return
	 */
	public AccessUserEntity selectByPrimaryKey(Long id);
	/*
	 * @fun 更新教育背景
	 * @param record
	 * @return
	 */
	public int updateByPrimaryKeySelective(AccessUserEntity record);
	/*
	 * @fun 
	 * @param map
	 * @return
	 */
	public AccessUserEntity selectByMap(Map<String, Object> map);
	
	/* 更新考核用户对象
	 * @author zhangya
	 * @param record
	 * @return
	 */  
	public int updateKHUser(AccessUserEntity record);
}
