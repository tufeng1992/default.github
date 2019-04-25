package com.study.test.core.servlet;

import com.study.test.beans.factory.STWebApplicationContext;
import com.study.test.beans.supports.STServletHandler;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class STDispatcherServlet extends HttpServlet {

    private STWebApplicationContext webApplicationContext;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        STServletHandler handler = webApplicationContext.getHandler(req);
        if (null == handler) {
            resp.getWriter().write("404 Not Found!!!");
            return;
        }
        Object obj = null;
        try {
            obj = handler.doDispatch(req, resp);

        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.println(handler);
        if (null != obj) {
            resp.getWriter().println(obj);
        }
    }

    @Override
    public void init(ServletConfig config) {
        try {
            this.webApplicationContext = new STWebApplicationContext();
            this.webApplicationContext.refrensh(config);
            System.out.println("wait");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
