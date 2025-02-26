package com.Berries.Wang.mybatis.plugin;

import com.Berries.Wang.mapper.UserMapper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

@Intercepts({@Signature(type = UserMapper.class, method = "selectById", args = {Long.class})})
public class STUMyBatisPlugin implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("Before executing method:" + invocation.getMethod().getName());

        Object execRes = invocation.proceed();

        System.out.println("After Executing method: " + invocation.getMethod().getName());

        return execRes;
    }
}
