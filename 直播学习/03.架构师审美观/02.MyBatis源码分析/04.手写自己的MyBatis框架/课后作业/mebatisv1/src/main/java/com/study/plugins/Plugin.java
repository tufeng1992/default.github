package com.study.plugins;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Set;

public class Plugin implements InvocationHandler {

    private final Interceptor interceptor;

    private final Object target;

    public Plugin(Interceptor interceptor, Object target) {
        this.interceptor = interceptor;
        this.target = target;
    }

    public static Object wrap(Interceptor interceptor, Object target) {
        Class<?> targetClazz = target.getClass();
        Class<?> superclass = targetClazz.getSuperclass();
        return Proxy.newProxyInstance(targetClazz.getClassLoader(), superclass.getInterfaces(), new Plugin(interceptor, target));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            if (Object.class.equals(method.getDeclaringClass())) {
                return method.invoke(this, args);
            }
            if (this.isDefaultMethod(method)) {
                return this.invokeDefaultMethod(proxy, method, args);
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return interceptor.intercept(new Invocation(target, args, method));
    }

    private Object invokeDefaultMethod(Object proxy, Method method, Object[] args) throws Throwable {
        Constructor<MethodHandles.Lookup> constructor = MethodHandles.Lookup.class.getDeclaredConstructor(Class.class, Integer.TYPE);
        if (!constructor.isAccessible()) {
            constructor.setAccessible(true);
        }

        Class<?> declaringClass = method.getDeclaringClass();
        return ((MethodHandles.Lookup)constructor.newInstance(declaringClass, 15)).unreflectSpecial(method, declaringClass).bindTo(proxy).invokeWithArguments(args);
    }

    private boolean isDefaultMethod(Method method) {
        return (method.getModifiers() & 1033) == 1 && method.getDeclaringClass().isInterface();
    }
}
