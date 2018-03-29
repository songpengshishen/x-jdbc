package com.jd.jdbc.core;
import com.jd.jdbc.exception.XJdbcBaseException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * JDBC方法Invocation执行对象
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/03/29
 */
public class JdbcMethodInvocation {

    /**
     * 执行的JDBC方法对象
     */
    private Method method;


    /**
     * 执行的JDBC方法参数
     */
    private Object[] arguments;



    public JdbcMethodInvocation(Method method,Object[] arguments){
        this.method = method;
        this.arguments = arguments;
    }
    
    /**
     * 执行JDBC方法
     */
    public void invoke(final Object target) {
        try {
            method.invoke(target, arguments);
        } catch (final IllegalAccessException | InvocationTargetException ex) {
            throw new XJdbcBaseException("Invoke jdbc method exception", ex);
        }
    }
}
