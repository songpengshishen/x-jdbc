package com.jd.jdbc.spring;

import com.jd.jdbc.ds.DataSourceCluterConfig;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * cluter 自定义标签处理
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 1.0.0.Alpha
 */
public class CluterDefinitionParser extends AbstractSimpleBeanDefinitionParser {

    /**
     * element 相当于对应的element元素 parserContext 解析的上下文 builder 用于该标签的实现
     */
    @Override
    protected void doParse(Element element, ParserContext parserContext,
                           BeanDefinitionBuilder builder) {

    }


    @Override
    protected Class getBeanClass(Element element) {
        // 返回该标签所定义的类实现,在这里是为了创建出CacheProxy对象
        return DataSourceCluterConfig.class;
    }
}