package com.jd.jdbc.route;
import com.jd.jdbc.core.DataSourceDefinition;
import java.util.List;

/**
 * 数据源路由算法接口
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/03/28
 */
public interface Route {

    /**
     * 路由算法,根据可用的数据源集合路选出一个可用的数据源
     * @param dataSourceDefinitions 可用的数据源集合
     */
    public DataSourceDefinition route(List<DataSourceDefinition> dataSourceDefinitions);
}
