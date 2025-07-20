package org.acme.interceptor;

import io.quarkus.logging.Log;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import java.util.Arrays;
import java.util.stream.Collectors;

@Logged
@Interceptor
public class LoggingInterceptor {

    @AroundInvoke
    public Object logMethodExecution(InvocationContext context) throws Exception {
        Logged logged = getLoggedAnnotation(context);
        
        String className = context.getTarget().getClass().getSimpleName();
        String methodName = context.getMethod().getName();
        
        // 记录方法开始执行
        long startTime = System.currentTimeMillis();
        
        // 构建日志消息
        StringBuilder logMessage = new StringBuilder();
        logMessage.append("执行方法: ").append(className).append(".").append(methodName).append("()");
        
        // 记录参数
        if (logged.logParameters() && context.getParameters().length > 0) {
            String parameters = Arrays.stream(context.getParameters())
                .map(param -> param == null ? "null" : param.toString())
                .collect(Collectors.joining(", "));
            logMessage.append(" | 参数: [").append(parameters).append("]");
        }
        
        Log.info(logMessage.toString() + " - 开始执行");
        
        Object result = null;
        Exception exception = null;
        
        try {
            // 执行实际方法
            result = context.proceed();
            
            // 记录执行时间和结果
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            
            StringBuilder resultMessage = new StringBuilder();
            resultMessage.append("方法执行完成: ").append(className).append(".").append(methodName).append("()");
            
            if (logged.logExecutionTime()) {
                resultMessage.append(" | 执行时间: ").append(executionTime).append("ms");
            }
            
            if (logged.logResult() && result != null) {
                String resultStr = result.toString();
                if (resultStr.length() > 200) {
                    resultStr = resultStr.substring(0, 200) + "...";
                }
                resultMessage.append(" | 返回值: ").append(resultStr);
            }
            
            Log.info(resultMessage.toString());
            
        } catch (Exception e) {
            exception = e;
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            
            Log.error("方法执行异常: " + className + "." + methodName + "()" + 
                     " | 执行时间: " + executionTime + "ms" + 
                     " | 异常: " + e.getMessage(), e);
            
            throw e;
        }
        
        return result;
    }
    
    private Logged getLoggedAnnotation(InvocationContext context) {
        // 首先检查方法级别的注解
        Logged methodAnnotation = context.getMethod().getAnnotation(Logged.class);
        if (methodAnnotation != null) {
            return methodAnnotation;
        }
        
        // 然后检查类级别的注解
        Logged classAnnotation = context.getTarget().getClass().getAnnotation(Logged.class);
        if (classAnnotation != null) {
            return classAnnotation;
        }
        
        // 如果都没有找到，返回一个默认的注解实现
        return new Logged() {
            @Override
            public boolean logParameters() { return true; }
            
            @Override
            public boolean logResult() { return true; }
            
            @Override
            public boolean logExecutionTime() { return true; }
            
            @Override
            public String level() { return "INFO"; }
            
            @Override
            public Class<? extends java.lang.annotation.Annotation> annotationType() {
                return Logged.class;
            }
        };
    }
}