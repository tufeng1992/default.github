package com.study.test.web.servlet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModelAndView {

    private String viewName;

    private Map<String, ?> modelMap;

}
