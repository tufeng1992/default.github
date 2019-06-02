package com.study.test.beans.supports;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class ResourceLoader {


    private Properties properties = new Properties();

    private static final String CONTEXT_CONFIG_LOCATION = "contextConfigLocation";


    public void loadConfiguration(Map<String, Object> config) {
        InputStream inputStream = null;
        try {
            inputStream = this.getClass().getClassLoader().getResourceAsStream(config.get(CONTEXT_CONFIG_LOCATION).toString().replaceAll("classpath:", ""));
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<Class<?>> scanConfiguration() throws ClassNotFoundException {
        String configPath = properties.getProperty("scanPackage");
        List<Class<?>> classList = new ArrayList<>();
        doScanConfiguration(configPath, classList);
        return classList;
    }

    private void doScanConfiguration(String configPath, List<Class<?>> classList) throws ClassNotFoundException {
        URL url = this.getClass().getClassLoader().getResource("/" + configPath.replaceAll("\\.", "/"));
        File files = new File(url.getFile());
        for (File file : files.listFiles()) {
            if (file.isDirectory()) {
                doScanConfiguration(configPath + "." + file.getName(), classList);
            } else {
                if (file.getName().endsWith(".class")) {
                    Class<?> clazz = Class.forName(configPath + "." + file.getName().substring(0, file.getName().lastIndexOf(".class")));
                    classList.add(clazz);
                }
            }
        }
    }

    public Properties getProperties() {
        return properties;
    }
}
