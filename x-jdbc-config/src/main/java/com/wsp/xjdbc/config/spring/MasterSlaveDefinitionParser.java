package com.wsp.xjdbc.config.spring;

import com.wsp.xjdbc.config.StandardMasterSlaveDataSourceFactory;
import com.wsp.xjdbc.config.api.MasterDataSourceConfig;
import com.wsp.xjdbc.config.api.MasterSlaveStrategyConfig;
import com.wsp.xjdbc.config.api.SlaveDataSourceConfig;
import com.wsp.xjdbc.config.bean.MasterSlaveDataSourceConfigBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionValidationException;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.util.ArrayList;
import java.util.List;

/**
 * x-jdbc masterSlave配置自定义标签处理器
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 1.0.0.Alpha
 */
public class MasterSlaveDefinitionParser extends AbstractSimpleBeanDefinitionParser {

    private static final Logger logger = LoggerFactory.getLogger(MasterSlaveDefinitionParser.class);

    /**
     * 开始对masterSlave标签进行解析
     * @param element 当前的标签
     * @param parserContext 解析上下文
     * @param builder bean构建对象,使用builder可以根据class动态构建Bean对象
     */
    @Override
    protected void doParse(Element element,ParserContext parserContext,BeanDefinitionBuilder builder) {
        if(logger.isInfoEnabled()){
            logger.info("Start Parse X-jdbc Spring Tag...");
        }
        String idVal = element.getAttribute("id");//配置的BeanID
        //当前容器是否已有重复BeanID
        if(parserContext.getRegistry().containsBeanDefinition(idVal)){
            throw new BeanDefinitionValidationException("<xjdbc:MasterSlave> Duplicate spring bean id "+idVal);
        }

        //添加master数据源配置
        builder.addPropertyValue("master",createMasterDataSourceDefinition(element,parserContext));
        //添加slave集合数据源配置
        builder.addPropertyValue("slaves",createSlaveDataSources(element,parserContext));
        //添加strategy配置
        builder.addPropertyValue("strategyConfig",createSlaveDataSources(element,parserContext));
        BeanDefinition beanDefinition =  parserContext.getRegistry().getBeanDefinition(element.getAttribute("dataSourceFactory"));
        builder.addPropertyValue("dataSourceFactory",null!=beanDefinition?beanDefinition:new StandardMasterSlaveDataSourceFactory());

    }

    /**
     * 创建masterDataSourceBeanDefinition
     * @param element
     * @return
     */
    private BeanDefinition createMasterDataSourceDefinition(Element element,ParserContext parserContext){
        BeanDefinitionBuilder builder = getBeanDefinitionBuilder(MasterDataSourceConfig.class);
        Element masterElement  =   getMasterDataSourceElement(element);
        builder.addPropertyValue("id",masterElement.getAttribute("id"));
        builder.addPropertyValue("name",masterElement.getAttribute("name"));
        builder.addPropertyValue("roomArea",masterElement.getAttribute("roomArea"));
        builder.addPropertyValue("targetDataSource",parserContext.getRegistry().getBeanDefinition(masterElement.getAttribute("targetDataSource")));
        beanPostProcess(builder);
        return builder.getBeanDefinition();
    }




    /**
     * 创建slaveDataSourceBeanDefinition
     * @param element
     * @return
     */
    private BeanDefinition createSlaveDataSource(Element element,ParserContext parserContext){
        BeanDefinitionBuilder builder = getBeanDefinitionBuilder(SlaveDataSourceConfig.class);
        builder.addPropertyValue("id",element.getAttribute("id"));
        builder.addPropertyValue("name",element.getAttribute("name"));
        builder.addPropertyValue("roomArea",element.getAttribute("roomArea"));
        builder.addPropertyValue("enable",element.getAttribute("enable"));
        builder.addPropertyValue("weight",element.getAttribute("weight"));
        builder.addPropertyValue("targetDataSource",parserContext.getRegistry().getBeanDefinition(element.getAttribute("targetDataSource")));
        beanPostProcess(builder);
        return builder.getBeanDefinition();
    }


    /**
     * 创建slave集合BeanDefinition
     * @param element
     * @return
     */
    private List<BeanDefinition> createSlaveDataSources(Element element,ParserContext parserContext){
        ManagedList  managedList = null;
        Element slavesElement  =  getSlaveDataSourcesElement(element);
        List<Element> elements = getSlaveDataSourceElement(slavesElement);
        if(null != elements && elements.size() > 0){
            managedList = new ManagedList();
            for(Element e : elements){
                managedList.add(createSlaveDataSource(e,parserContext));
            }
        }
        return managedList;
    }


    /**
     * 创建createStrategyBeanDefinition
     * @param element
     * @return
     */
    private BeanDefinition createStrategy(Element element,ParserContext parserContext){
        BeanDefinitionBuilder builder = getBeanDefinitionBuilder(MasterSlaveStrategyConfig.class);
        builder.addPropertyValue("id",element.getAttribute("id"));
        builder.addPropertyValue("route",element.getAttribute("route"));
        beanPostProcess(builder);
        return builder.getBeanDefinition();
    }





    private BeanDefinitionBuilder getBeanDefinitionBuilder(Class c){
       return BeanDefinitionBuilder.rootBeanDefinition(c);
    }


    private void beanPostProcess(BeanDefinitionBuilder builder){
        builder.setLazyInit(false);
        builder.setScope(BeanDefinition.SCOPE_SINGLETON);
    }

    /**
     * 获取masterDataSource数据源标签
     * @param element
     * @return
     */
    private Element getMasterDataSourceElement(Element element){
        //因为xsd的校验,程序走到这里肯定是有标签的,并且masterDataSource只有一个.
        return loopFindElement(element,"masterDataSource").get(0);
    }

    /**
     * 获取slaveDataSources数据源标签
     * @param element
     * @return
     */
    private Element getSlaveDataSourcesElement(Element element){
        //因为xsd的校验,程序走到这里肯定是有标签的,并且slaveDataSources只有一个.
        return loopFindElement(element,"slaveDataSources").get(0);
    }



    /**
     * 获取slaveDataSource数据源标签
     * @param element
     * @return
     */
    private List<Element> getSlaveDataSourceElement(Element element){
        //因为xsd的校验,程序走到这里肯定是有标签的,并且slaveDataSources只有一个.
        return loopFindElement(element,"slaveDataSource");
    }



    /**
     * 从指定的element寻址所有的tagName标签
     * 添加到list中并返回
     * @param element 父标签
     * @param tagName 寻址的标签名
     * @return element中所有的tagName标签
     */
    private List<Element> loopFindElement(Element element,String tagName){
        List<Element> elements = null;
        NodeList nodeList = element.getChildNodes();
        if(null != nodeList && nodeList.getLength()>0){
            elements = new ArrayList<Element>(nodeList.getLength());
            for(int i = 0 ; i<nodeList.getLength();i++){
                Node node = nodeList.item(i);
                if(node instanceof Element){
                    //只有是标签元素的才进行判断,其他的都过滤
                    if(node.getLocalName().equals(tagName) || node.getNodeName().equals(tagName)){
                        elements.add((Element)node);
                    }
                }
            }
        }
        return elements;
    }


    /**
     * 返回该标签所对应的Class.
     * @param element
     * @return
     */
    @Override
    protected Class getBeanClass(Element element) {
        return MasterSlaveDataSourceConfigBean.class;
    }


}