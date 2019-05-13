package com.study.test;

import com.study.session.SqlSession;
import com.study.session.SqlSessionFactory;
import com.study.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        InputStream inputStream = App.class.getResourceAsStream("/mebatis-config.properties");
        try {
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            SqlSession sqlSession = sqlSessionFactory.openSession();
//            sqlSession.selectOne(null, null);
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            System.out.println(userMapper.findList());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
