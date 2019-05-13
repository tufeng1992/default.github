package com.study.executor;

import com.study.executor.statement.StatementHandler;

import java.util.List;

public interface Executor {

    <E> List<E> query(String statement, Object parameter, StatementHandler statementHandler);

}
