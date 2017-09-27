package com.jd.jdbc.route;
import com.jd.jdbc.ds.DataSourceWrapper;
import com.jd.jdbc.enums.RouteEnum;
import com.jd.jdbc.exception.XJdbcConfigurationException;
import com.jd.jdbc.exception.XJdbcNoAliveDataSourceException;
import com.jd.jdbc.utils.SpringUtils;
import javax.sql.DataSource;
import java.util.List;

/**
 * 数据源路由算法抽象基类
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 1.0.0.Alpha
 */
public abstract class AbstractRoute implements Route {


    /**
     * 工厂获取路由算法类
     * @param routeName 路由算法名称
     * @return 实现了路由算法的子类
     */
    public static Route getInstance(String routeName, String beanId) {
        Route route =  null;
        switch (RouteEnum.valueOf(routeName)){
            case SIMPLE:route = new SimpleRoute();break;
            case RANDOM:route = new RandomRoute();break;
            case LOCALROOM:route = new LocalRoomRoute();break;
            default: throw new XJdbcConfigurationException("route Configuration Error!routeName : " + routeName,beanId);
        }
        return route;
    }

    /**
     * 路由算法
     * @param dataSourceWrappers 可用的数据源集合
     * @return 数据源
     */
    public DataSource route(List<DataSourceWrapper> dataSourceWrappers) {
        if (dataSourceWrappers.size() == 0) {
            throw new XJdbcNoAliveDataSourceException();
        }
        if (dataSourceWrappers.size() == 1) {
            return getDataSourceByBeanId(dataSourceWrappers.get(0).getId());
        } else {
            return doRoute(dataSourceWrappers);
        }
    }

    /**
     * 根据路由算法获取真正的数据源
     * @param dataSourceWrappers 可用的数据源集合
     * @return 数据源
     */
    public abstract DataSource doRoute(List<DataSourceWrapper> dataSourceWrappers);


    /**
     * 获取数据源的权重
     * @param dataSourceWrapper 数据源包装类
     * @return
     */
    protected int getWeight(DataSourceWrapper dataSourceWrapper) {
        return dataSourceWrapper.getWeight() < 0 ? 0 : dataSourceWrapper.getWeight();
    }

    /**
     * 根据BeanId获取真正的数据源
     * @param beanId
     * @return
     */
    protected DataSource getDataSourceByBeanId(String beanId){
        return  SpringUtils.getBean(beanId,DataSource.class);
    }


}
