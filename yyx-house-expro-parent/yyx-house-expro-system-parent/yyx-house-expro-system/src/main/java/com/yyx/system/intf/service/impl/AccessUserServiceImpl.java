package com.yyx.system.intf.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyx.system.entity.AccessUserEntity;
import com.yyx.system.intf.mapper.AccessUserMapper;
import com.yyx.system.intf.service.AccessUserService;
@Service("accessUserService")
public class AccessUserServiceImpl implements AccessUserService{
	
	@Autowired
	private AccessUserMapper accessUserMapper;
	@Override
	public int deleteByPrimaryKey(Long id) {
		return accessUserMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insertSelective(AccessUserEntity record) {
		return accessUserMapper.insertSelective(record);
	}

	@Override
	public AccessUserEntity selectByPrimaryKey(Long id) {
		return accessUserMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(AccessUserEntity record) {
		return accessUserMapper.updateByPrimaryKeySelective(record);
	}

	/* 
	 * @see com.yyx.zbhr.service.AccessUserService#selectByMap(java.util.Map)
	 * @fun 
	 * @author yangkai
	 */
	@Override
	public AccessUserEntity selectByMap(Map<String, Object> map) {
		return accessUserMapper.selectByMap(map);
	}

	/*
	 * @Description: 更新考核用户对象
	 * @author zhangya
	 * @param record
	 * @return
	 */
	@Override
	public int updateKHUser(AccessUserEntity record) {
		return accessUserMapper.updateKHUser(record);
	}
	
	

}
