package com.study.test.beans.factory;

import javax.servlet.ServletConfig;
import java.util.*;

public class WebApplicationContext extends DefaultListableApplicationContext {

    public WebApplicationContext(ServletConfig config) {
        Enumeration e = config.getInitParameterNames();
        this.applicationConfig = new HashMap<>();
        while (e.hasMoreElements()) {
            String eName = e.nextElement().toString();
            applicationConfig.put(eName, config.getInitParameter(eName));
        }
    }

    @Override
    public void refresh() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        super.refresh();
    }
}
