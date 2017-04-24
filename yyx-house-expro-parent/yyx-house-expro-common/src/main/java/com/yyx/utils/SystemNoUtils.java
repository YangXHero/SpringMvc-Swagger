package com.yyx.utils;

import java.util.Date;

import org.apache.http.impl.cookie.DateUtils;

import com.yyx.redis.RedisCache;



/*
 * @fun 系统生成编号工具类
 * @author yaofeng
 * @date 2016-06-12
 */
public class SystemNoUtils {

	private static String dateStyle = "yyyyMMdd";

	/*
	 * @fun 生成自增主键
	 * @Description: TODO
	 * @author yaofeng
	 * @param namespace		功能名称
	 * @return
	 */
	public static Long generatePrimary(String namespace) {

		Date date = new Date();
		String dateStr = DateUtils.formatDate(date, dateStyle);
		StringBuffer key = new StringBuffer(namespace);
		key.append(dateStr);

		String orderNo = null;
		if (RedisCache.exists(key.toString())) {
			// 若存在，则进行累加
			orderNo = String.valueOf(RedisCache.incr(key.toString()));
		} else {
			// 若该键不存在，则重新开始计算订单号。并将其存储到redis中，设置过期时间为1天
			orderNo = dateStr + Constants.PRIMARY_INIT;
			RedisCache.set(key.toString(), orderNo, Constants.PRIMARY_TIME_OUT);
		}

		return Long.valueOf(orderNo);
	}
	
	/*
	 * @fun 生成系统编号
	 * @Description: TODO
	 * @author yaofeng
	 * @param namespace  功能名称
	 * @return
	 */
	public static String getSystemNo(String namespace) {

		StringBuffer key = new StringBuffer(namespace);
		String orderNo = null;
		if (RedisCache.exists(key.toString())) {
			// 若存在，则进行累加
			orderNo = String.valueOf(RedisCache.incr(key.toString()));
		} else {
			// 若该键不存在，则重新开始计算订单号。并将其存储到redis中
			orderNo = Constants.ORDER_NO_INIT;
			RedisCache.set(key.toString(), orderNo);
		}

		return orderNo;
	}
	
}
