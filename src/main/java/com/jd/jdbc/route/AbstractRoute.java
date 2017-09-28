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
     * 路由算法
     * @param dataSourceWrappers 可用的数据源集合
     * @return 数据源
     */
    public String route(List<DataSourceWrapper> dataSourceWrappers) {
        if (dataSourceWrappers.size() == 0) {
            throw new XJdbcNoAliveDataSourceException();
        }
        if (dataSourceWrappers.size() == 1) {
            return dataSourceWrappers.get(0).getId();
        } else {
            return doRoute(dataSourceWrappers);
        }
    }

    /**
     * 根据路由算法获取真正的数据源
     * @param dataSourceWrappers 可用的数据源集合
     * @return 数据源beanId
     */
    public abstract String doRoute(List<DataSourceWrapper> dataSourceWrappers);


    /**
     * 获取数据源的权重
     * @param dataSourceWrapper 数据源包装类
     * @return
     */
    protected int getWeight(DataSourceWrapper dataSourceWrapper) {
        return dataSourceWrapper.getWeight() < 0 ? 0 : dataSourceWrapper.getWeight();
    }




}
