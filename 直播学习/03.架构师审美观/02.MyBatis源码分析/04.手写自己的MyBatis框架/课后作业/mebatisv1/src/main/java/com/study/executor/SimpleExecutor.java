package com.study.executor;

import com.study.executor.statement.StatementHandler;

import java.util.List;

public class SimpleExecutor extends BaseExecutor {

    @Override
    public <E> List<E> doQuery(String statement, Object parameter, StatementHandler statementHandler) {
        return statementHandler.query(statement, parameter);
    }



}
