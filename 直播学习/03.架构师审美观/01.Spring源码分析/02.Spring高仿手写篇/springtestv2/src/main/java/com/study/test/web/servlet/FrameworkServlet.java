package com.study.test.web.servlet;

import com.study.test.beans.factory.ApplicationContext;
import com.study.test.beans.factory.WebApplicationContext;
import com.study.test.context.context.ApplicationContextAware;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public abstract class FrameworkServlet extends HttpServlet implements ApplicationContextAware {

    protected WebApplicationContext webApplicationContext;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doService(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doService(req, resp);
    }

    protected abstract void doService(HttpServletRequest req, HttpServletResponse resp) throws IOException;

    @Override
    public void init(ServletConfig config) {
        try {
            this.webApplicationContext = new WebApplicationContext(config);
            this.webApplicationContext.refresh();
            onRefresh(webApplicationContext);
            System.out.println("refresh end");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        if (applicationContext instanceof WebApplicationContext) {
            this.webApplicationContext = (WebApplicationContext) applicationContext;
        }
    }

    public WebApplicationContext getApplicationContext() {
        return webApplicationContext;
    }

    protected void onRefresh(ApplicationContext context) throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        // For subclasses: do nothing by defaults.
    }
}
