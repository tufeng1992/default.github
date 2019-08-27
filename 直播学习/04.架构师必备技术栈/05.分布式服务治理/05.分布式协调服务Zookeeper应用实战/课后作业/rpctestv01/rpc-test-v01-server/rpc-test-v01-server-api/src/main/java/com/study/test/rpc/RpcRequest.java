package com.study.test.rpc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RpcRequest implements Serializable {


    private static final long serialVersionUID = 2151749846308739738L;

    private String className;

    private String methodName;

    private Object[] params;


}
