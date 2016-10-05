package me.gacl.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.gacl.util.RedisUtil;
import redis.clients.jedis.Jedis;

public class SyncService {

	Logger logger = LoggerFactory.getLogger(SyncService.class);
	public static AtomicInteger ai = new AtomicInteger();
	
	public static AtomicInteger counter = new  AtomicInteger();
	
	public static AtomicInteger counterAct = new AtomicInteger();
	
	public static Map<String,Integer> syncTestMap = new HashMap<String,Integer>();
	
	public void testAtomicSync(){
		for(;;){
			int cur = ai.get();
			int expect = cur + 1;
			boolean  flag =ai.compareAndSet(cur, expect);
			counterAct.incrementAndGet();
			if(flag){
				counter.incrementAndGet();
//				logger.info(String.valueOf(counter.incrementAndGet()));
				break;
			}
		}
	}
	
	public void testSyncObject(){
		synchronized ("syncO") {
			counter.incrementAndGet();
//			logger.info(counter.toString());
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
			counterAct.incrementAndGet();
			if(flag){
				counter.incrementAndGet();
//				logger.info(String.valueOf(counter.incrementAndGet()));
				break;
			}
		}
	}
	
	public void testRedis() {
		Jedis jedis = RedisUtil.getJedis();
		long flag = 0;
		for(;;){
			flag = jedis.setnx("redisSyncKey", "1");
			if(flag == 1){
				counter.incrementAndGet();
				logger.info(counter.toString());
				break;
			}else{
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if(flag ==1){
			jedis.del("redisSyncKey");
		}
		RedisUtil.returnResource(jedis);
	}
}
