package com.yyx.system;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.yyx.core.entity.ResponseEntity;
import com.yyx.utils.pageInfo.PageInfo;
import com.yyx.system.entity.AccessUserEntity;
import com.yyx.system.entity.User;

public interface IUserService {
	/**
	 * 更新密码
	 * 
	 */
	ResponseEntity renewal(User user);
	
	/***
	 * 创建密码
	 * @param record
	 * @param roleId
	 * @return
	 */
	ResponseEntity selectForCreateP();

	/*
	 * @fun 获取：用户对象
	 * @param map
	 * @return
	 */
	public ResponseEntity selectByMap(Map<String, Object> map);
	
	/*
	 * @fun 获取：菜单列表
	 * @param map
	 * @return
	 */
	public ResponseEntity selectMenuListByMap(Map<String,Object> map);
	
	/*
	 * @fun 获取：用户分页列表
	 * @param pageInfo
	 * @param map
	 * @return
	 */
	public ResponseEntity getUserPageInfo(PageInfo<JSON> pageInfo, Map<String, Object> map);
	
	/*
	 * @fun 插入用户对象
	 * @param userVo
	 * @param roleId
	 * @param accessUserEntity 
	 * @return
	 */
	public ResponseEntity insertSelective(User userVo,Long roleId, AccessUserEntity accessUserEntity);
	
	/*
	 * @fun 获取：用户列表
	 * @param map
	 * @return
	 */
	public ResponseEntity selectUserRoleByMap(Map<String,Object> map);
	
	/*
	 * @fun 更新：用户对象和中间对象
	 * @param record
	 * @param roleId
	 * @return
	 */
	public ResponseEntity updateByPrimaryKeySelective(User record,Long roleId);
	
	/*
	 * @fun 更新：用户对象
	 * @param user
	 * @return
	 */
	public ResponseEntity updateUserStatus(User userVo);
	
	/*
	 * @fun 批量删除:用户对象
	 * @param user
	 * @return
	 */
	public ResponseEntity deleteUsers(String strId);
	
	
	/*
	 * @fun 获取：用户对应角色
	 * @param map
	 * @return
	 */
	public ResponseEntity selectUserRoleMap(Map<String, Object> map);

	/*
	 * @fun 获取用户数
	 * @param deptId
	 * @return
	 */
	public ResponseEntity getUserCount(Long deptId);

	/*
	 * @fun 
	 * @param user
	 * @return
	 */
	public ResponseEntity updateByPrimaryKeySelective(User user);

	/*
	 * @fun 
	 * @param map
	 */
	public ResponseEntity selectByMapAccess(Map<String, Object> map);

	/*
	 * @fun 
	 * @param map
	 * @return
	 */
	public ResponseEntity selectBacklog(Map<String, Object> map);
	
	/* 更新考核用户对象
	 * @author zhangya
	 * @param map
	 * @return
	 */  
	public ResponseEntity updateKHUser(AccessUserEntity user);
	/*
	 * @Description:更新反馈状态
	 * @author Yangkai 2017/2/13 17:56
	 * @return
	 */
	ResponseEntity updateBacklog(Map<String,Object> map);
    /*
     * @Description:获取更新待办事项操作记录操作记录
     * @author Yangkai 2017/2/20 10:41
     * @return
     */
    ResponseEntity selectOperationByMap(Map<String, Object> operMap);
    /*
     * @Description: 合同中符合日期的插入待办事项。
     * @author Yangkai 2017/2/20 10:54
     * @return
     */
    ResponseEntity insertContractBcaklog(Map<String, Object> operMap);
    /*
     * @Description: 插入待办事项获取操作表
     * @author Yangkai 2017/2/20 10:55
     * @return
     */
    ResponseEntity insertOperationRecord(Map<String, Object> operMap);

    /**
     * 查询用户列表（员工关联时用到）
     * @param roleMap
     * @return
     */
	ResponseEntity selectUserListByMap(Map<String, Object> roleMap);

    /**
     * 关闭事项提醒
     * @param roleMap
     * @return
     */
    ResponseEntity closeFeedback(Map<String, Object> roleMap);
}
