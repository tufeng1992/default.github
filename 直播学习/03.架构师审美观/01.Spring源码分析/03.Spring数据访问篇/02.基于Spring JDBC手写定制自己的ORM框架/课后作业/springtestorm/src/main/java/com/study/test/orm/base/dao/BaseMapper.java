package com.study.test.orm.base.dao;

import java.io.Serializable;
import java.util.List;

public interface BaseMapper<T extends Serializable, PK extends Serializable> {


    List<T> select();

    T selectOne();

}
