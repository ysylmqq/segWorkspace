package com.gboss.util;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.BinaryConnectionFactory;
import net.spy.memcached.MemcachedClient;

public class MemcachedUtil {
	private static MemcachedClient mc = null;
	
	public static MemcachedClient getClient(boolean useBinary) {
		try {
			if (mc == null) {
				String servers = PropertyUtil.getPropertyValue("memcached.properties", "memcached.servers");
				if (useBinary) {
					mc = new MemcachedClient(new BinaryConnectionFactory(), AddrUtil.getAddresses(servers));
				} else {
					mc = new MemcachedClient(AddrUtil.getAddresses(servers));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mc;
	}
	
	
}
