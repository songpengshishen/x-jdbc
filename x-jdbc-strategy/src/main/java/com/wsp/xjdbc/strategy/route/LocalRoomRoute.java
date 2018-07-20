package com.wsp.xjdbc.strategy.route;

import com.wsp.xjdbc.config.api.SlaveDataSourceConfig;

import javax.sql.DataSource;
import java.util.List;

/**
 * 数据源同机房调用路由算法基类
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/07/20
 */
public class LocalRoomRoute extends AbstractRoute {

    /**
     * 根据路由算法获取真正的数据源
     * @param slaveDataSourceConfigs 可用的数据源集合配置类
     * @return 数据源
     */
    @Override
    public DataSource doRoute(List<SlaveDataSourceConfig> slaveDataSourceConfigs) {
        // TODO: 2017/9/27 暂未实现 
        return null;
    }


}

