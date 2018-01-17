package com.jd.jdbc.config;

import org.apache.commons.lang.StringUtils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 抽象的数据源配置类
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2.0.0.Alpha
 */
public abstract class AbstractDataSourceConfig implements DataSourceConfig{

    /**
     * 数据源配置名称
     */
    protected String name;

    /**
     * 默认数据源组配置的名字前缀
     */
    protected static final String GROUP_NAME_PREFIX = "dsConfig-group-";

    /**
     * 默认的数据源分片配置的名字前缀
     */
    protected static final String SHARDING_NAME_PREFIX = "dsConfig-sharding-";

    /**
     * 默认数据源组配置的名字计数器
     */
    protected static final AtomicInteger GROUP_NAME_COUNTER = new AtomicInteger(0);

    /**
     * 默认的数据源分片配置的名字计数器
     */
    protected static final AtomicInteger SHARDING_NAME_COUNTER = new AtomicInteger(0);

    public AbstractDataSourceConfig(String name){
        if(StringUtils.isBlank(name)){
            this.name = generatorName();
        }else{
            this.name = name;
        }
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    protected abstract String generatorName();


}
