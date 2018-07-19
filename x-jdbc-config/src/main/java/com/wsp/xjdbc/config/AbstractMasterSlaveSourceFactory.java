package com.wsp.xjdbc.config;

import com.wsp.xjdbc.common.enums.RoomAreaEnum;
import com.wsp.xjdbc.common.enums.RouteEnum;
import com.wsp.xjdbc.common.exception.XJdbcConfigurationException;
import com.wsp.xjdbc.common.utils.EnumUtils;
import com.wsp.xjdbc.config.api.AbstractDataSourceConfig;
import com.wsp.xjdbc.config.api.MasterDataSourceConfig;
import com.wsp.xjdbc.config.api.MasterSlaveStrategyConfig;
import com.wsp.xjdbc.config.api.SlaveDataSourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.sql.DataSource;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * master\slave 数据源创建工厂抽象父类
 * Date : 2018-06-17
 *
 * @author wsp
 * @since 2.0
 */
public abstract class AbstractMasterSlaveSourceFactory implements MasterSlaveDataSourceFactory {

    private static final Logger logger = LoggerFactory.getLogger(AbstractMasterSlaveSourceFactory.class);

    /**
     * 缓存的当前系统的所有DataSource,key是通过三个配置类的hashcode生成的,保证配置一样只有一个Datasource
     */
    protected static ConcurrentHashMap<String, DataSource> CACHE_DATASOURCE_MAP = new ConcurrentHashMap<String, DataSource>(1);

    /**
     * 获取master/slave数据源,验证及创建
     *
     * @param master         master配置
     * @param slaves         slaves配置集合
     * @param strategyConfig 数据源策略配置
     * @return
     */
    @Override
    public DataSource getDataSource(MasterDataSourceConfig master, Set<? extends SlaveDataSourceConfig> slaves,
                                    MasterSlaveStrategyConfig strategyConfig) {
        if (null == master) {
            throw new NullPointerException("MasterDataSourceConfig Can Not Empty!");
        }
        if (null == slaves || slaves.isEmpty()) {
            throw new NullPointerException("SlaveDataSourceConfig Can Not Empty!");
        }
        if (null == strategyConfig) {
            if (logger.isWarnEnabled()) {
                logger.warn("No Startup Route Strategy!");
            }
        }
        checkDataSourceAndStrategyConfigs(master, slaves, strategyConfig);
        try {
            String mapKey = generateDataSourceMapKey(master, slaves, strategyConfig);
            CACHE_DATASOURCE_MAP.putIfAbsent(mapKey, createDataSource(master, slaves, strategyConfig));
            return CACHE_DATASOURCE_MAP.get(mapKey);
        } catch (Exception e) {
            logger.error("An Exception Occurs when the data source is created : ", e);
            throw e;
        }
    }

    /**
     * 生成DataSourceMap的key根据三个配置对象的hashCode
     *
     * @return
     */
    private String generateDataSourceMapKey(MasterDataSourceConfig master, Set<? extends SlaveDataSourceConfig> slaves, MasterSlaveStrategyConfig strategyConfig) {
        int keyVal = master.hashCode();
        if(null != strategyConfig){
            keyVal  += strategyConfig.hashCode();
        }
        for (SlaveDataSourceConfig slave : slaves) {
            keyVal += slave.hashCode();
        }
        return String.valueOf(keyVal);
    }


    private void checkDataSourceAndStrategyConfigs(MasterDataSourceConfig master, Set<? extends SlaveDataSourceConfig> slaves, MasterSlaveStrategyConfig strategyConfig) {
        Set<String> distinctSoureNameSet = new HashSet<String>();
        //验证master数据源配置
        checkDataSourceConfig(master);
        distinctSoureNameSet.add(master.getName());
        //验证配置策略
        RouteEnum routeEnum = null;
        //检查策略配置中路由选项
        if (null != strategyConfig) {
            routeEnum = EnumUtils.getByCache(RouteEnum.class, strategyConfig.getRoute());
            if (null == routeEnum) {
                throw new XJdbcConfigurationException("StrategyConfig Route  This configuration [" + strategyConfig.getRoute() + "]can not be found");
            }
        }
        //迭代检查从数据源
        int enableTrueNumber = 0;//从数据源中配置了enable为True选项的个数
        for (SlaveDataSourceConfig slave : slaves) {
            checkDataSourceConfig(slave);
            if (slave.isEnable()) {
                enableTrueNumber++;
            }
            distinctSoureNameSet.add(slave.getName());
        }

        if (distinctSoureNameSet.size() != (slaves.size() + 1)) {
            throw new XJdbcConfigurationException("configuration name Attr Cannot Cannot Duplicate MasterDataSourceConfig : " + master +
                    " slaveDataSourceConfig : " + slaves);
        }
        if (enableTrueNumber > 1) {
            throw new XJdbcConfigurationException("slaveDataSourceConfig enable Can only be configured with one");
        }

        if (null != routeEnum && enableTrueNumber > 0) {
            throw new XJdbcConfigurationException("routing policy has been configured [" + strategyConfig.getRoute() + "]enable can not be configured!");
        }

        if (null == routeEnum && enableTrueNumber == 0) {
            throw new XJdbcConfigurationException("No configuration routing policy,You need to Open a slave please set enable");
        }

    }

    private void checkDataSourceConfig(AbstractDataSourceConfig dataSourceConfig) {

        if (null == dataSourceConfig.getTargetDataSource()) {
            throw new XJdbcConfigurationException("targetDataSource is Empty! [ " + dataSourceConfig + "]");
        }
        if (null == dataSourceConfig.getName() || dataSourceConfig.getName().isEmpty()) {
            throw new XJdbcConfigurationException("name is Empty! [ " + dataSourceConfig + "]");
        }
        if (null == dataSourceConfig.getRoomArea() || dataSourceConfig.getRoomArea().isEmpty()) {
            if (logger.isWarnEnabled()) {
                logger.warn("roomArea is Empty! [ " + dataSourceConfig + "]");
            }
        } else {
            if (RoomAreaEnum.valueOf(dataSourceConfig.getRoomArea()) == null) {
                throw new XJdbcConfigurationException("dataSourceConfig roomArea This configuration [" + dataSourceConfig.getRoomArea() + "]can not be found");
            }
        }
    }

    /**
     * 创建数据源由子类实现
     *
     * @return
     */
    protected abstract DataSource createDataSource(MasterDataSourceConfig master, Set<? extends SlaveDataSourceConfig> slaves,
                                                   MasterSlaveStrategyConfig strategyConfig) throws IllegalStateException;


}
