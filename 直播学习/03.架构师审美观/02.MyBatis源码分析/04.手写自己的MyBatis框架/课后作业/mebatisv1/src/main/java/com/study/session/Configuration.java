package com.study.session;

import com.study.annotations.Intercept;
import com.study.annotations.Select;
import com.study.binding.MapperRegistry;
import com.study.executor.Executor;
import com.study.executor.ExecutorType;
import com.study.executor.SimpleExecutor;
import com.study.executor.statement.PrepardStatementHandler;
import com.study.executor.statement.StatementHandler;
import com.study.mapping.DataSourceInfo;
import com.study.mapping.MappedStatement;
import com.study.mapping.SqlCommandType;
import com.study.plugins.InterceptChain;
import com.study.plugins.Interceptor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * mebatis config model
 * @author to_be_continued
 */
public class Configuration {

    private MapperRegistry mapperRegistry;

    private ExecutorType executorType;

    private Map<String, MappedStatement> mappedStatements;

    private List<String> classPaths = new ArrayList<>();

    private InterceptChain interceptChain;

    private DataSourceInfo dataSourceInfo;

    public Configuration(InputStream inputStream) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Properties properties = new Properties();
        properties.load(inputStream);

        mapperRegistry = new MapperRegistry(this);
        executorType = parseExecutorType(properties);
        mappedStatements = parseMappedStatement(properties.get("mapperScan").toString());
        interceptChain = parseInterceptChain(properties.get("plugins").toString());
        dataSourceInfo = parseDataSourceInfo(properties);
    }

    private DataSourceInfo parseDataSourceInfo(Properties properties) {
        DataSourceInfo dataSourceInfo = new DataSourceInfo(properties.get("driver").toString(), properties.get("url").toString(),
                properties.get("username").toString(), properties.get("password").toString());
        return dataSourceInfo;
    }

    private InterceptChain parseInterceptChain(String plugins) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        List<Interceptor> interceptors = new ArrayList<>();
        String classPath = Configuration.class.getResource("/").getPath();
        plugins = plugins.replace(".", File.separator);
        String[] pluginClassNames = plugins.split(",");
        for (String className : pluginClassNames) {
            className = className.replace(classPath.replace("/", "\\").replaceFirst("\\\\", ""), "").replace("\\", ".").replace(".class", "");
            Class<?> clazz = Class.forName(className);
            if (null != clazz.getAnnotation(Intercept.class)) {
                interceptors.add((Interceptor) clazz.newInstance());
            }
        }
        return new InterceptChain(interceptors);
    }

    private Map<String, MappedStatement> parseMappedStatement(String mapperScan) throws IOException, ClassNotFoundException {
        String classPath = Configuration.class.getResource("/").getPath();
        mapperScan = mapperScan.replace(".", File.separator);
        String mainPath = classPath + mapperScan;
        doPath(new File(mainPath), classPaths);
        Map<String, MappedStatement> mappedStatementMap = new HashMap<>();
        for (String className : classPaths) {
            className = className.replace(classPath.replace("/", "\\").replaceFirst("\\\\", ""), "").replace("\\", ".").replace(".class", "");
            Class<?> clazz = Class.forName(className);
            for (Method method : clazz.getDeclaredMethods()) {
                if (null != method.getAnnotation(Select.class)) {
                    String sql = method.getAnnotation(Select.class).sql();
                    MappedStatement mappedStatement = new MappedStatement();
                    Parameter[] parameters = method.getParameters();
                    StringBuilder id = new StringBuilder(clazz.getName() + "|" + method.getName() + "|" + SqlCommandType.SELECT.name()
                            + "|" + sql);
                    Map<String, Object> parameterMap = new HashMap<>();
                    for (Parameter parameter : parameters) {
                        parameterMap.put(parameter.getName(), parameter);
                        id.append("|" + parameter.getName());
                    }
                    mappedStatement.setParameterMap(parameterMap);
                    mappedStatement.setSql(sql);
                    mappedStatement.setSqlCommandType(SqlCommandType.SELECT);
                    mappedStatement.setId(id.toString());
                    mappedStatementMap.put(mappedStatement.getId(), mappedStatement);
                }
            }
        }
        return mappedStatementMap;
    }

    private ExecutorType parseExecutorType(Properties properties) {
        Object executorType = properties.get("executorType");
        if (ExecutorType.BATCH.name().equals(executorType)) {
            return ExecutorType.BATCH;
        } else if (ExecutorType.REUSE.name().equals(executorType)) {
            return ExecutorType.REUSE;
        } else {
            return ExecutorType.SIMPLE;
        }

    }

    /**
     * 获取文件或文件夹下所有的类.class
     */
    private void doPath(File file, List<String> classPaths) {
        // 文件夹，遍历
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f1 : files) {
                doPath(f1, classPaths);
            }
        } else {
            // 文件，直接添加
            if (file.getName().endsWith(".class")) {
                classPaths.add(file.getPath());
            }
        }
    }


    public <T> T getMapper(Class<T> clazz, SqlSession sqlSession) {
        return mapperRegistry.getMapper(clazz, sqlSession);
    }

    public Executor newExecutor() {
        if (executorType.name().equals(ExecutorType.SIMPLE.name())) {
            Executor executor = new SimpleExecutor();
            executor = (Executor) interceptChain.pluginAll(executor);
            return executor;
        }
        return null;
    }

    public Map<String, MappedStatement> getMappedStatements() {
        return mappedStatements;
    }

    public MappedStatement getMappedStatment(Class<?> mapperInterface, Method method) {
        Select select = method.getAnnotation(Select.class);
        StringBuilder id = new StringBuilder(mapperInterface.getName() + "|" + method.getName() + "|" + SqlCommandType.SELECT.name()
                + "|" + select.sql());
        return mappedStatements.get(id.toString());
    }

    public StatementHandler getStatementHandler() {
        StatementHandler statementHandler = new PrepardStatementHandler(dataSourceInfo);
        return statementHandler;
    }
}

