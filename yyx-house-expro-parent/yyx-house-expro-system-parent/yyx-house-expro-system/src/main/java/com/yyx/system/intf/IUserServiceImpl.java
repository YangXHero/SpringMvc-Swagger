package com.yyx.system.intf;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yyx.core.entity.ErrorResponseEntity;
import com.yyx.core.entity.ResponseEntity;
import com.yyx.core.entity.SuccessResponseEntity;
import com.yyx.system.entity.AccessUserEntity;
import com.yyx.system.entity.User;
import com.yyx.system.IUserService;
import com.yyx.system.intf.service.AccessUserService;
import com.yyx.system.intf.service.UserService;
import com.yyx.utils.ConstantsErrorMsg;
import com.yyx.utils.StringUtil;
import com.yyx.utils.pageInfo.PageInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IUserServiceImpl implements IUserService {
private static Logger logger = LoggerFactory.getLogger(IUserServiceImpl.class);
	
	@Autowired(required = false)
	private UserService userService;

    @Autowired(required = false)
	private AccessUserService accessUserService;
	/*
	 * @fun 获取：用户对象
	 * @param map
	 * @return
	 */
	public ResponseEntity selectByMap(Map<String, Object> map) {
		try {
			User user = userService.selectByMap(map);
			
			logger.info("=====成功获取：用户对象==="+JSON.toJSONString(user));
			//成功：返回对象
			if(StringUtil.isNotNull(user)){
				return new SuccessResponseEntity(user);
			}else{
				return new ErrorResponseEntity();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ErrorResponseEntity(ConstantsErrorMsg.system_error_msg);
		}
	}

	/*
	 * @fun 获取：菜单列表
	 * @param map
	 * @return
	 */
	public ResponseEntity selectMenuListByMap(Map<String,Object> map) {
		try {
			List<JSON> mapList = userService.selectMenuListByMap(map);
			
			logger.info("=====成功获取：菜单列表==="+JSON.toJSONString(mapList));
			//成功：返回对象
			return new SuccessResponseEntity(mapList);
		} catch (Exception e) {
			e.printStackTrace();
			return new ErrorResponseEntity(ConstantsErrorMsg.system_error_msg);
		}
	}
	
	/*
	 * @fun 获取：用户分页列表
	 * @param pageInfo
	 * @param map
	 * @return
	 */
	public ResponseEntity getUserPageInfo(PageInfo<JSON> pageInfo, Map<String, Object> map) {
		try {
			return new SuccessResponseEntity(userService.getUserPageInfo(pageInfo, map));
		} catch (Exception e) {
			e.printStackTrace();
			return new ErrorResponseEntity(ConstantsErrorMsg.system_error_msg);
		}
	}
	
	
	/*
	 * @fun 插入用户对象
	 * @param User
	 * @param roleId
	 * @return
	 */
	public ResponseEntity insertSelective(User User,Long roleId, AccessUserEntity accessUserEntity) {
		try {
			User user = JSONObject.parseObject(JSON.toJSONString(User),User.class);
			
			//插入
			int k = userService.insertSelective(user, roleId);
			if (k > 0) {
				logger.info("=====成功插入：用户对象==="+JSON.toJSONString(user));
				accessUserEntity.setUserId(user.getId());
				k = accessUserService.insertSelective(accessUserEntity);
				if (k > 0) {
					logger.info("=====成功插入：用户对象==="+JSON.toJSONString(accessUserEntity));
					return new SuccessResponseEntity();
				}else {
					logger.info("=====失败插入：用户对象==="+JSON.toJSONString(user));
					
					return new ErrorResponseEntity("失败插入：用户对象");
				}
			} else {
				logger.info("=====失败插入：用户对象==="+JSON.toJSONString(user));
				
				return new ErrorResponseEntity("失败插入：用户对象");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ErrorResponseEntity(ConstantsErrorMsg.system_error_msg);
		}
	}
	
	/*
	 * @fun 获取：用户列表
	 * @param map
	 * @return
	 */
	public ResponseEntity selectUserRoleByMap(Map<String,Object> map) {
		try {
			
			return new SuccessResponseEntity(userService.selectUserRoleByMap(map));
		} catch (Exception e) {
			e.printStackTrace();
			return new ErrorResponseEntity(ConstantsErrorMsg.system_error_msg);
		}
	}
	
	/*
	 * @fun 更新：用户对象
	 * @param record
	 * @param roleId
	 * @return
	 */
	public ResponseEntity updateByPrimaryKeySelective(User record,Long roleId) {
		try {
			User user = JSONObject.parseObject(JSON.toJSONString(record),User.class);
			
			//插入
			int k = userService.updateByPrimaryKeySelective(user, roleId);
			if (k > 0) {
				logger.info("=====成功更新：用户对象==="+JSON.toJSONString(user));
				
				return new SuccessResponseEntity();
			} else {
				logger.info("=====失败更新：用户对象==="+JSON.toJSONString(user));
				
				return new ErrorResponseEntity("失败更新：用户对象");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ErrorResponseEntity(ConstantsErrorMsg.system_error_msg);
		}
	}
	
	/*
	 * @fun 更新：用户对象
	 * @param user
	 * @return
	 */
	public ResponseEntity updateUserStatus(User User) {
		try {
			User user = JSONObject.parseObject(JSON.toJSONString(User),User.class);
			
			//插入
			int k = userService.updateUserStatus(user);
			if (k > 0) {
				logger.info("=====成功更新：用户对象==="+JSON.toJSONString(user));
				
				return new SuccessResponseEntity();
			} else {
				logger.info("=====失败更新：用户对象==="+JSON.toJSONString(user));
				
				return new ErrorResponseEntity("失败更新：用户对象");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ErrorResponseEntity(ConstantsErrorMsg.system_error_msg);
		}
	}

	/*
	 * @Description: 获取用户对应角色
	 * @author zhangya
	 * @date 2016年5月31日
	 */
	@Override
	public ResponseEntity selectUserRoleMap(Map<String, Object> map) {
		try {
			return new SuccessResponseEntity(userService.selectRoles(map));
		} catch (Exception e) {
			e.printStackTrace();
			return new ErrorResponseEntity(ConstantsErrorMsg.system_error_msg);
		}
	}
	
	/*
	 * @Description: 批量删除用户
	 * @author yangkai
	 * @date 2016年10月28日
	 */@Override
	public ResponseEntity deleteUsers(String strId) {
		try {
			User user= new User();
			user.setIds(strId.split(","));
			user.setHasvalid("-1");
			return new SuccessResponseEntity(userService.updateByIdsSelective(user));
		} catch (Exception e) {
			e.printStackTrace();
			return new ErrorResponseEntity(ConstantsErrorMsg.system_error_msg);
		}
	}

	/* 
	 * @see com.yyx.zbhr.service.MUserService#getUserCount(java.lang.Long)
	 * @fun 获取用户数
	 * @author yangkai
	 */
	public ResponseEntity getUserCount(Long deptId) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("deptId", deptId);
			return new SuccessResponseEntity(userService.getUserCount(map));
		} catch (Exception e) {
			e.printStackTrace();
			return new ErrorResponseEntity(ConstantsErrorMsg.system_error_msg);
		}
	}
	/*
	 * @fun 更新：用户对象
	 * @param record
	 * @param roleId
	 * @return
	 */
	public ResponseEntity updateByPrimaryKeySelective(User record) {
		try {
			User user = JSONObject.parseObject(JSON.toJSONString(record),User.class);
			
			//插入
			int k = userService.updateByPrimaryKeySelective(user);
			if (k > 0) {
				logger.info("=====成功更新：用户对象==="+JSON.toJSONString(user));
				
				return new SuccessResponseEntity();
			} else {
				logger.info("=====失败更新：用户对象==="+JSON.toJSONString(user));
				
				return new ErrorResponseEntity("失败更新：用户对象");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ErrorResponseEntity(ConstantsErrorMsg.system_error_msg);
		}
	}

	/* 
	 * @see com.yyx.zbhr.service.MUserService#selectByMapAccess(java.util.Map)
	 * @fun 
	 * @author yangkai
	 */
	public ResponseEntity selectByMapAccess(Map<String, Object> map) {
		try {
			AccessUserEntity user = accessUserService.selectByMap(map);
			
			logger.info("=====成功获取：用户对象==="+JSON.toJSONString(user));
			//成功：返回对象
			return new SuccessResponseEntity(user);
		} catch (Exception e) {
			e.printStackTrace();
			return new ErrorResponseEntity(ConstantsErrorMsg.system_error_msg);
		}
	}

	/* 
	 * @see com.yyx.zbhr.service.MUserService#selectBacklog(java.util.Map)
	 * @fun 
	 * @author yangkai
	 */
	public ResponseEntity selectBacklog(Map<String, Object> map) {
		try {
			logger.info("=====成功获取：用户待办对象==="+JSON.toJSONString(map));
			//成功：返回对象
			return new SuccessResponseEntity(userService.selectBacklog(map));
		} catch (Exception e) {
			e.printStackTrace();
			return new ErrorResponseEntity(ConstantsErrorMsg.system_error_msg);
		}
	}

    public ResponseEntity updateBacklog(Map<String, Object> map) {
        try {
            return new SuccessResponseEntity(userService.updateBacklog(map));
        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorResponseEntity(ConstantsErrorMsg.system_error_msg);
        }
    }

    /*
         * @Description: 更新考核用户对象
         * @author zhangya
         * @param map
         * @return
         */
	public ResponseEntity updateKHUser(AccessUserEntity user) {
		try {
			//插入
			int k = accessUserService.updateByPrimaryKeySelective(user);
			if (k > 0) {
				logger.info("=====成功更新：考核用户对象==="+JSON.toJSONString(user));
				return new SuccessResponseEntity();
			}else {
				logger.info("=====失败更新：考核用户对象==="+JSON.toJSONString(user));
				
				return new ErrorResponseEntity("失败更新：考核用户对象");
			} 
		} catch (Exception e) {
			e.printStackTrace();
			return new ErrorResponseEntity(ConstantsErrorMsg.system_error_msg);
		}
	}

    public ResponseEntity selectOperationByMap(Map<String, Object> operMap) {
        try {
            return new SuccessResponseEntity(userService.selectOperationByMap(operMap));
        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorResponseEntity(ConstantsErrorMsg.system_error_msg);
        }
    }

    public ResponseEntity insertContractBcaklog(Map<String, Object> operMap) {
        try {
            return new SuccessResponseEntity(userService.insertContractBcaklog(operMap));
        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorResponseEntity(ConstantsErrorMsg.system_error_msg);
        }
    }

    public ResponseEntity insertOperationRecord(Map<String, Object> operMap) {
        try {
            return new SuccessResponseEntity(userService.insertOperationRecord(operMap));
        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorResponseEntity(ConstantsErrorMsg.system_error_msg);
        }
    }

	public ResponseEntity selectUserListByMap(Map<String, Object> roleMap) {
		try {
            return new SuccessResponseEntity(userService.selectUserListByMap(roleMap));
        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorResponseEntity(ConstantsErrorMsg.system_error_msg);
        }
	}

    public ResponseEntity closeFeedback(Map<String, Object> roleMap) {
        try {
            return new SuccessResponseEntity(userService.closeFeedback(roleMap));
        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorResponseEntity(ConstantsErrorMsg.system_error_msg);
        }
    }

    /***
	 * 创建密码
	 * @param record
	 * @param roleId
	 * @return
	 */
	public ResponseEntity selectForCreateP() {
		try {
            return new SuccessResponseEntity(userService.selectForCreateP());
        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorResponseEntity(ConstantsErrorMsg.system_error_msg);
        }
	}

	public ResponseEntity renewal(User user) {
		try {
            return new SuccessResponseEntity(userService.renewal(user));
        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorResponseEntity(ConstantsErrorMsg.system_error_msg);
        }
	}
}
