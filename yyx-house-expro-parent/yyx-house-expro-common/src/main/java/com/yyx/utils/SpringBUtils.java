package com.yyx.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/*
 * 
 * 
 * 
 * <p>
 * ----------------------------------<h1>【统一认证项目】</h1>-------------------------------------------
 * <br>
 * 工程名 ：tyrz-common
 * <br>
 * 功能：获取spring context
 * <br>
 * 描述：TODO
 * <br>
 * 授权 : (C) Copyright (c) 2011
 * <br>
 * 公司 : 北京中交车联科技服务有限公司
 * <br>
 * -----------------------------------------------------------------------------
 * <br>
 * 修改历史
 * <br>
 * <table width="432" border="1">
 * <tr><td>版本</td><td>时间</td><td>作者</td><td>改变</td></tr>
 * <tr><td>$</td><td>2015年12月30日 下午7:06:49</td><td></td><td>创建</td></tr>
 * </table>
 * <br>
 * <font color="#FF0000">注意: 本内容仅限于[北京中交兴路车联网科技有限公司]内部使用，禁止转发</font>
 * <br>
 * 
 * @version 1.0
 * @author yaofeng
 * @since JDK1.7
 */
public class SpringBUtils implements ApplicationContextAware {

	private static ApplicationContext context = null;
	
	private static SpringBUtils springUtils = null;
	
	/*
	 * @fun 获取SpringBUtils对象
	 * @return
	 */
	public synchronized static SpringBUtils init() {
	  if(springUtils == null){
		  springUtils = new SpringBUtils();
	  }
	  return springUtils;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	   context = applicationContext;
	}

	public synchronized static Object getBean(String beanName) {
	   return context.getBean(beanName);
	}
	
}
