package com.jd.jdbc.exception;

/**
 * XJdbc没有存活的数据源异常类
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 1.0.0.Alpha
 */
public class XJdbcNoAliveDataSourceException extends XJdbcBaseException {

    public XJdbcNoAliveDataSourceException() {
        super("current No alive dataSource!");
    }
}
