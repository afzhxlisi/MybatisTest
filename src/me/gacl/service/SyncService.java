package me.gacl.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import me.gacl.util.RedisUtil;
import redis.clients.jedis.Jedis;

public class SyncService {

	public static AtomicInteger ai = new AtomicInteger();
	
	public static AtomicInteger counter = new  AtomicInteger();
	
	public static Map<String,Integer> syncTestMap = new HashMap<String,Integer>();
	
	public void testAtomicSync(){
		for(;;){
			int cur = ai.get();
			int expect = cur + 1;
			boolean  flag =ai.compareAndSet(cur, expect);
			if(flag){
				counter.incrementAndGet();
				break;
			}
		}
	}
	
	public void testSyncObject(){
		synchronized ("syncO") {
			counter.incrementAndGet();
		}
	}
	
	public void testConHashMap(ConcurrentMap<String, Integer> concurrentMap){
		Integer value = null;
		boolean flag = false;
		for(;;){
			value = concurrentMap.putIfAbsent("key", 0);
			if(value == null){
				value = 0;
			}
			flag = concurrentMap.replace("key",value , value+1);
			if(flag){
				counter.incrementAndGet();
				break;
			}
		}
	}
	
	public void testRedis(){
		Jedis jedis = RedisUtil.getJedis();
		for(;;){
			long flag = 0;
			flag = jedis.setnx("redisSyncKey", "1");
			if(flag == 1){
				counter.incrementAndGet();
				break;
			}
		}
		jedis.del("redisSyncKey");
		RedisUtil.returnResource(jedis);
	}
}
