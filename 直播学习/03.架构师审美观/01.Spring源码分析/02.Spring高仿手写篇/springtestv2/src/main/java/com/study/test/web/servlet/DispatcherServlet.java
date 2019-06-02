package com.study.test.web.servlet;

import com.study.test.beans.factory.ApplicationContext;
import com.study.test.beans.factory.config.BeanDefinition;
import com.study.test.beans.supports.HandlerMapping;
import com.study.test.core.annotation.Controller;
import com.study.test.core.annotation.RequestMapping;
import com.study.test.core.annotation.RequestParam;
import com.study.test.web.servlet.defaults.DefaultHandlerAdapter;
import com.study.test.web.servlet.defaults.HtmlViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DispatcherServlet extends FrameworkServlet {

    private List<HandlerMapping> handlerMappings = new ArrayList<>();

    private Map<HandlerMapping, HandlerAdapter> handlerAdapters = new HashMap<>();

    private List<ViewResolver> viewResolvers = new ArrayList<>();


    @Override
    protected void doService(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doDispatch(req, resp);
    }

    private void doDispatch(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HandlerMapping handlerMapping = getHandler(request);
        ModelAndView modelAndView = null;
        try {
            if (null == handlerMapping) {
                processDispatchResult(request, response, new ModelAndView("404", null));
                return;
            }
            HandlerAdapter handlerAdapter = getHandlerAdapter(handlerMapping);
            modelAndView = handlerAdapter.handle(request, response, handlerMapping);
        } catch (Throwable e) {
            e.printStackTrace();
            modelAndView = new ModelAndView("500", null);
        }
        try {
            processDispatchResult(request, response, modelAndView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onRefresh(ApplicationContext context) throws ClassNotFoundException{
        //文件上传解析
        initMultipartResolver(context);
        //实现多语言解析
        initLocaleResolver(context);
        //主体解析
        initThemeResolver(context);
        //init HandlerMappings
        initHandlerMappings(context);
        //动态匹配类型参数、动态转换
        initHandlerAdapters(context);
        //异常解析
        initHandlerExceptionResolver(context);
        //直接解析请求到视图名
        initRequestToViewNameTranslator(context);
        //视图解析匹配
        initViewResolver(context);
        //flash映射管理
        initFlashMapManager(context);
    }

    private void initFlashMapManager(ApplicationContext context) {

    }

    private void initViewResolver(ApplicationContext context) {
        Object rootHtml = getApplicationContext().getResourceLoader().getProperties().get("rootHtml");
        String rootHtmlPath = this.getClass().getResource("/" + rootHtml.toString()).getFile();
        File file = new File(rootHtmlPath);
        viewResolvers.add(new HtmlViewResolver(file));
    }

    private void initRequestToViewNameTranslator(ApplicationContext context) {

    }

    private void initHandlerExceptionResolver(ApplicationContext context) {

    }

    private void initHandlerAdapters(ApplicationContext context) {
        for (HandlerMapping handlerMapping : handlerMappings) {
            handlerAdapters.put(handlerMapping, new DefaultHandlerAdapter());
        }
    }

    private void initThemeResolver(ApplicationContext context) {

    }

    private void initLocaleResolver(ApplicationContext context) {

    }

    private void initMultipartResolver(ApplicationContext context) {

    }

    /**
     * init load handlerMappings for this initialized beanDefinitions
     */
    private void initHandlerMappings(ApplicationContext context) throws ClassNotFoundException {
        for (BeanDefinition beanDefinition : webApplicationContext.getAllBeanDefinition()) {
            Class<?> clazz = Class.forName(beanDefinition.getBeanClassName());
            if (null != clazz.getAnnotation(Controller.class)) {
                StringBuffer path = new StringBuffer();
                if (null != clazz.getAnnotation(RequestMapping.class)) {
                    path.append("/" + clazz.getAnnotation(RequestMapping.class).path());
                }
                Object beanObj = context.getBean(beanDefinition.getFactoryBeanName());
                doLoadMethods(beanObj, clazz.getDeclaredMethods(), path);
            }
        }
    }


    private HandlerMapping getHandlerMapping(String path) {
        if (null == path || "".equals(path)) {
            return null;
        }
        if (path.lastIndexOf("/") == (path.length() - 1)) {
            path = path.substring(0, path.length() - 1);
        }
        for (HandlerMapping handlerMapping : handlerMappings) {
            if (handlerMapping.getPath().equals(path)) {
                return handlerMapping;
            }
        }
        return null;
    }

    private HandlerMapping getHandler(HttpServletRequest request) {
        return doGetHandler(request);
    }

    private HandlerMapping doGetHandler(HttpServletRequest request) {
        String url = request.getRequestURI();
        System.out.println(url);
        url = url.replace(request.getContextPath(), "").replaceAll("/+", "/");
        HandlerMapping handlerMapping = getHandlerMapping(url);
        return handlerMapping;
    }

    private void doLoadMethods(Object obj, Method[] methods, StringBuffer path) {
        for (Method method : methods) {
            if (null != method.getAnnotation(RequestMapping.class)) {
                HandlerMapping handlerMapping = new HandlerMapping();
                String urlPath = path.toString() + "/" + method.getAnnotation(RequestMapping.class).path();
                handlerMapping.setArgs(method.getParameters());
                handlerMapping.setPath(urlPath);
                handlerMapping.setMethod(method);
                handlerMapping.setBean(obj);
                doLoadParameter(method, handlerMapping);
                handlerMappings.add(handlerMapping);
            }
        }
    }

    private String[] doLoadParameter(Method method, HandlerMapping handlerMapping) {
        String[] parameterName = new String[method.getParameterAnnotations().length];
        for (int i = 0; i < method.getParameterAnnotations().length; i++) {

            Annotation[] annotation = method.getParameterAnnotations()[i];
            if (annotation[0].annotationType().equals(RequestParam.class)){
                parameterName[i] = ((RequestParam)annotation[0]).name();
            }
        }
        handlerMapping.setParameterNames(parameterName);
        return null;
    }


    private HandlerAdapter getHandlerAdapter(HandlerMapping handlerMapping) {
        return handlerAdapters.get(handlerMapping);
    }

    private void processDispatchResult(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) throws Exception {
        if (null == modelAndView) {
            return;
        }
        if (viewResolvers.isEmpty()) {
            return;
        }
        for (ViewResolver viewResolver : viewResolvers) {
            View view = viewResolver.resolveViewName(modelAndView.getViewName());
            if (null != view) {
                view.render(modelAndView.getModelMap(), request, response);
            }
        }


    }
}
