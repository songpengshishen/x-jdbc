package com.jd.jdbc.route;
import com.jd.jdbc.core.DataSourceDefinition;
import java.util.List;

/**
 * 简单的读写分离路由算法类,直接返回一个读库数据源
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 1.0.0.Alpha
 */
public class SimpleRoute extends AbstractRoute implements Route{

    /**
     * 根据路由算法获取真正的数据源
     * @param dataSourceDefinitions 可用的数据源集合
     * @return 数据源
     */
    @Override
    public DataSourceDefinition doRoute(List<DataSourceDefinition> dataSourceDefinitions) {
       return dataSourceDefinitions.get(0);
    }
}
