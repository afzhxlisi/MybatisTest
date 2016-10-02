package me.gacl.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

import me.gacl.domain.User;
import me.gacl.test.Test1;

public class UserService {

    static Logger logger = Logger.getLogger(UserService.class);
	
	public void simpleInsert(){
		 //mybatis的配置文件
        String resource = "conf.xml";
        //使用类加载器加载mybatis的配置文件（它也加载关联的映射文件）
        InputStream is = Test1.class.getClassLoader().getResourceAsStream(resource);
        //构建sqlSession的工厂
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
        //创建能执行映射文件中sql的sqlSession
        SqlSession session = sessionFactory.openSession(ExecutorType.SIMPLE);
        /**
         * 映射sql的标识字符串，
         * me.gacl.mapping.userMapper是userMapper.xml文件中mapper标签的namespace属性的值，
         * getUser是select标签的id属性值，通过select标签的id属性值就可以找到要执行的SQL
         */
        String statement = "me.gacl.mapping.userMapper.getMaxUser";//映射sql的标识字符串
        String insertStatement = "me.gacl.mapping.userMapper.insert";//映射sql的标识字符串
        
        //执行查询返回一个唯一user对象的sql
        User user = session.selectOne(statement, 1);
        List<User> users = new ArrayList<User>(1000);
        long startTime = new Date().getTime();
        for(int i =0 ;i<1000;i++){
            User u = new User();
            u.setId(user.getId()+i+1);
            u.setStartDate(new Date());
            u.setEndDate(new Date());
            users.add(u);
        }
        
        logger.info(new Date());
        for(User u :users){
            session.insert(insertStatement, u);
        }
        session.commit();
        logger.info(new Date().getTime()-startTime);
        logger.info(new Date());
        logger.info(user);
	}
	
	public void batchInsert(){
		 //mybatis的配置文件
       String resource = "conf.xml";
       //使用类加载器加载mybatis的配置文件（它也加载关联的映射文件）
       InputStream is = Test1.class.getClassLoader().getResourceAsStream(resource);
       //构建sqlSession的工厂
       SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
       //创建能执行映射文件中sql的sqlSession
       SqlSession session = sessionFactory.openSession(ExecutorType.BATCH);
       /**
        * 映射sql的标识字符串，
        * me.gacl.mapping.userMapper是userMapper.xml文件中mapper标签的namespace属性的值，
        * getUser是select标签的id属性值，通过select标签的id属性值就可以找到要执行的SQL
        */
       String statement = "me.gacl.mapping.userMapper.getMaxUser";//映射sql的标识字符串
       String insertStatement = "me.gacl.mapping.userMapper.insert";//映射sql的标识字符串
       
       //执行查询返回一个唯一user对象的sql
       User user = session.selectOne(statement, 1);
       List<User> users = new ArrayList<User>(1000);
       long startTime = new Date().getTime();
       for(int i =0 ;i<1000;i++){
           User u = new User();
           u.setId(user.getId()+i+1);
           u.setStartDate(new Date());
           u.setEndDate(new Date());
           users.add(u);
       }
       
       logger.info(new Date());
       for(User u :users){
           session.insert(insertStatement, u);
       }
       session.commit();
       logger.info(new Date().getTime()-startTime);
       logger.info(new Date());
       logger.info(user);
	}
	
	public void batchInsertXml(){
		 //mybatis的配置文件
      String resource = "conf.xml";
      //使用类加载器加载mybatis的配置文件（它也加载关联的映射文件）
      InputStream is = Test1.class.getClassLoader().getResourceAsStream(resource);
      //构建sqlSession的工厂
      SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
      //创建能执行映射文件中sql的sqlSession
      SqlSession session = sessionFactory.openSession(ExecutorType.SIMPLE);
      /**
       * 映射sql的标识字符串，
       * me.gacl.mapping.userMapper是userMapper.xml文件中mapper标签的namespace属性的值，
       * getUser是select标签的id属性值，通过select标签的id属性值就可以找到要执行的SQL
       */
      String statement = "me.gacl.mapping.userMapper.getMaxUser";//映射sql的标识字符串
      String insertStatement1 = "me.gacl.mapping.userMapper.batchInsert";//映射sql的标识字符串
      
      //执行查询返回一个唯一user对象的sql
      User user = session.selectOne(statement, 1);
      List<User> users = new ArrayList<User>(1000);
      long startTime = new Date().getTime();
      for(int i =0 ;i<1000;i++){
          User u = new User();
          u.setId(user.getId()+i+1);
          u.setStartDate(new Date());
          u.setEndDate(new Date());
          users.add(u);
      }
      
      logger.info(new Date());
      Map<String,Object> params = new HashMap<String,Object>();
      params.put("users", users);
      session.insert(insertStatement1, params);
      session.commit();
      logger.info(new Date().getTime()-startTime);
      logger.info(new Date());
      logger.info(user);
	}
}
