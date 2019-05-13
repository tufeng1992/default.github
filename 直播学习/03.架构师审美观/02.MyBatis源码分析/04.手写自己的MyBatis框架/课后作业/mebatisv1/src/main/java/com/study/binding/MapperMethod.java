package com.study.binding;

import com.study.mapping.MappedStatement;
import com.study.mapping.SqlCommandType;
import com.study.session.Configuration;
import com.study.session.SqlSession;

import java.lang.reflect.Method;

public class MapperMethod<T> {

    private MappedStatement mappedStatement;

    public MapperMethod(Class<T> mapperInterface, Method method, Configuration configuration) {
        mappedStatement = configuration.getMappedStatment(mapperInterface, method);
    }

    public Object execute(SqlSession sqlSession, Object[] args) {
        if (SqlCommandType.SELECT.name().equals(mappedStatement.getSqlCommandType().name())) {
            System.out.println(args);
            return sqlSession.selectList(mappedStatement.getSql(), args);
        }
        return null;
    }
}
