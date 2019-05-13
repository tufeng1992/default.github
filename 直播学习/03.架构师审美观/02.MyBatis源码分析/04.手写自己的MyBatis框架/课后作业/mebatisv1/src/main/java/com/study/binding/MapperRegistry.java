package com.study.binding;

import com.study.session.Configuration;
import com.study.session.SqlSession;

public class MapperRegistry {

    private Configuration configuration;

    public MapperRegistry(Configuration configuration) {
        this.configuration = configuration;
    }

    public <T> T getMapper(Class<T> clazz, SqlSession sqlSession) {
        MapperProxyFactory<T> mapperProxyFactory = new MapperProxyFactory<>(clazz);
        return mapperProxyFactory.newInstance(sqlSession);
    }
}
