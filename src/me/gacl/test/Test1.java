package me.gacl.test;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import me.gacl.service.SyncService;
import me.gacl.service.UserService;
import me.gacl.util.RedisUtil;
import redis.clients.jedis.Jedis;

public class Test1 {

    static Logger logger = Logger.getLogger(Test1.class);
    public static void main(String[] args) throws IOException, InterruptedException {
    	UserService userService = new UserService();
//    	userService.simpleInsert();
//    	userService.batchInsert();
//    	userService.batchInsertXml();
    	
    	final SyncService syncService = new SyncService();
    	final ConcurrentMap<String, Integer> concurrentMap = new ConcurrentHashMap<String,Integer>();
    	Jedis jedis = RedisUtil.getJedis();
    	jedis.del("redisSyncKey");
    	ExecutorService es= Executors.newCachedThreadPool();
    	Date startDate = new Date();
    	for(int i = 0;i<10;i++){
	    	es.execute(new Runnable() {
				
				@Override
				public void run() {
//					syncService.testAtomicSync();
//					syncService.testSyncObject();	
					syncService.testConHashMap(concurrentMap);
//					syncService.testRedis();
					
				}
			});
    	}
    	
    	es.shutdown();
    	while(!es.isTerminated()){
    		Thread.sleep(50);
    	}
    	logger.info(new Date().getTime()-startDate.getTime());
    	System.out.println("counter " +SyncService.counter.get());
    	System.out.println("counterAct " +SyncService.counterAct.get());
    	RedisUtil.returnResource(jedis);
    	
    	
    }
}