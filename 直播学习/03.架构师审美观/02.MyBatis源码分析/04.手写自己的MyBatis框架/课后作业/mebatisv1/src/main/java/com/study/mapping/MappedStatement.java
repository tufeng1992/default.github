package com.study.mapping;

import lombok.Data;

import java.util.Map;

@Data
public class MappedStatement {

    private String sql;

    private SqlCommandType sqlCommandType;

    private Map<String, Object> parameterMap;

    private String id;

}
