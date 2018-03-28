package com.jd.jdbc.route;
import com.jd.jdbc.core.DataSourceDefinition;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

/**
 * 数据源路由算法抽象基类
 *
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 1.0.0.Alpha
 */
public abstract class AbstractRoute implements Route {

    private static final Logger log = LoggerFactory.getLogger(AbstractRoute.class);

    /**
     * 路由算法,从库集合为空时返回null
     * @param dataSourceDefinitions 可用的数据源集合
     * @return 数据源
     */
    public DataSourceDefinition route(List<DataSourceDefinition> dataSourceDefinitions) {
        if (CollectionUtils.isEmpty(dataSourceDefinitions)) {
            return null;
        }else if (dataSourceDefinitions.size() == 1) {
            return dataSourceDefinitions.get(0);
        } else {
            return doRoute(dataSourceDefinitions);
        }
    }

    /**
     * 根据路由算法获取真正的数据源
     * @param dataSourceWrappers 可用的数据源集合
     * @return 数据源beanId
     */
    public abstract DataSourceDefinition doRoute(List<DataSourceDefinition> dataSourceWrappers);


    /**
     * 获取数据源的权重
     * @param dataSourceDefinition 数据源定义
     * @return
     */
    protected int getWeight(DataSourceDefinition dataSourceDefinition) {
        return dataSourceDefinition.getWeight() < 0 ? 0 : dataSourceDefinition.getWeight();
    }


}
