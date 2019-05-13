package com.study.executor.statement;

import com.study.mapping.DataSourceInfo;

import java.sql.*;
import java.util.List;

public abstract class BaseStatementHandler implements StatementHandler {

    private final DataSourceInfo dataSourceInfo;


    public BaseStatementHandler(DataSourceInfo dataSourceInfo) {
        this.dataSourceInfo = dataSourceInfo;
    }

    @Override
    public <E> List<E> query(String statement, Object parameter) {
        return doQuery(statement, parameter);
    }

    protected abstract <E> List<E> doQuery(String statement, Object parameter);

    protected Connection getConnection(){
        Connection connection = null;
        try {
            Class.forName(dataSourceInfo.getDriver()).newInstance();
            connection = DriverManager.getConnection(dataSourceInfo.getUrl(),
                    dataSourceInfo.getUsername(), dataSourceInfo.getPassword());
        } catch (SQLException | IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    protected void close(Connection connection, Statement statement, ResultSet rs) {
        try {
            if (null != rs) {
                rs.close();
            }
            if (null != statement) {
                statement.close();
            }
            if (null != connection) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
