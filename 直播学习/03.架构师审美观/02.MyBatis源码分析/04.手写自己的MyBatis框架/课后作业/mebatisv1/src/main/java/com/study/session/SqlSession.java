package com.study.session;

import java.util.List;

public interface SqlSession {

    /**
     * get custom mapper for this session
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T getMapper(Class<T> clazz);

    /**
     * select something data
     * @param statement
     * @param parameter
     * @param <E>
     * @return
     */
    <E> List<E> selectList(String statement, Object parameter);

    /**
     * select a data
     * @param statement
     * @param parameter
     * @param <E>
     * @return
     */
    <E> E selectOne(String statement, Object parameter);

    Configuration getConfiguration();
}
