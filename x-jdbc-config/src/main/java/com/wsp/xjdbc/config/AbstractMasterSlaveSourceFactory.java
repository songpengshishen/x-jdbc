package com.wsp.xjdbc.config;

import com.wsp.xjdbc.common.exception.IllegalConfigException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
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
     * 默认的策略配置
     */
    private static final MasterSlaveStrategyConfig DEFAULT_STRATEGY_CONFIG = new MasterSlaveStrategyConfig();

    /**
     * 缓存的当前系统的所有DataSource,key是通过配置生成的,保证配置一样只有一个Datasource
     */
    protected static ConcurrentHashMap<String, DataSource> CACHE_DATASOURCE_MAP = new ConcurrentHashMap<String, DataSource>(1);


    @Override
    public DataSource getDataSource(MasterDataSourceConfig master, Set<SlaveDataSourceConfig> slaves,
                                    MasterSlaveStrategyConfig strategyConfig) {
        if (null == master) {
            throw new NullPointerException("MasterDataSourceConfig Can Not Empty!");
        }
        if (null == slaves || slaves.isEmpty()){
            throw new NullPointerException("SlaveDataSourceConfig Can Not Empty!");
        }
        if(null == strategyConfig){
            if(logger.isWarnEnabled()){
                logger.warn("strategyConfig is Empty,Use Default strategyConfig " + DEFAULT_STRATEGY_CONFIG);
            }
            strategyConfig = DEFAULT_STRATEGY_CONFIG;
        }
        checkMasterDataSourceConfig(master);
        checkSlaveDataSourceConfigs(slaves);
        checkStrategyConfig(strategyConfig);
        try {
            String mapKey = generateDataSourceMapKey(master,slaves,strategyConfig);
            CACHE_DATASOURCE_MAP.putIfAbsent(mapKey,createDataSource(master,slaves,strategyConfig));
            return CACHE_DATASOURCE_MAP.get(mapKey);
        }catch (Exception e){
            logger.error("An Exception Occurs when the data source is created : " , e);
            throw e;
        }
    }

    /**
     * 生成DataSourceMap的key根据三个配置对象的hashCode
     * @return
     */
    private String generateDataSourceMapKey(MasterDataSourceConfig master,Set<SlaveDataSourceConfig> slaves,MasterSlaveStrategyConfig strategyConfig){
          int keyVal = master.hashCode() + strategyConfig.hashCode();
          for(SlaveDataSourceConfig slave : slaves){
              keyVal += slave.hashCode();
          }
          return String.valueOf(keyVal);
    }


    private void checkMasterDataSourceConfig(MasterDataSourceConfig master){
        checkDataSourceConfig(master);
    }

    private void checkSlaveDataSourceConfigs(Set<SlaveDataSourceConfig> slaves){
        for(SlaveDataSourceConfig slave : slaves){
            checkDataSourceConfig(slave);
            // TODO: 2018/6/17  权重验证
        }
    }

    private void checkStrategyConfig(MasterSlaveStrategyConfig strategyConfig){
        // TODO: 2018/6/17 策略验证
    }

    private void checkDataSourceConfig(AbstractDataSourceConfig dataSourceConfig){
        if(null == dataSourceConfig.getTargetDataSource()){
            throw new IllegalConfigException("targetDataSource is Empty! [ " + dataSourceConfig + "]");
        }
        if(null == dataSourceConfig.getName() || dataSourceConfig.getName().isEmpty()){
            throw new IllegalConfigException("name is Empty! [ " + dataSourceConfig + "]");
        }
        if(null == dataSourceConfig.getRoomArea() || dataSourceConfig.getRoomArea().isEmpty()){
            if(logger.isWarnEnabled()){
                logger.warn("roomArea is Empty! [ " + dataSourceConfig + "]" );
            }
        }
    }




    /**
     * 创建数据源由子类实现
     *
     * @return
     */
    protected abstract DataSource createDataSource(MasterDataSourceConfig master, Set<SlaveDataSourceConfig> slaves,
                                                   MasterSlaveStrategyConfig strategyConfig) throws IllegalStateException ;


}
