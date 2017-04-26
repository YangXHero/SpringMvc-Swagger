package com.yyx.system.intf.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.yyx.system.entity.User;
import com.yyx.system.intf.mapper.UserMapper;
import com.yyx.system.intf.service.UserService;
import com.yyx.utils.StringUtil;
import com.yyx.utils.pageInfo.PageInfo;
@Service
public class UserServiceImpl implements UserService {
	@Autowired(required = false)
	private UserMapper userMapper;
	/*@Autowired(required = false)
	private PersonInfoMapper persInfoMapper;*/

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public int insertSelective(User record,Long roleId) {
		if(StringUtil.isNotEmpty(record.getPassword())){
			record.setPassword(new Md5Hash(record.getPassword(),record.getLoginName(),2).toHex());
		}else{
			record.setPassword(new Md5Hash("123456",record.getLoginName(),2).toHex());
		}
		int k = userMapper.insertSelective(record);//用户表新增
		Map map = new HashMap();
		map.put("userId", record.getId());
		map.put("roleId", roleId);
		k = userMapper.insertUserRole(map);//用户角色表新增
		//k = persInfoMapper.insertNullPerson(record);
		return k;
	}

	/*
	 * 修改密码
	 * 
	 * @param userId
	 * @param newPassword
	 */
	@Override
	public int changePassword(String userId, String newPassword) {
		return userMapper.changePassword(userId, newPassword);
	}

	
	/*
	 * @Description: 更新用户
	 * @author zhangya
	 * @date 2016年5月11日
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public int updateByPrimaryKeySelective(User record,Long roleId) {
		int k = userMapper.updateByPrimaryKeySelective(record);//修改用户
		//k = persInfoMapper.updateNullPerson(record);
		Map map = new HashMap();
		map.put("userId", record.getId());
		List<Map<String, Object>> userRoleList = userMapper.selectUserRoleByMap(map);
		if (StringUtil.isNotNull(userRoleList) && userRoleList.size()> 0) {
			if(roleId!=userRoleList.get(0).get("roleId")){//用户角色有变化，修改用户对应角色
				map.put("id", userRoleList.get(0).get("id"));
				map.put("roleId", roleId);
				k=userMapper.updateUserRole(map);
			}
		}
		return k;
	}
	/*
	 * @Description: 更新用户
	 * @author zhangya
	 * @date 2016年5月11日
	 */
	public int updateByPrimaryKeySelective(User record) {
		int k = userMapper.updateByPrimaryKeySelective(record);//修改用户
		return k;
	}
	@Override
	public User selectByMap(Map<String, Object> map) {
		try {
			return userMapper.selectByMap(map);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public PageInfo<JSON> getUserPageInfo(
			PageInfo<JSON> pageInfo, Map<String, Object> map) {
		int start = (pageInfo.getPageNumber() - 1) * pageInfo.getPageSize();
		//==========================设置排序及排序顺序    start
	       if (StringUtil.isNotNull(pageInfo)
	            && StringUtil.isNotEmpty(pageInfo.getSortColum())) {
	               map.put("sortColum",pageInfo.getSortColum());
	               map.put("sortMethod",pageInfo.getSortMethod());
	        }
	        //==========================设置排序及排序顺序    end
			
			pageInfo.setRows(userMapper.getUserList(map, new RowBounds(start, pageInfo.getPageSize())));
			pageInfo.setTotal(userMapper.getUserListCount(map));
			return pageInfo;
	}

//	/*
//	 * 根据map查找用户
//	 * @param username
//	 * @return
//	 */
//	@Override
//	public SecurityUserEntity selectByMap(Map<String, Object> map) {
//		return userMapper.selectByMap(map);
//	}
//
//	/*
//	 * 根据用户名查找其角色
//	 * @param username
//	 * @return
//	 */
//	@Override
//	public Set<String> selectRoles(Map<String, Object> map) {
//		return userMapper.selectRoles(map);
//	}
//
//	/*
//	 * 根据用户名查找其权限
//	 * @param username
//	 * @return
//	 */
//	@Override
//	public Set<String> selectPermissions(Map<String, Object> map) {
//		return userMapper.selectPermissions(map);
//	}
//
//	/*
//	 * @fun 获取菜单权限
//	 * @param map
//	 * @return
//	 */
//	public List<Map<String,Object>> selectMenuListByMap(Map<String,Object> map) {
//		return userMapper.selectMenuListByMap(map);
//	}
//	
//	
//	/*
//	 * @fun 更新用户无效
//	 * @author yaofeng
//	 * @param securityUserEntity
//	 * @return
//	 */
//	public int delSecurityUserDisable(SecurityUserEntity securityUserEntity) {
//		return userMapper.delSecurityUserDisable(securityUserEntity);
//	}
//	
//	/*
//	 * @fun 根据机构查找：是否绑定角色
//	 * @author yaofeng
//	 * @param map
//	 * @return
//	 */
//	public List<Map<String,Object>> selectOrgRoleByMap(Map<String,Object> map) {
//		return userMapper.selectOrgRoleByMap(map);
//	}
//	
//	/*
//	 * @fun 根据角色id更新：组织机构id
//	 * @author yaofeng
//	 * @param map
//	 * @return
//	 */
//	public int updateOrgRole(Map<String,Object> map) {
//		return userMapper.updateOrgRole(map);
//	}
//
//	/*
//	 * @fun 获取用户分页列表
//	 * @param pageInfo
//	 * @param map
//	 * @return
//	 */
//	public PageInfo<Map<String,Object>> getUserPageInfo(PageInfo<Map<String,Object>> pageInfo,Map<String, Object> map) {
//		int start = (pageInfo.getPageNumber() - 1) * pageInfo.getPageSize();
//		
//	   //==========================设置排序及排序顺序    start
//       if (StringUtil.isNotNull(pageInfo)
//            && StringUtil.isNotEmpty(pageInfo.getSortColum())) {
//               map.put("sortColum",pageInfo.getSortColum());
//               map.put("sortMethod",pageInfo.getSortMethod());
//        }
//        //==========================设置排序及排序顺序    end
//		
//		pageInfo.setRows(securityUserMapper.getUserList(map, new RowBounds(start, pageInfo.getPageSize())));
//		pageInfo.setTotal(securityUserMapper.getUserListCount(map));
//		return pageInfo;
//	}
	
//	/*
//	 * @fun 更新用户或者用户角色中间对象
//	 * @param securityUserEntity 用户账号表
//	 * @param sysOrgEnity 用户机构表
//	 * @param roleId	角色id
//	 * @return
//	 */
//	public int updateUserOrUserRoleMiddle(SecurityUserEntity securityUserEntity,SysOrgEnity sysOrgEnity,String roleId) {
//		int k =0;
//		try {
//			if (StringUtil.isNotEmpty(roleId)) {
//				//先根据用户id，删除用户角色中间对象
//				Map<String,Object> map = new HashMap<String, Object>();
//				map.put("userId",sysOrgEnity.getFdid());
//				k = securityUserMapper.deleteUserRoleMiddleByMap(map);
//				//重新插入用户角色中间对象
//				map.put("roleId",roleId);
//				k = securityUserMapper.insertUserRoleMiddle(map);
//				
//				//=============绑定【用户数据角色】 start
//				//先删除：用户数据角色中间对象(根据用户id)
//				k = securityUserMapper.delUserDataRoleMiddle(map);
//				//重新绑定：用户数据角色中间对象
//				if (StringUtil.isNotEmpty(securityUserEntity.getDataRoleId())) {
//					String[] dataRoleIds = securityUserEntity.getDataRoleId().split(",");
//					for (int i=0;i<dataRoleIds.length;i++) {
//					    if (StringUtil.isNotEmpty(dataRoleIds[i])) {
//					    	map.put("dataRoleId",dataRoleIds[i]);
//					    	k = securityUserMapper.insertUserDataRoleMiddle(map);
//					    }
//					}
//					
//				}
//				//=============绑定【用户数据角色】 end
//			} 
//			//只更新用户信息
//			k = sysOrgService.updateSysOrg(sysOrgEnity,securityUserEntity);
//			//=================================用户信息更新成功了，需要将用户对应的权限：存放到redis里面   start
//			if (k > 0) {
//				//登录成功：应该将编辑的当前账号对应的权限存储到redis中
//				Map<String,Object> map = new HashMap<String,Object>();
//				map.put("userId",sysOrgEnity.getFdid());
//				//获取：权限
//				List<Map<String,Object>> menuList = securityUserMapper.selectMenuListByMap(map);
//				//将：权限存储到redis中
////				jRedisTemplate.setList(sysOrgEnity.getFdid(),menuList);
//				redisUtil.setList(sysOrgEnity.getFdid(),menuList);
//				
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		//=================================用户信息更新成功了，需要将用户对应的权限：存放到redis里面 end
//		return k;
//	}
	
	/*
	 * @fun 根据用户id获取角色id
	 * @author yaofeng
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> selectUserRoleByMap(Map<String,Object> map) {
		return userMapper.selectUserRoleByMap(map);
	}
//	
//	/*
//	 * @fun 删除用户角色中间对象
//	 * @param map
//	 * @return
//	 */
//	public int deleteUserRoleMiddleByMap(Map<String,Object> map) {
//		return securityUserMapper.deleteUserRoleMiddleByMap(map);
//	}
//
//	@Override
//	public List<Map<String, Object>> selectUserDataRoleByMap(String userId) {
//		// TODO Auto-generated method stub
//		return securityUserMapper.selectUserDataRoleByMap(userId);
//	}

	/*
	 * @Description: 更新用户状态
	 * @author zhangya
	 * @date 2016年5月11日
	 */
	@Override
	public int updateUserStatus(User user) {
		return userMapper.updateByPrimaryKeySelective(user);
	}

	/*
	 * @Description: 获取菜单权限
	 * @author zhangya
	 * @date 2016年5月16日
	 */
	@Override
	public List<JSON> selectMenuListByMap(Map<String, Object> map) {
		return userMapper.selectMenuListByMap(map);
	}

	/*
	 * @Description: 根据用户查找其角色
	 * @author zhangya
	 * @date 2016年5月31日
	 */
	@Override
	public Set<String> selectRoles(Map<String, Object> map) {
		return userMapper.selectRoles(map);
	}

	/*
	 * @Description: 批量修改
	 * @author zhangya
	 * @date 2016年5月31日
	 */
	@Override
	public int updateByIdsSelective(User user) {
		return userMapper.updateByIdsSelective(user);
	}

	/* 
	 * @see com.yyx.zbhr.service.UserService#getUserCount(com.yyx.zbhr.entity.User)
	 * @fun 
	 * @author yangkai
	 */
	@Override
	public int getUserCount(Map<String, Object> map) {
		return userMapper.getUserListCount(map);
	}

	/* 
	 * @see com.yyx.zbhr.service.UserService#selectBacklog(java.util.Map)
	 * @fun 
	 * @author yangkai
	 */
	@Override
	public List<JSON> selectBacklog(Map<String, Object> map) {
		return userMapper.selectBacklog(map);
	}

    @Override
    public int updateBacklog(Map<String, Object> map) {
        return userMapper.updateBacklog(map);
    }

    @Override
    public int selectOperationByMap(Map<String, Object> operMap) {
        return userMapper.selectOperationByMap(operMap);
    }

    @Override
    public int insertOperationRecord(Map<String, Object> operMap) {
        return userMapper.insertOperationRecord(operMap);
    }

    @Override
    public int insertContractBcaklog(Map<String, Object> operMap) {
        return userMapper.insertContractBcaklog(operMap);
    }

	@Override
	public List<JSON> selectUserListByMap(Map<String, Object> roleMap) {
		// TODO Auto-generated method stub
		 return userMapper.selectUserListByMap(roleMap);
	}

	/***
	 * 创建密码
	 * @param record
	 * @param roleId
	 * @return
	 */
	@Override
	public List<User> selectForCreateP() {
		return userMapper.selectForCreateP();
	}

    @Override
    public int closeFeedback(Map<String, Object> roleMap) {
        return userMapper.closeFeedback(roleMap);
    }

    @Override
	public int renewal(User user) {
		return userMapper.updateByPrimaryKeySelective(user);
	}	
}	
