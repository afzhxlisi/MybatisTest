package me.gacl.test;

import java.io.IOException;

import org.apache.log4j.Logger;

import me.gacl.service.UserService;

public class Test1 {

    static Logger logger = Logger.getLogger(Test1.class);
    public static void main(String[] args) throws IOException {
    	UserService userService = new UserService();
//    	userService.simpleInsert();
//    	userService.batchInsert();
    	userService.batchInsertXml();
    }
}