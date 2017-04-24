package com.yyx.redis;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;

import org.redisson.ClusterServersConfig;
import org.redisson.Config;
import org.redisson.Redisson;
import org.redisson.RedissonClient;

public class RedissonUtils {

//	private static final Log logger = LogFactory.getLog(RedissonUtils.class);

	private static ResourceBundle bundle = null;

	private static RedissonClient redisson;

	private RedissonUtils() {
	}

	public static RedissonClient getRedisson() {
		Config config = new Config();
		ClusterServersConfig clusterServersConfig = config.useClusterServers();// ("10.16.31.199:7000");

		if (redisson == null) {
			bundle = ResourceBundle.getBundle("redis");

			Enumeration<String> enumeration = bundle.getKeys();
			List<String> ips = new ArrayList<String>();

			while (enumeration.hasMoreElements()) {
				String key = (String) enumeration.nextElement();
				if (key.matches("^DATA_SOURCE.*")) {
					String datasource = bundle.getString(key);
					ips.add(datasource);
				}
			}
			clusterServersConfig.addNodeAddress(ips.toArray(new String[0]));
			redisson = Redisson.create(config);
		}
		return redisson;

	}

}
