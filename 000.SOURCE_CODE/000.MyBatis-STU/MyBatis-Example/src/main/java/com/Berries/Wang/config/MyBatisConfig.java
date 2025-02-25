package com.Berries.Wang.config;

import jakarta.annotation.Resource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
public class MyBatisConfig {
    @Resource
    private DataSource dataSource;

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactoryBean sqlSessionFactory() {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);

        // 加载MyBatis配置文件
        PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        // 能加载多个，所以可以配置通配符(如：classpath*:mapper/**/*.xml)
        try {
            sqlSessionFactory.setMapperLocations(resourcePatternResolver.getResources("classpath*:mappers/*.xml"));
            sqlSessionFactory.setConfigLocation(
                resourcePatternResolver.getResources("classpath*:config/mybatis-config.xml")[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sqlSessionFactory;
    }
}
