package me.gacl.service;

import com.alibaba.fastjson.JSON;

public class RedisService {
	
	public static void main(String args[]){
		test();
	}
	
	public static void test(){
		NewObject o = new NewObject();
		System.out.println(JSON.toJSONString(o));
	}
}
class NewObject{
	public String getName(){
		return "name1";
	}
}