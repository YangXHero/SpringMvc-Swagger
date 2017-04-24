package com.yyx.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.redisson.RedissonClient;

import redis.clients.jedis.JedisCluster;

import com.alibaba.fastjson.JSON;
import com.yyx.utils.ConstantRedisKeys;
import com.yyx.utils.StringUtil;

public class RedisCache {

	private static final Log logger = LogFactory.getLog(RedisCache.class);

	public static final String SCORE_MAX = "+inf";

	public static final String SCORE_MIN = "-inf";

	/*
	 * 缓存中存入map结构的数据
	 * 
	 * @param key
	 * @param map
	 * @param expireSeconds 值>0则设置失效时间(单位秒);值<=0,则没有失效时间
	 */
	public static void setHashMapString(final String key, final Map<String, String> map, final int expireSeconds) {
		if (StringUtils.isBlank(key) || map.size() == 0) {
			return;
		}
		try {
			JedisCluster cluster = RedisUtils.getJedisCluster();
			cluster.hmset(ConstantRedisKeys.REDIS_SHIRO_LOGIN_KEY+key, map);
			if (expireSeconds > 0) {
				cluster.expire(ConstantRedisKeys.REDIS_SHIRO_LOGIN_KEY+key, expireSeconds);
			}
		} catch (Exception e) {
			logger.error("set cache exception，key is ： " + key, e);
		}
	}
	
	/*
	 * 获取缓存中Map结构中某个field对应的值
	 * 
	 * @param key
	 * @param field
	 */
	public static String hGet(final String key, final String field) {
		String fieldValue = null;
		if (StringUtils.isBlank(key) || StringUtils.isBlank(field)) {
			return null;
		}
		try {
			JedisCluster cluster = RedisUtils.getJedisCluster();
			fieldValue = cluster.hget(ConstantRedisKeys.REDIS_SHIRO_LOGIN_KEY+key, field);
		} catch (Exception e) {
			logger.error("set cache exception，key is ： " + key, e);
		}
		return fieldValue;
	}

	/*
	 * 获取缓存对象
	 * 
	 * @param key
	 */
	public static Map<String, String> hGetAll(final String key) {
		Map<String, String> obj = null;
		if (StringUtils.isBlank(key)) {
			return obj;
		}
		try {
			JedisCluster cluster = RedisUtils.getJedisCluster();
			obj = cluster.hgetAll(ConstantRedisKeys.REDIS_SHIRO_LOGIN_KEY+key);
		} catch (Exception e) {
			logger.error("hGetAll cache exception，key is ： " + key, e);
		}
		return obj;
	}
	
	/*
	 * zset增加元素
	 * 
	 * @param key
	 * @param score
	 * @param obj
	 */
	public static void zAddObj(final String key, final double score, final Object obj) {
		if (StringUtils.isBlank(key) || obj == null) {
			return;
		}
		JedisCluster cluster = RedisUtils.getJedisCluster();
		try {
			if (obj instanceof String) {
				cluster.zadd(ConstantRedisKeys.REDIS_SHIRO_LOGIN_KEY+key, score, obj.toString());
			} else {
				cluster.zadd(ConstantRedisKeys.REDIS_SHIRO_LOGIN_KEY+key, score, JSON.toJSONString(obj));
			}
		} catch (Exception e) {
			logger.error("zadd exception，key is ： " + key, e);
		}
	}

