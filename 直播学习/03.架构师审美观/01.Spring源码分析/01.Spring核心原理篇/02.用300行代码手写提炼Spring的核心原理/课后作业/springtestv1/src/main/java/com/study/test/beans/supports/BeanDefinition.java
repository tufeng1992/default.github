package com.study.test.beans.supports;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BeanDefinition {

//    private String beanId;

    private String beanName;

    private String typeName;

    private Object object;

}
