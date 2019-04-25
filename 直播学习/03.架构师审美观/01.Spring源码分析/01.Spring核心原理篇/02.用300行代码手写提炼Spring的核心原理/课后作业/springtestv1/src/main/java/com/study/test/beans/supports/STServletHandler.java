package com.study.test.beans.supports;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;

public class STServletHandler {

    private HandlerMapping handlerMapping;

    public STServletHandler(HandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }


    public Object doDispatch(HttpServletRequest request, HttpServletResponse response) throws InvocationTargetException, IllegalAccessException {
        Method method = handlerMapping.getMethod();
        Object[] args = new Object[handlerMapping.getArgs().length];
        for (int i = 0; i < handlerMapping.getArgs().length; i++) {
            String paramsName = handlerMapping.getParameterNames()[i];
            String paramsValue = request.getParameter(paramsName);
            Class<?> typeClass = handlerMapping.getArgs()[i].getType();
            args[i] = resolverType(paramsValue, String.class, typeClass);
        }
        return method.invoke(handlerMapping.getBean(), args);
    }

    private Object resolverType(String source, Class<?> paramType, Class<?> targetType) {
        String trimmed = StringUtils.trimAllWhitespace(source);

        if (paramType.equals(targetType)) {
            return source;
        } if (Byte.class == targetType) {
            return (isHexNumber(trimmed) ? Byte.decode(trimmed) : Byte.valueOf(trimmed));
        }
        else if (Short.class == targetType) {
            return (isHexNumber(trimmed) ? Short.decode(trimmed) : Short.valueOf(trimmed));
        }
        else if (Integer.class == targetType) {
            return (isHexNumber(trimmed) ? Integer.decode(trimmed) : Integer.valueOf(trimmed));
        }
        else if (Long.class == targetType) {
            return (isHexNumber(trimmed) ? Long.decode(trimmed) : Long.valueOf(trimmed));
        }
        else if (BigInteger.class == targetType) {
            return (isHexNumber(trimmed) ? decodeBigInteger(trimmed) : new BigInteger(trimmed));
        }
        else if (Float.class == targetType) {
            return Float.valueOf(trimmed);
        }
        else if (Double.class == targetType) {
            return Double.valueOf(trimmed);
        }
        else if (BigDecimal.class == targetType || Number.class == targetType) {
            return new BigDecimal(trimmed);
        } else {
            throw new IllegalArgumentException(
                    "Cannot convert String [" + source + "] to target class [" + targetType.getName() + "]");
        }
    }

    /**
     * Determine whether the given {@code value} String indicates a hex number,
     * i.e. needs to be passed into {@code Integer.decode} instead of
     * {@code Integer.valueOf}, etc.
     */
    private static boolean isHexNumber(String value) {
        int index = (value.startsWith("-") ? 1 : 0);
        return (value.startsWith("0x", index) || value.startsWith("0X", index) || value.startsWith("#", index));
    }

    /**
     * Decode a {@link java.math.BigInteger} from the supplied {@link String} value.
     * <p>Supports decimal, hex, and octal notation.
     * @see BigInteger#BigInteger(String, int)
     */
    private static BigInteger decodeBigInteger(String value) {
        int radix = 10;
        int index = 0;
        boolean negative = false;

        // Handle minus sign, if present.
        if (value.startsWith("-")) {
            negative = true;
            index++;
        }

        // Handle radix specifier, if present.
        if (value.startsWith("0x", index) || value.startsWith("0X", index)) {
            index += 2;
            radix = 16;
        }
        else if (value.startsWith("#", index)) {
            index++;
            radix = 16;
        }
        else if (value.startsWith("0", index) && value.length() > 1 + index) {
            index++;
            radix = 8;
        }

        BigInteger result = new BigInteger(value.substring(index), radix);
        return (negative ? result.negate() : result);
    }
}
