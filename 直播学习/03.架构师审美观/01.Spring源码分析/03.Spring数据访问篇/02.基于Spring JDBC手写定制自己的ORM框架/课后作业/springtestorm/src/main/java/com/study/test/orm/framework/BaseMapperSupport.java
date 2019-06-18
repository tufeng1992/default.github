package com.study.test.orm.framework;

import com.study.test.orm.base.dao.BaseMapper;
import com.study.test.orm.support.EntitySupport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.persistence.Id;
import javax.sql.DataSource;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class BaseMapperSupport<T extends Serializable, PK extends Serializable> implements BaseMapper<T, PK> {

    private JdbcTemplate jdbcTemplate;

    private EntitySupport<T> entitySupport;

    protected BaseMapperSupport() {
        String classTypeName = ((ParameterizedType)this.getClass().getGenericSuperclass())
                .getActualTypeArguments()[0].getTypeName();
        Class clazz = null;
        try {
            clazz = Class.forName(classTypeName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        initConfig(clazz);
    }

    protected void initConfig(Class<T> clazz) {
        try {
            entitySupport = new EntitySupport<>(clazz, getPk(clazz));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void setJdbc(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private String getPk(Class<T> clazz) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getAnnotation(Id.class) != null) {
                return field.getName();
            }
        }
        return null;
    }


    @Override
    public List<T> select() {

        String sql = "select * from " + entitySupport.tableName;
        return jdbcTemplate.query(sql, entitySupport.rowMapper);
    }

    @Override
    public T selectOne() {

        List<T> list = select();
        if (null != list && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}
