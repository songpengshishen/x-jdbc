package com.wsp.xjdbc.config.spring;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * x-jdbc spring 自定义标签处理器
 * 用来注册标签及标签解析处理类的对应关系
 * Date : 2018-06-18
 * @author wsp
 * @since 2.0
 */
public class XJdbcNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("masterSlave", new MasterSlaveDefinitionParser());
    }
}
