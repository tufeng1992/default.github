package com.study.test.web.servlet.defaults;

import com.study.test.beans.supports.StringUtils;
import com.study.test.web.servlet.View;
import com.study.test.web.servlet.ViewResolver;

import java.io.File;

public class HtmlViewResolver implements ViewResolver {

    private static String TEMPLATE_SUFFIX = ".html";

    private File templateFiles;

    public HtmlViewResolver(File templateFiles) {
        this.templateFiles = templateFiles;
    }

    @Override
    public View resolveViewName(String viewName) {
        if (StringUtils.isBlank(viewName)) {
            return null;
        }
        for (File file : templateFiles.listFiles()) {
            String fileName = file.getName();
            if (file.isFile() && fileName.endsWith(TEMPLATE_SUFFIX)) {
                String s = fileName.substring(0, fileName.lastIndexOf(TEMPLATE_SUFFIX));
                if (s.equals(viewName)) {
                    return new DefaultView(file);
                }
            }
        }
        return null;
    }
}
