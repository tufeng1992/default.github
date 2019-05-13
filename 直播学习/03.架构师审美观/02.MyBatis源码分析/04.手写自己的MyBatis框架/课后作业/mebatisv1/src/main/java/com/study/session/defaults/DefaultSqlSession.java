package com.study.session.defaults;

import com.study.executor.Executor;
import com.study.session.Configuration;
import com.study.session.SqlSession;

import java.util.List;

public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    private Executor executor;

    public DefaultSqlSession(Configuration configuration, Executor executor) {
        this.configuration = configuration;
        this.executor = executor;
    }

    @Override
    public <T> T getMapper(Class<T> clazz) {
        return configuration.getMapper(clazz, this);
    }

    @Override
    public <E> List<E> selectList(String statement, Object parameter) {
        return executor.query(statement, parameter, configuration.getStatementHandler());
    }

    @Override
    public <E> E selectOne(String statement, Object parameter) {
        return (E) selectList(statement, parameter).get(0);
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }
}
