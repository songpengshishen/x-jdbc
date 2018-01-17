package com.jd.jdbc.config;

import com.jd.jdbc.core.ds.DataSourceDefinition;
import com.jd.jdbc.enums.RouteEnum;
import com.jd.jdbc.route.Route;
import com.jd.jdbc.route.RouteFactory;
import org.apache.commons.collections.CollectionUtils;
import java.util.ArrayList;
import java.util.List;
/**
 * 数据源组配置类 :  一个数据源组配置对象代表一个主从DB架构配置
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2.0.0.Alpha
 */
public class DataSourceGroupConfig extends AbstractDataSourceConfig {


    /**
     * 主库数据源定义
     */
    private DataSourceDefinition masterDataSource;


    /**
     * 从库集合数据源定义
     */
    private List<DataSourceDefinition> slaveDataSources;


    /**
     * 主从库路由策略
     */
    private Route route;


    /**
     * 创建一个数据源组配置类
     * @param masterDataSource 主数据源
     * @param slaveDataSources 从集合数据源
     */
    public DataSourceGroupConfig(DataSourceDefinition masterDataSource,List<DataSourceDefinition> slaveDataSources){
        this(null,masterDataSource,slaveDataSources,null);
    }



    /**
     * 创建一个数据源组配置类
     * @param name 配置名称
     * @param masterDataSource 主数据源
     * @param slaveDataSources 从集合数据源
     * @param route 路由算法
     */
    public DataSourceGroupConfig(String name,DataSourceDefinition masterDataSource,List<DataSourceDefinition> slaveDataSources,Route route){
           super(name);
           this.masterDataSource = masterDataSource;
           this.slaveDataSources = slaveDataSources;
           if(null == route){
               this.route = RouteFactory.getInstance(RouteEnum.SIMPLE.name());
           }else{
               this.route = route;
           }

    }


    @Override
    protected String generatorName() {
        return GROUP_NAME_PREFIX+GROUP_NAME_COUNTER.getAndIncrement();
    }


    public DataSourceDefinition getMasterDataSource() {
        return masterDataSource;
    }

    public void setMasterDataSource(DataSourceDefinition masterDataSource) {
        this.masterDataSource = masterDataSource;
    }

    public List<DataSourceDefinition> getSlaveDataSources() {
        return slaveDataSources;
    }

    public void setSlaveDataSources(List<DataSourceDefinition> slaveDataSources) {
        this.slaveDataSources = slaveDataSources;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public void addSlaveDataSource(DataSourceDefinition slaveDataSource){
        if(CollectionUtils.isEmpty(this.slaveDataSources)){
            this.slaveDataSources = new ArrayList<DataSourceDefinition>();
        }
        this.slaveDataSources.add(slaveDataSource);
    }
}
