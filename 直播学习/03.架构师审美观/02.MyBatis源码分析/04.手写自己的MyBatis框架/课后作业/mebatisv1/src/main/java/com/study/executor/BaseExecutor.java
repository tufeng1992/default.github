package com.study.executor;

import com.study.executor.statement.StatementHandler;

import java.util.List;

public abstract class BaseExecutor implements Executor{


    @Override
    public <E> List<E> query(String statement, Object parameter, StatementHandler statementHandler) {
        System.out.println("do something");
        return this.doQuery(statement, parameter, statementHandler);
    }

    public abstract <E> List<E> doQuery(String statement, Object parameter, StatementHandler statementHandler);
}
