package com.yyx.redis;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
/*
 * 
 * 
 * 
 * 
 * @author yangzhi
 * @time 2015年9月16日下午2:53:34
 * @email zhi19861117@126.com
 * @version 1.0
 * @类介绍 redis3.0 获取客户端单例模式
 */
public class RedisUtils {
	private static final Log logger = LogFactory.getLog(RedisUtils.class);

	private static ResourceBundle bundle = null;

	private static JedisCluster jedisCluster;

	private RedisUtils() {

	}

	public static JedisCluster getJedisCluster() {
		try {

			if (jedisCluster == null) {
				bundle = ResourceBundle.getBundle("redis");
				
				Enumeration<String> enumeration = bundle.getKeys();
				Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
				while (enumeration.hasMoreElements()) {
					String key = (String) enumeration.nextElement();
					if (key.matches("^DATA_SOURCE.*")) {
						

						String datasource = bundle.getString(key);
						logger.info("初始化redis集群链接》》"+key+":"+datasource);
						String[] datasourceArr = datasource.split(":");
						jedisClusterNodes.add(new HostAndPort(datasourceArr[0], Integer.parseInt(datasourceArr[1])));

					}
				}
				
				jedisCluster = new JedisCluster(jedisClusterNodes);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("连接redis失败！", e);
		}
		

		return jedisCluster;
	}

}
