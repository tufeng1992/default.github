package com.study.session;

import com.study.executor.Executor;
import com.study.session.defaults.DefaultSqlSession;

public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private final Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration, configuration.newExecutor());
    }
}
