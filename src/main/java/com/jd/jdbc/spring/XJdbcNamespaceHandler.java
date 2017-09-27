package com.jd.jdbc.spring;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
/**
 * xjdbc Spring自定义标签处理
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 1.0.0.Alpha
 */
public class XJdbcNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("rwds", new RwdsDefinitionParser());
        registerBeanDefinitionParser("cluter", new CluterDefinitionParser());
    }
}