	/*
	 * 获取zset分页数据
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @param clz
	 * @return
	 */
	public static List<?> zGetObj(final String key, long start, long end, final Class<?> clz) {
		if (StringUtils.isBlank(key)) {
			return null;
		}
		JedisCluster cluster = RedisUtils.getJedisCluster();
		try {
			Set<String> value = cluster.zrevrange(ConstantRedisKeys.REDIS_SHIRO_LOGIN_KEY+key, start, end);
			List<String> list = new ArrayList<String>(value);
			if (value != null && clz != null) {
				return list;
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error("zget exception，key is ： " + key, e);
			return null;
		}
	}

	/*
	 * 获取zget分页数据，根据分数区间
	 * 
	 * @param key
	 * @param max
	 * @param min
	 * @param offset
	 * @param count
	 * @param clz
	 * @return
	 */
	public static List<?> zGetObjByScore(final String key, double max, double min, int offset, int count,
			final Class<?> clz) {
		if (StringUtils.isBlank(key)) {
			return null;
		}
		JedisCluster cluster = RedisUtils.getJedisCluster();
		try {
			Set<String> value = cluster.zrevrangeByScore(ConstantRedisKeys.REDIS_SHIRO_LOGIN_KEY+key, max, min, offset, count);
			List<String> list = new ArrayList<String>(value);
			if (value != null && clz != null) {
				return list;
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error("zget by score exception，key is ： " + key, e);
			return null;
		}
	}

	/*
	 * 获取zget分页数据，根据分数区间
	 * 
	 * @param key
	 * @param max
	 * @param min
	 * @param offset
	 * @param count
	 * @param clz
	 * @return
	 */
	public static List<?> zGetObjByScore(final String key, String max, String min, int offset, int count,
			final Class<?> clz) {
		if (StringUtils.isBlank(key)) {
			return null;
		}
		JedisCluster cluster = RedisUtils.getJedisCluster();
		try {
			Set<String> value = cluster.zrevrangeByScore(ConstantRedisKeys.REDIS_SHIRO_LOGIN_KEY+key, max, min, offset, count);
			List<String> list = new ArrayList<String>(value);
			if (value != null && clz != null) {
				return list;
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error("zget by score exception，key is ： " + key, e);
			return null;
		}
	}

	public static long zCount(final String key) {
		if (StringUtils.isBlank(key)) {
			return 0l;
		}
		JedisCluster cluster = RedisUtils.getJedisCluster();
		try {
			Long count = cluster.zcount(ConstantRedisKeys.REDIS_SHIRO_LOGIN_KEY+key, SCORE_MIN, SCORE_MAX);
			return count;
		} catch (Exception e) {
			logger.error("zcount by score exception，key is ： " + key, e);
			return 0l;
		}
	}

	public static long zCountByScore(final String key, String min) {
		if (StringUtils.isBlank(key)) {
			return 0l;
		}
		if (StringUtils.isBlank(min)) {
			logger.error("zcount by score ，min is necessary，otherwise return zero。key is ： " + key);
			return 0l;
		}
		JedisCluster cluster = RedisUtils.getJedisCluster();
		try {
			Long count = cluster.zcount(ConstantRedisKeys.REDIS_SHIRO_LOGIN_KEY+key, "(" + min, SCORE_MAX);
			return count;
		} catch (Exception e) {
			logger.error("zcount by score exception，key is ： " + key, e);
			return 0l;
		}
	}

	public static long zRem(final String key, String member) {
		if (StringUtils.isBlank(key)) {
			return 0l;
		}
		if (StringUtils.isBlank(member)) {
			logger.error("zrem member ，member is necessary，otherwise return zero。member is ： " + member);
			return 0l;
		}
		JedisCluster cluster = RedisUtils.getJedisCluster();
		try {
			Long count = cluster.zrem(ConstantRedisKeys.REDIS_SHIRO_LOGIN_KEY+key, member);
			return count;
		} catch (Exception e) {
			logger.error("zRem by score exception，member is ： " + member, e);
			return 0l;
		}
	}

	/*
	 * 缓存对象
	 * 
	 * @param key
	 * @param value
	 * @param expire
	 */
	public static void set(final String key, final Object value) {
		set(key, value, -1);
	}

	public static void set(final String key, final Object value, Integer expire) {
		if (StringUtils.isBlank(key) || value == null) {
			return;
		}
		JedisCluster cluster = RedisUtils.getJedisCluster();
		expire = expire == null ? 0 : expire;
		try {
			if (value instanceof String) {
				if (expire > 0) {
					cluster.setex(ConstantRedisKeys.REDIS_SHIRO_LOGIN_KEY+key, expire, value.toString());
				} else {
					cluster.set(ConstantRedisKeys.REDIS_SHIRO_LOGIN_KEY+key, value.toString());
				}
			} else {
				if (expire > 0) {
					cluster.setex(ConstantRedisKeys.REDIS_SHIRO_LOGIN_KEY+key, expire, JSON.toJSONString(value));
				} else {
					cluster.set(ConstantRedisKeys.REDIS_SHIRO_LOGIN_KEY+key, JSON.toJSONString(value));
				}
			}
		} catch (Exception e) {
			logger.error("set cache exception，key is ： " + key, e);
		}
	}

	/*
	 * 获取缓存对象
	 * 
	 * @param key
	 * @param clz
	 * @return
	 * @return
	 */
	public static String get(String key) {
		if (StringUtils.isBlank(key)) {
			return null;
		}
		JedisCluster cluster = RedisUtils.getJedisCluster();
		try {
			return cluster.get(ConstantRedisKeys.REDIS_SHIRO_LOGIN_KEY+key);

		} catch (Exception e) {
			logger.error("get cache exception，key is ： " + key, e);
			return null;
		}
	}

	public static <T> T get(String key, Class<?> clz) {
		if (StringUtils.isBlank(key)) {
			return null;
		}
		JedisCluster cluster = RedisUtils.getJedisCluster();
		try {
			String value = cluster.get(ConstantRedisKeys.REDIS_SHIRO_LOGIN_KEY+key);
			if (value != null && clz != null) {
				return (T) JSON.parseObject(value, clz);
				// return mapper.readValue(value, clz);
			} else {
				return (T) value;
			}
		} catch (Exception e) {
			logger.error("get cache exception，key is ： " + key, e);
			return null;
		}
	}

	
	public static Object getObj(String key) {
		if (StringUtils.isBlank(key)) {
			return null;
		}
		JedisCluster cluster = RedisUtils.getJedisCluster();
		try {
			String value = cluster.get(ConstantRedisKeys.REDIS_SHIRO_LOGIN_KEY+key);
			
			return JSON.parseObject(value);
		} catch (Exception e) {
			logger.error("get cache exception，key is ： " + key, e);
			return null;
		}
	}
	
	/*
	 * 删除缓存对象
	 * 
	 * @param key
	 */
	public static void delObj(final String key) {
		if (StringUtils.isBlank(key)) {
			return;
		}
		JedisCluster cluster = RedisUtils.getJedisCluster();
		try {
			cluster.del(ConstantRedisKeys.REDIS_SHIRO_LOGIN_KEY+key);
		} catch (Exception e) {
			logger.error("delete cache exception，key is ： " + key, e);
		}
	}

	/*
	 * 获取增长key
	 * 
	 * @param key
	 */
	public static Long incr(String key) {
		if (StringUtils.isBlank(key)) {
			return null;
		}

		JedisCluster cluster = RedisUtils.getJedisCluster();
		try {
			// if (false == cluster.exists(key)) {
			// // 如果增长的key 不存在从100开始初始化增长
			// set(key, 1000);
			// }

			return cluster.incr(ConstantRedisKeys.REDIS_SHIRO_LOGIN_KEY+key);
		} catch (Exception e) {
			logger.error("incr cache exception，key is ： " + key, e);

			return null;
		}
	}
	
	/*
	 * @fun 判断键是否存在
	 * @author yaofeng
	 * @param key
	 * @return
	 */
	public static boolean exists(String key) {
		boolean b = false;
		if (StringUtils.isBlank(key)) {
			return b;
		} 

		JedisCluster cluster = RedisUtils.getJedisCluster();
		try {
			return cluster.exists(ConstantRedisKeys.REDIS_SHIRO_LOGIN_KEY+key);
		} catch (Exception e) {
			logger.error("incr cache exception，key is ： " + key, e);
			return b;
		}
	}

	/*
	 * 删除指定key
	 * @param key
	 * @return
	 */
	public static Long delete(String key) {
		JedisCluster cluster = RedisUtils.getJedisCluster();
		return cluster.del(ConstantRedisKeys.REDIS_SHIRO_LOGIN_KEY+key);
	}

	/*
	 * 获取keys
	 * 
	 * @param keys
	 * @return
	 */
	public static Iterable<String> getKeys(String keys) {
		RedissonClient client = RedissonUtils.getRedisson();
		return client.getKeys().findKeysByPattern(ConstantRedisKeys.REDIS_SHIRO_LOGIN_KEY+keys);
	}

	/*
	 * 删除keys
	 * 
	 * @param keys
	 * @return
	 */
	public static Long delKeys(String keys) {
		RedissonClient client = RedissonUtils.getRedisson();
		return client.getKeys().deleteByPattern(ConstantRedisKeys.REDIS_SHIRO_LOGIN_KEY+keys);
	}

	/*
	 * @fun 设置Object对象(建议存储：list,set,map)
	 * @param key
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static String setObj(String key,Object obj) throws Exception {
        String result = null;
        String exceptionInfo = "";
        JedisCluster jedisCluster = RedisUtils.getJedisCluster();
        try {
        	if (null == jedisCluster) {
        		exceptionInfo = "reids链接异常！";
        		throw new Exception(exceptionInfo);
        	}
        	
        	if (StringUtil.isNull(obj) || StringUtil.isNull(key)) {
        		exceptionInfo = "【传入参数】为空异常！";
        		throw new Exception(exceptionInfo);
        	}
        	
        	//将目标对象：通过字节流形式存储到redis中
        	result = setByteValue((ConstantRedisKeys.REDIS_SHIRO_LOGIN_KEY+key).getBytes(),StringUtil.toByteArray(obj));
        	
        } catch (Exception e) {
        	if (StringUtil.isEmpty(exceptionInfo)) {
        		exceptionInfo = "reids链接异常！";
        	}
        	throw new Exception(exceptionInfo);
        } finally {
        	//jedisCluster.close();
        }
        return result;
    } 
	
	/*
	 * @fun 获取object对象(最终是通过字节转化获取目标对象)
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static Object getObjByKey(String key) throws Exception {
		Object obj = null;
		String exceptionInfo = "";
		JedisCluster jedisCluster = RedisUtils.getJedisCluster();
		try {
        	if (null == jedisCluster) {
        		exceptionInfo = "reids链接异常！";
        		throw new Exception(exceptionInfo);
        	} 
        	
        	if (StringUtil.isNull(key)) {
        		exceptionInfo = "【传入参数】为空异常！";
        		throw new Exception(exceptionInfo);
        	}
        	
        	//获取对象：通过字节流进行获取
        	byte[] b = getByteValue((ConstantRedisKeys.REDIS_SHIRO_LOGIN_KEY+key).getBytes());
        	if (StringUtil.isNotNull(b)) {
        		//通过字节：转化为目标对象
        		obj = StringUtil.toObject(b);
        	}
        } catch (Exception e) {
        	if (StringUtil.isEmpty(exceptionInfo)) {
        		exceptionInfo = "reids链接异常！";
        	}
        	throw new Exception(exceptionInfo);
        } finally {
        	//jedisCluster.close();
        }
		return obj;
	}
	
	/*
	 * @fun 
	 * @param key
	 * @param value
	 * @return
	 * @throws Exception
	 */
	private static String setByteValue(byte[] key, byte[] value) throws Exception {
        String result = null;
        String exceptionInfo = "";
        JedisCluster jedisCluster = RedisUtils.getJedisCluster();
        try {
        	if (null == jedisCluster) {
        		exceptionInfo = "reids链接异常！";
        		throw new Exception(exceptionInfo);
        	} else {
        		result = jedisCluster.set(key,value);
        	}
        } catch (Exception e) {
        	if (StringUtil.isEmpty(exceptionInfo)) {
        		exceptionInfo = "reids链接异常！";
        	}
        	throw new Exception(exceptionInfo);
        } finally {
        	//jedisCluster.close();
        }
        return result;
    }
	
	/*
	 * @fun 获取字节值
	 * @param key
	 * @return
	 * @throws Exception
	 */
	private static byte[] getByteValue(byte[] key) throws Exception {
		byte[] b = null;
		String exceptionInfo = "";
		JedisCluster jedisCluster = RedisUtils.getJedisCluster();
        try {
        	if (null == jedisCluster) {
        		exceptionInfo = "reids链接异常！";
        		throw new Exception(exceptionInfo);
        	} else {
        		b = jedisCluster.get(key);
        	}
        } catch (Exception e) {
        	if (StringUtil.isEmpty(exceptionInfo)) {
        		exceptionInfo = "reids链接异常！";
        	}
        	throw new Exception(exceptionInfo);
        } finally {
        	//jedisCluster.close();
        }
        return b;
	}
	
}
