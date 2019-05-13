package com.study.executor.statement;

import java.util.List;

public interface StatementHandler {

    <E> List<E> query(String statement, Object parameter);
}
