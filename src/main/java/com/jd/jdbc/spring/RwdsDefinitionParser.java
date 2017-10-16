package com.jd.jdbc.spring;
import com.jd.jdbc.ds.DataSourceCluterConfig;
import com.jd.jdbc.ds.DataSourceWrapper;
import com.jd.jdbc.ds.ReadWriteMultipleDataSource;
import com.jd.jdbc.enums.RouteEnum;
import com.jd.jdbc.route.RouteFactory;
import org.apache.commons.collections.CollectionUtils;
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
 * rwds 自定义标签处理
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 1.0.0.Alpha
 */
public class RwdsDefinitionParser extends AbstractSimpleBeanDefinitionParser {

    private static final Logger log = LoggerFactory.getLogger(RwdsDefinitionParser.class);

    /**
     * id标签名称
     */
    private final String ID_TAG_NAME = "id";

    /**
     * route标签名称
     */
    private final String ROUTE_TAG_NAME = "route";

    /**
     * masterTargetDataSource标签名称
     */
    private final String MASTER_DATASOURCE_TAG_NAME = "masterDataSource";


    /**
     * slaveDataSources标签名称
     */
    private final String SLAVE_DATASOURCES_TAG_NAME = "slaveDataSources";



    /**
     * dataSource标签名称
     */
    private final String DATASOURCE_TAG_NAME = "dataSource";


    /**
     * deployRoom标签名称
     */
    private final String DEPLOY_ROOM_TAG_NAME = "deployRoom";


    /**
     * role标签名称
     */
    private final String ROLE_TAG_NAME = "role";



    /**
     * weight标签名称
     */
    private final String WEIGHT_TAG_NAME = "weight";

    /**
     * 开始对rwds标签进行解析
     * @param element 当前的标签
     * @param parserContext 解析上下文
     * @param builder bean构建对象,使用builder可以根据class动态构建Bean对象
     */
    @Override
    protected void doParse(Element element,ParserContext parserContext,BeanDefinitionBuilder builder) {
        if(log.isDebugEnabled()){
            log.debug("Start Parse X-jdbc Spring Tag...");
        }
        String idVal = element.getAttribute(ID_TAG_NAME);//beanID
        //当前容器是否已有重复BeanID
        if(parserContext.getRegistry().containsBeanDefinition(idVal)){
            throw new BeanDefinitionValidationException("xjdbc-Rwds Duplicate spring bean id "+idVal);
        }
        //添加路由算法
        builder.addPropertyValue(ROUTE_TAG_NAME,createRouteBeanDefinition(element));
        //添加数据源集群
        builder.addPropertyValue("dataSourceCluterConfig",createDataSourceCluter(element));
    }

    /**
     * 创建路由类BeanDefinition
     * @param element
     * @return
     */
    private BeanDefinition createRouteBeanDefinition(Element element){
        String routeVal = element.getAttribute(ROUTE_TAG_NAME);//获取路由标签值
        String idVal = element.getAttribute(ID_TAG_NAME);//beanID
        RouteEnum routeEnum =  RouteEnum.valueOfByCode(routeVal);
        if(null == routeEnum){
            throw new BeanDefinitionValidationException("xjdbc-Rwds Attribute route is Illegal!route : " + routeVal);
        }
        BeanDefinitionBuilder builder = getBeanDefinitionBuilder(RouteFactory.getInstance(routeVal,idVal).getClass());
        beanPostProcess(builder);
        return builder.getBeanDefinition();
    }

    /**
     * 创建数据源集群类 BeanDefinition
     * @param element
     * @return
     */
    private BeanDefinition createDataSourceCluter(Element element){
        BeanDefinitionBuilder builder = getBeanDefinitionBuilder(DataSourceCluterConfig.class);
        builder.addPropertyValue("master",createDataSource(getMasterDataSourceElement(element)));
        builder.addPropertyValue("slaveList",createSlaveDataSource(element));
        beanPostProcess(builder);
        return builder.getBeanDefinition();
    }


    /**
     * 创建slave数据源
     * @param element
     * @return
     */
    private List<BeanDefinition> createSlaveDataSource(Element element){
        ManagedList  managedList = null;
        Element targetDataSource  =  getSlaveDataSourceElement(element);
        List<Element> dsElement = loopFindElement(targetDataSource,DATASOURCE_TAG_NAME);
        if(CollectionUtils.isNotEmpty(dsElement)){
            managedList = new ManagedList();
            for(Element e : dsElement){
                managedList.add(createDataSource(e));
            }
        }
        return managedList;
    }



    /**
     * 创建数据源 Wrapper BeanDefinition
     * @param element
     * @return
     */
    private BeanDefinition createDataSource(Element element){
        BeanDefinitionBuilder builder = getBeanDefinitionBuilder(DataSourceWrapper.class);
        builder.addPropertyValue(ID_TAG_NAME,element.getAttribute(ID_TAG_NAME));
        builder.addPropertyValue(DEPLOY_ROOM_TAG_NAME,element.getAttribute(DEPLOY_ROOM_TAG_NAME));
        builder.addPropertyValue(ROLE_TAG_NAME,element.getAttribute(ROLE_TAG_NAME));
        builder.addPropertyValue(WEIGHT_TAG_NAME,element.getAttribute(WEIGHT_TAG_NAME));
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
     * 获取Master数据源标签
     * @param element
     * @return
     */
    private Element getMasterDataSourceElement(Element element){
        //因为xsd的校验,程序走到这里肯定是有标签的,并且MASTER_DATASOURCE_TAG_NAME只有一个.
        return loopFindElement(element,MASTER_DATASOURCE_TAG_NAME).get(0);
    }


    private Element getSlaveDataSourceElement(Element element){
        //因为xsd的校验,程序走到这里肯定是有标签的,并且SLAVE_DATASOURCES_TAG_NAME只有一个.
        return loopFindElement(element,SLAVE_DATASOURCES_TAG_NAME).get(0);
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
        return ReadWriteMultipleDataSource.class;
    }
}