package com.jd.jdbc.exception;

/**
 * xJdbc配置异常类
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 1.0.0.Alpha
 */
public class XJdbcConfigurationException extends XJdbcBaseException{

    /**
     * spring beanID
     */
    private String beanId;

    public XJdbcConfigurationException(){super();}

    public XJdbcConfigurationException(String beanId){
        super("Error Configuration bean with beanId \'" + beanId + "\': ");
        this.beanId = beanId;
    }

    public XJdbcConfigurationException(String beanId,String msg){
        super(msg + " beanId: " + beanId);
        this.beanId = beanId;
    }

}
