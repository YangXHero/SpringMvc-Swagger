package com.yyx.system.intf.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.yyx.system.entity.User;
import com.yyx.utils.pageInfo.PageInfo;

public interface UserService {
	/**
	 * 更新密码
	 */
	int renewal(User user);
	
	/***
	 * 创建密码
	 * @param record
	 * @param roleId
	 * @return
	 */
	List<User> selectForCreateP();
	
	/*
	 * @Description: 用户新增
	 * @author zhangya
	 * @date 2016年5月11日
	 */
	public int insertSelective(User record,Long roleId);

	/*
	 * 修改密码
	 * 
	 * @param userId
	 * @param newPassword
	 */
	public int changePassword(String userId, String newPassword);
	
	
	/*
	 * @Description: 用户修改
	 * @author zhangya
	 * @date 2016年5月11日
	 */
	public int updateByPrimaryKeySelective(User record,Long roleId);

	/*
	 * 根据map查找用户
	 * @param username
	 * @return
	 */
	public User selectByMap(Map<String,Object> map);


	/*
	 * @fun 获取用户分页列表
	 * @param pageInfo
	 * @param map
	 * @return
	 */
	public PageInfo<JSON> getUserPageInfo(PageInfo<JSON> pageInfo,Map<String, Object> map);

	/*
	 * @fun 根据用户id获取角色id
	 * @author yaofeng
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> selectUserRoleByMap(Map<String,Object> map);

	/*
	 * @Description: 更新用户状态
	 * @author zhangya
	 * @date 2016年5月11日
	 */
	public int updateUserStatus(User user);
	
	/*
	 * @Description: 获取菜单权限
	 * @author zhangya
	 * @date 2016年5月16日
	 */
	public List<JSON> selectMenuListByMap(Map<String,Object> map);
	
	/*
	 * 根据用户查找其角色
	 * @param username
	 * @return
	 */
	public Set<String> selectRoles(Map<String,Object> map);

	/*
	 * @fun 批量更新用户
	 * @param strId
	 * @return 
	 */
	public int updateByIdsSelective(User user);

	/*
	 * @fun 获取用户数量
	 * @param user
	 * @return
	 */
	public int getUserCount(Map<String,Object> map);

	/*
	 * @fun 
	 * @param user
	 * @return
	 */
	public int updateByPrimaryKeySelective(User user);

	/*
	 * @fun 
	 * @param map
	 * @return
	 */
	public List<JSON> selectBacklog(Map<String, Object> map);
    /*
     * @Description:更新待办状态
     * @author Yangkai 2017/2/13 18:01
     * @return
     */
    int updateBacklog(Map<String, Object> map);
    /*
     * @Description:更新待办状态
     * @author Yangkai 2017/2/13 18:01
     * @return
     */
    int selectOperationByMap(Map<String, Object> operMap);

    int insertOperationRecord(Map<String, Object> operMap);

    int insertContractBcaklog(Map<String, Object> operMap);

	List<JSON> selectUserListByMap(Map<String, Object> roleMap);

    /**
     * 关闭首页事项显示
     * @param roleMap
     * @return
     */
    int closeFeedback(Map<String, Object> roleMap);
}
