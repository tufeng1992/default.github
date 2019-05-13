package com.study.mapping;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetHandler {



    public <T> T parseResult(ResultSet rs, Class<T> clazz) {
        try {
            T result = clazz.newInstance();
            int length = rs.getMetaData().getColumnCount();
            for (int i = 1; i <= length; i++) {
                String columnName = rs.getMetaData().getColumnName(i);
                String entityName = columnName;
                if (columnName.contains("_")) {
                    String[] strs = columnName.split("_");
                    StringBuilder sbName = new StringBuilder();
                    for (String str : strs) {
                        if (sbName.length() > 0 ) {
                            sbName.append(toUpperCase(str));
                        } else {
                            sbName.append(str);
                        }
                        entityName = sbName.toString();
                    }
                }
                Field field = result.getClass().getDeclaredField(entityName);
                if (null != field) {
                    String methodName = "set" + toUpperCase(entityName);
                    Object val = convertValueType(rs, columnName, field.getType());
                    Method method = result.getClass().getMethod(methodName, val.getClass());
                    if (null != method) {
                        method.invoke(result, val);
                    }
                }
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Object convertValueType(ResultSet rs, String columnName, Class<?> type) throws SQLException {
        if (String.class.equals(type)) {
            return rs.getString(columnName);
        } else if (Long.class.equals(type)) {
            return rs.getLong(columnName);
        } else if (Integer.class.equals(type)) {
            return rs.getInt(columnName);
        } else if (java.util.Date.class.equals(type)) {
            Date date = rs.getDate(columnName);
            return new java.util.Date(date.getTime());
        }
        return null;
    }

    private String toUpperCase(String str) {
        char[] chars = str.toCharArray();
        chars[0] -= 32;
        return new String(chars);
    }
}
