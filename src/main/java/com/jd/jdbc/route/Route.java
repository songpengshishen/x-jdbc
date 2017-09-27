package com.jd.jdbc.route;

import com.jd.jdbc.ds.DataSourceWrapper;
import javax.sql.DataSource;
import java.util.List;

/**
 * 数据源路由算法接口
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 1.0.0.Alpha
 */
public interface Route {

    /**
     * 路由算法
     * @param dataSourceWrappers 可用的数据源集合
     * @return 数据源BeanId
     */
    public String route(List<DataSourceWrapper> dataSourceWrappers);
}
