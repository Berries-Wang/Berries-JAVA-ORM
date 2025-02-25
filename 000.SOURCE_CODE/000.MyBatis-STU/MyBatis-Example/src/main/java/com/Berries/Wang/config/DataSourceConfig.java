package com.Berries.Wang.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    @Bean
    public DataSource initDefaultDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(
            "jdbc:mysql://192.168.3.198:3309/mybatis_stu_a?useUnicode=true&characterEncoding=utf-8&autoReconnect=true&useSSL=false&serverTimezone=GMT%2B8&allowMultiQueries=true&useAffectedRows=true");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setMaxActive(20);
        dataSource.setInitialSize(8);
        dataSource.setMaxWait(6000000);
        dataSource.setMinEvictableIdleTimeMillis(60000);
        dataSource.setMinIdle(1);
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setPoolPreparedStatements(true);
        dataSource.setMaxOpenPreparedStatements(20);
        dataSource.setAsyncInit(true);
        return dataSource;
    }
}
