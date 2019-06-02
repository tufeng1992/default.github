package com.study.test.aop.support;

import com.study.test.aop.aspect.AfterReturningAdvice;
import com.study.test.aop.aspect.AfterThrowingAdvice;
import com.study.test.aop.aspect.MethodBeforeAdvice;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdvisedSupport {

    private Class<?> targetClass;

    private Object target;

    private Pattern pointCutClassPattern;


    private transient Map<Method, List<Object>> methodCache;

    private AopConfig aopConfig;

    public AdvisedSupport(AopConfig aopConfig) {
        this.aopConfig = aopConfig;
    }


    public Class<?> getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(Class<?> targetClass) {
        this.targetClass = targetClass;
        parse();
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }


    public List<Object> getInterceptorsAndDynamicInterceptionAdvise(Method method, Class<?> targetClass) throws NoSuchMethodException {
        List<Object> cached = methodCache.get(method);
        if (null == cached) {
            Method m = targetClass.getMethod(method.getName(), method.getParameterTypes());
            cached = methodCache.get(m);
            if (null != cached) {
                methodCache.put(m, cached);
            }
        }
        return cached;
    }

    public boolean pointCutMatch() {
        return pointCutClassPattern.matcher(targetClass.toString()).matches();
    }

    private void parse() {
        String pointCut = aopConfig.getPointCut().replaceAll("\\.", "\\\\.")
                .replaceAll("\\\\.\\*", ".*").replaceAll("\\(", "\\\\(")
                .replaceAll("\\)", "\\\\)");

        String pointCutForClass = pointCut.substring(0, pointCut.lastIndexOf("\\(") - 4);

        pointCutClassPattern = Pattern.compile("class " + pointCutForClass.substring(pointCutForClass.lastIndexOf(" ")+1));

        methodCache = new HashMap<>();

        Pattern pattern = Pattern.compile(pointCut);

        try {
            Class<?> aspectClass = Class.forName(aopConfig.getAspectClass());
            Map<String, Method> aspectMethods = new HashMap<>();
            for (Method m : aspectClass.getMethods()){
                aspectMethods.put(m.getName(),m);
            }

            //在这里得到的方法都是原生的方法
            for (Method m : targetClass.getMethods()) {
                String methodString = m.toString();
                if (methodString.contains("throws")) {
                    methodString =
                            methodString.substring(0, methodString.lastIndexOf("throws")).trim();
                }
                Matcher matcher = pattern.matcher(methodString);
                if (matcher.matches()) {
                    //能满足切面规 则的类，添加的 AOP 配置中
                    List<Object> advices = new LinkedList<Object>();
                    //前置通知
                    if (!(null == aopConfig.getAspectBefore() ||
                            "".equals(aopConfig.getAspectBefore().trim()))) {
                        advices.add(new
                                MethodBeforeAdvice(aspectMethods.get(aopConfig.getAspectBefore()), aspectClass.newInstance()));
                    }
                    //后置通知
                    if (!(null == aopConfig.getAspectAfter() ||
                            "".equals(aopConfig.getAspectAfter().trim()))) {
                        advices.add(new
                                AfterReturningAdvice(aspectMethods.get(aopConfig.getAspectAfter()), aspectClass.newInstance()));
                    }
                    //异常通知
                    if (!(null == aopConfig.getAspectAfterThrow() ||
                            "".equals(aopConfig.getAspectAfterThrow().trim()))) {
                        AfterThrowingAdvice afterThrowingAdvice = new
                                AfterThrowingAdvice(aspectMethods.get(aopConfig.getAspectAfterThrow()),
                                aspectClass.newInstance());
                        afterThrowingAdvice.setThrowingName(aopConfig.getAspectAfterThrowingName());
                        advices.add(afterThrowingAdvice);
                    }
                    methodCache.put(m, advices);
                }

            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

}
