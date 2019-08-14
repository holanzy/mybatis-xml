package com.mybatis;

import com.mybatis.mapper.UserMapper;
import com.mybatis.model.User;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        String path = "mapper/UserMapper.xml";
        UserMapper userMapper = SqlSession.getUserMapper(UserMapper.class, path);
        User user = userMapper.queryUser("name1");
        System.out.println(user);
    }
}
