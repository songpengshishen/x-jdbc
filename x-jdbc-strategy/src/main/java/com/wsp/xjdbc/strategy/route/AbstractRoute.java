package com.wsp.xjdbc.strategy.route;


import com.wsp.xjdbc.config.api.SlaveDataSourceConfig;
import org.apache.commons.collections.CollectionUtils;
import javax.sql.DataSource;
import java.util.List;



/**
 * 数据源路由算法抽象基类
 *
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/07/20
 */
public abstract class AbstractRoute implements Route {



    /**
     * 路由算法,从库集合为空时返回null
     * @param slaveDataSourceConfigs 可用的数据源集合配置
     * @return 数据源
     */
    public DataSource route(List<SlaveDataSourceConfig> slaveDataSourceConfigs) {
        if (CollectionUtils.isEmpty(slaveDataSourceConfigs)) {
            return null;
        }else if (slaveDataSourceConfigs.size() == 1) {
            return slaveDataSourceConfigs.get(0).getTargetDataSource();
        } else {
            return doRoute(slaveDataSourceConfigs);
        }
    }

    /**
     * 根据路由算法获取真正的数据源
     * @param slaveDataSourceConfigs 可用的数据源集合配置
     */
    public abstract DataSource doRoute(List<SlaveDataSourceConfig> slaveDataSourceConfigs);


    /**
     * 获取数据源的权重
     * @param slaveDataSourceConfig 从数据源配置
     * @return 数据源权重值
     */
    protected int getWeight(SlaveDataSourceConfig slaveDataSourceConfig) {
        return slaveDataSourceConfig.getWeight() < 0 ? 0 : slaveDataSourceConfig.getWeight();
    }


}
