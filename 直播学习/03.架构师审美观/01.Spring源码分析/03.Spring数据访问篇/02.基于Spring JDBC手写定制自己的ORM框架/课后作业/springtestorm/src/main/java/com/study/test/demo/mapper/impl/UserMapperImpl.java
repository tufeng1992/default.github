package com.study.test.demo.mapper.impl;

import com.study.test.demo.entity.User;
import com.study.test.demo.mapper.UserMapper;
import com.study.test.orm.framework.BaseMapperSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class UserMapperImpl extends BaseMapperSupport<User, Long> implements UserMapper {


    @Autowired
    @Override
    protected void setJdbc(DataSource dataSource) {
        super.setJdbc(dataSource);
    }
}
