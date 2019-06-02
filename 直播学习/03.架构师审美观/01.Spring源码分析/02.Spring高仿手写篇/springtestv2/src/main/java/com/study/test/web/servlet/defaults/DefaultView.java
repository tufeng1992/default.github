package com.study.test.web.servlet.defaults;

import com.study.test.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultView implements View {

    private static final String DEFAULT_CONTENT_TYPE = "text/html;charset=utf-8";

    private File viewFile;

    public static String getDefaultContentType() {
        return DEFAULT_CONTENT_TYPE;
    }

    public DefaultView(File viewFile) {
        this.viewFile = viewFile;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        StringBuffer sb = new StringBuffer();
        RandomAccessFile randomAccessFile = new RandomAccessFile(viewFile, "r");

        try {
            String l = null;
            while (null != (l = randomAccessFile.readLine())) {
                l = new String(l.getBytes("iso-8859-1"), "utf-8");
                Pattern p = Pattern.compile("￥\\{[^\\}]+\\}", Pattern.CASE_INSENSITIVE);
                Matcher matcher = p.matcher(l);
                while (matcher.find()) {
                    String paramName = matcher.group();
                    paramName = paramName.replaceAll("￥\\{|\\}", "");
                    if (!model.containsKey(paramName)) {
                        continue;
                    }
                    Object objectV = model.get(paramName);
                    if (null == objectV) {
                        objectV = "";
                    }
                    l = matcher.replaceFirst(makeStringForRegExp(objectV.toString()));
                    matcher = p.matcher(l);
                }
                sb.append(l);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            randomAccessFile.close();
        }
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(sb.toString());
    }


    private static String makeStringForRegExp(String str) {
        return str.replace("\\", "\\\\").replace("*", "\\*")
                .replace("+", "\\+").replace("|", "\\|")
                .replace("(", "\\(").replace(")", "\\)")
                .replace("{", "\\{").replace("}", "\\}")
                .replace("^", "\\^").replace("$", "\\$")
                .replace("[", "\\[").replace("]", "\\]")
                .replace(".", "\\.").replace("?", "\\?")
                .replace(",", "\\,").replace("&", "\\&");
    }
}
