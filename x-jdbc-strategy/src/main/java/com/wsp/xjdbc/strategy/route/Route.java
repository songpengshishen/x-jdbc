package com.wsp.xjdbc.strategy.route;

import com.wsp.xjdbc.config.api.SlaveDataSourceConfig;

import javax.sql.DataSource;
import java.util.List;



/**
 * 数据源路由算法接口
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/07/20
 */
public interface Route {

    /**
     * 路由算法,根据可用的数据源集合路选出一个可用的数据源
     * @param slaveDataSourceConfigs 可用的数据源集合配置
     */
     DataSource route(List<SlaveDataSourceConfig> slaveDataSourceConfigs);
}
