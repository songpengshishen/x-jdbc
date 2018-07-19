package com.wsp.xjdbc.common.exception;

/**
 * XJdbc没有存活的数据源异常类
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/07/19
 */
public class XJdbcNoAliveDataSourceException extends XJdbcBaseException {

    public XJdbcNoAliveDataSourceException() {
        super("current No alive dataSource!");
    }
}
