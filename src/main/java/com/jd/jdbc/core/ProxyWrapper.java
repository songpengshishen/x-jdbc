package com.jd.jdbc.core;
import com.jd.jdbc.exception.XJdbcBaseException;

import java.sql.SQLException;
import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 代理Wrapper抽象类,负责代理jdbc Wrapper接口的缺省方法
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/03/28
 */
public class ProxyWrapper implements Wrapper{


    /**
     * 记录JDBC方法调用的列表
     */
    private final ArrayList<JdbcMethodInvocation> jdbcMethodInvocations = new ArrayList<JdbcMethodInvocation>();

    @SuppressWarnings("unchecked")
    @Override
    public final <T> T unwrap(final Class<T> iface) throws SQLException {
        if (isWrapperFor(iface)) {
            return (T) this;
        }
        throw new SQLException(String.format("[%s] cannot be unwrapped as [%s]", getClass().getName(), iface.getName()));
    }

    @Override
    public final boolean isWrapperFor(final Class<?> iface) throws SQLException {
        return iface.isInstance(this);
    }

    /**
     * 记录方法调用.
     * @param targetClass 目标类
     * @param methodName 方法名称
     * @param argumentTypes 参数类型
     * @param arguments 参数
     */
    public final void recordMethodInvocation(final Class<?> targetClass, final String methodName, final Class<?>[] argumentTypes, final Object[] arguments) {
        try {
            jdbcMethodInvocations.add(new JdbcMethodInvocation(targetClass.getMethod(methodName, argumentTypes), arguments));
        } catch (final NoSuchMethodException ex) {
            throw new XJdbcBaseException("recordMethodInvocation Exception!",ex);
        }
    }

    /**
     * 回放记录的方法调用.
     * @param target 目标对象
     */
    public final void replayMethodsInvocation(final Object target) {
        for (JdbcMethodInvocation each : jdbcMethodInvocations) {
            each.invoke(target);
        }
    }
}

