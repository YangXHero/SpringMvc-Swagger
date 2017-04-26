package com.yyx.system.intf.mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.session.RowBounds;

import com.alibaba.fastjson.JSON;
import com.yyx.system.entity.User;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKeyWithBLOBs(User record);

    int updateByPrimaryKey(User record);
    
    /**
     * 新建密码
     * @param userId
     * @param newPassword
     * @return
     */
    List<User> selectForCreateP();
    
    /*
	 * 修改密码
	 * 
	 * @param userId
	 * @param newPassword
	 */
	public int changePassword(String userId, String newPassword);
	
	/*
	 * 根据map查找用户
	 * @return
	 */
	public User selectByMap(Map<String,Object> map);
	
	/*
	 * @Description: 插入用户角色中间表
	 * @author zhangya
	 * @date 2016年5月10日
	 */
	public int insertUserRole(Map<String,Object> map);
	
	/*
	 * @fun 获取用户列表
	 * @param map
	 * @param rowBounds
	 * @return
	 */
	public List<JSON> getUserList(Map<String,Object> map,RowBounds rowBounds);
	
	/*
	 * @fun 获取用户总条数
	 * @param map
	 * @return
	 */
	public int getUserListCount(Map<String,Object> map);
	
	/*
	 * @Description: 根据用户id获取角色id
	 * @author zhangya
	 * @date 2016年5月11日
	 */
	public List<Map<String,Object>> selectUserRoleByMap(Map<String,Object> map);
	
	/*
	 * 根据角色id修改用户对应角色
	 * @Description: TODO
	 * @author zhangya
	 * @date 2016年5月11日
	 */
	public int updateUserRole(Map<String,Object> map);
	
	/*
	 * @fun 获取菜单权限
	 * @param map
	 * @return
	 */
	public List<JSON> selectMenuListByMap(Map<String,Object> map); 
	
	
	/*
	 * 根据用户名查找其角色
	 * 
	 * @param username
	 * @return
	 */
	public Set<String> selectRoles(Map<String,Object> map);

	/*
	 * @fun 批量删除用户
	 * @param strId
	 * @return
	 */
	public int updateByIdsSelective(User record);

	/*
	 * @fun 
	 * @param map
	 * @return
	 */
	List<JSON> selectBacklog(Map<String, Object> map);

    int updateBacklog(Map<String, Object> map);

    int selectOperationByMap(Map<String, Object> operMap);

    int insertContractBcaklog(Map<String, Object> operMap);

    int insertOperationRecord(Map<String, Object> operMap);

	List<JSON> selectUserListByMap(Map<String, Object> roleMap);

    int closeFeedback(Map<String, Object> roleMap);
}