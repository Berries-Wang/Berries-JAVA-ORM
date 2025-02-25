package com.Berries.Wang;

import com.Berries.Wang.mapper.UserMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Hello world!
 */
@MapperScan("com.Berries.Wang")
@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy = true)
public class MyBatisSTUApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(MyBatisSTUApplication.class);
        ApplicationContext applicationContext = application.run(args);
        System.out.println("Hello World!");

        UserMapper userMapper = applicationContext.getBean(UserMapper.class);
        System.out.println((null == userMapper) ? null : userMapper.getClass());
        System.out.println(userMapper.selectById(1L));
    }
}
