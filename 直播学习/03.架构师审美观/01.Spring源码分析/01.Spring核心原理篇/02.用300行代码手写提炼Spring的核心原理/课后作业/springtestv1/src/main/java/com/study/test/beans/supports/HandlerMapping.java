package com.study.test.beans.supports;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HandlerMapping {

    private String path;

    private Parameter[] args;

    private String[] requestTypes;

    private Method method;

    private Object bean;

    private String[] parameterNames;


}
