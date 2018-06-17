package com.wsp.xjdbc.common.exception;

/**
 * 非法配置异常
 * Date : 2018-06-17
 * @author wsp
 * @since 2.0
 */
public class IllegalConfigException extends RuntimeException {

    public IllegalConfigException(String message) {
        super(message);
    }

    public IllegalConfigException(String message, Throwable cause) {
        super(message, cause);
    }
}
