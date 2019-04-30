package com.gupaoedu.interceptor;

import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Field;
import java.util.Properties;

@Intercepts(@Signature(
        type = Executor.class,
        method = "query",
        args ={MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
))
public class TestPageInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("查询前置");
        Object[] args = invocation.getArgs();
        RowBounds rowBounds = (RowBounds) args[2];
        MappedStatement mappedStatement = (MappedStatement) args[0];
        Object pararmeter = args[1];
        if (null != rowBounds) {
            int limit = rowBounds.getLimit();
            int offset = rowBounds.getOffset();
            System.out.println("offset：" + offset + "，limit：" + limit);
            BoundSql boundSql = mappedStatement.getBoundSql(pararmeter);
            String sql = boundSql.getSql();
            System.out.println(sql);
            sql = sql + " limit " + offset + ", " + limit;
            System.out.println(sql);
            SqlSource sqlSource = new StaticSqlSource(mappedStatement.getConfiguration(), sql, boundSql.getParameterMappings());
            System.out.println(sqlSource);
            Field field = mappedStatement.getClass().getDeclaredField("sqlSource");
            field.setAccessible(true);
            field.set(mappedStatement, sqlSource);
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
