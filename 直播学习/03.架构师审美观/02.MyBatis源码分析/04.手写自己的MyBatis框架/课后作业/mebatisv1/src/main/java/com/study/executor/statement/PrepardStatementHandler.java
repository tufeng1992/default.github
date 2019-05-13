package com.study.executor.statement;

import com.study.mapping.DataSourceInfo;
import com.study.mapping.ResultSetHandler;
import com.study.test.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrepardStatementHandler extends BaseStatementHandler {


    public PrepardStatementHandler(DataSourceInfo dataSourceInfo) {
        super(dataSourceInfo);
    }

    @Override
    public <E> List<E> doQuery(String statement, Object parameter) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            String sql = statement;
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            ResultSetHandler resultSetHandler = new ResultSetHandler();
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                User user = resultSetHandler.parseResult(rs, User.class);
                users.add(user);
            }
            return (List<E>) users;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(connection, ps, rs);
        }
        return null;
    }


}
