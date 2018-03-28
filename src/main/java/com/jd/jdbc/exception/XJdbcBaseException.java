package com.jd.jdbc.exception;

/**
 * xJdbc异常基础类
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 1.0.0.Alpha
 */
public class XJdbcBaseException extends RuntimeException{

    protected XJdbcBaseException(){
        super();
    }

    public XJdbcBaseException(String message){super(message);}

    protected XJdbcBaseException(String message,Throwable cause){
        super(message,cause);
    }

    protected XJdbcBaseException(Throwable cause){
        super(cause);
    }
}
