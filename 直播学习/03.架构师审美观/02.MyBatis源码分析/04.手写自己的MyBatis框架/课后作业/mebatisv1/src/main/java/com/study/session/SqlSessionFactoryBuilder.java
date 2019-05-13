package com.study.session;

import java.io.IOException;
import java.io.InputStream;

public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build(InputStream inputStream) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        Configuration configuration = new Configuration(inputStream);
        SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);
        return sqlSessionFactory;
    }
}
