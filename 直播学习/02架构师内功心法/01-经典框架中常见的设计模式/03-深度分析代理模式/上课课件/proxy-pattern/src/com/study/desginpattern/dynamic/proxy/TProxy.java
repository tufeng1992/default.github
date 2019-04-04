package com.study.desginpattern.dynamic.proxy;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class TProxy implements Serializable {

    private static String ln = "\n";

    private TProxy() {
    }


    public static Object newProxyInstance(TClassLoader loader,
                                          Class<?>[] interfaces,
                                          TInvocationHandler h) throws Exception {
        //1.生成源代码
        String sb = generateCode(interfaces[0]);

        //2.将生成的源代码保存为.java文件
        File f = new File("F:/study_code/$ProxyHeHe0.java");
        FileWriter fw = new FileWriter(f);
        fw.write(sb);
        fw.flush();
        fw.close();

        //3.编译源代码文件并生成.class文件
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();//获取java编译器
        StandardJavaFileManager manager = compiler.getStandardFileManager(null, null, null);
        Iterable iterable = manager.getJavaFileObjects(f);

        //4.将class文件动态加载到jvm中
        JavaCompiler.CompilationTask task = compiler.getTask(null, manager, null, null, null, iterable);
        task.call();
        manager.close();

        //5.返回被代理后的对象
        Class proxyClass = loader.findClass("$ProxyHeHe0");
        Constructor c = proxyClass.getConstructor(TInvocationHandler.class);

        return c.newInstance(h);
    }

    /**
     * 生成java文件
     *
     * @param interfaces
     * @return
     */
    private static String generateCode(Class<?> interfaces) {
        StringBuilder sb = new StringBuilder();
        sb.append("package com.study.desginpattern.dynamic.proxy;" + ln);
        sb.append("import java.lang.reflect.Method;");
        sb.append("public class $ProxyHeHe0 implements " + interfaces.getName() + "{" + ln);
        sb.append("TInvocationHandler h;" + ln);
        sb.append("public $ProxyHeHe0(TInvocationHandler h) {" + ln);
        sb.append("this.h = h;" + ln);
        sb.append("}" + ln);
        for (Method m : interfaces.getMethods()) {
            sb.append("public " + m.getReturnType().getName() + " " + m.getName() + "(){" + ln);
            sb.append("try{" + ln);
            sb.append("Method m = " + interfaces.getName() + ".class.getMethod(\"" + m.getName() + "\",new Class[]{});" + ln);
            sb.append("this.h.invoke(this,m,null);" + ln);
            sb.append("}catch(Throwable e){e.printStackTrace();}" + ln);
            sb.append("}" + ln);
        }
        sb.append("}" + ln);
        return sb.toString();
    }
}
