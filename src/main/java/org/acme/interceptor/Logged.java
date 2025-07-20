package org.acme.interceptor;

import jakarta.interceptor.InterceptorBinding;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@InterceptorBinding
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Logged {
    
    /**
     * 是否记录参数
     */
    boolean logParameters() default true;
    
    /**
     * 是否记录返回值
     */
    boolean logResult() default true;
    
    /**
     * 是否记录执行时间
     */
    boolean logExecutionTime() default true;
    
    /**
     * 日志级别
     */
    String level() default "INFO";
}