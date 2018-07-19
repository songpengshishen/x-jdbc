package com.wsp.xjdbc.common.exception;

/**
 * xJdbc异常基础类
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/07/19
 */
public class XJdbcBaseException extends RuntimeException{

    protected XJdbcBaseException(){
        super();
    }

    public XJdbcBaseException(String message){super(message);}

    public XJdbcBaseException(String message, Throwable cause){
        super(message,cause);
    }

    protected XJdbcBaseException(Throwable cause){
        super(cause);
    }
}
