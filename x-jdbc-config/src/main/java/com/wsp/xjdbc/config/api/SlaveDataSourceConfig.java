package com.wsp.xjdbc.config.api;

import com.wsp.xjdbc.common.ann.DataSourceField;
import com.wsp.xjdbc.common.constant.SystemContants;

import javax.sql.DataSource;

/**
 * slave的数据源配置
 * Date : 2018-06-17
 * @author wsp
 * @since 2.0
 */
public class SlaveDataSourceConfig extends AbstractDataSourceConfig {

    public SlaveDataSourceConfig() {
        super();
    }

    public SlaveDataSourceConfig(String name, String roomArea, DataSource targetDataSource, int weight,boolean enable) {
        super(name, roomArea, targetDataSource);
        this.weight = weight;
        this.enable = enable;
    }

    /**
     * 权重值
     */
    @DataSourceField(required = false)
    protected int weight = SystemContants.MIN_WEIGHT;

    /**
     * 是否启用该从库
     */
    @DataSourceField(required = false)
    protected boolean enable;


    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof SlaveDataSourceConfig) {
            final SlaveDataSourceConfig dataSourceConfig = (SlaveDataSourceConfig) obj;
            if (null!=this.getName()?!this.getName().equals(dataSourceConfig.getName()):null!=dataSourceConfig.getName()) return false;
            if (null!=this.getRoomArea()?!this.getRoomArea().equals(dataSourceConfig.getRoomArea()):null!=dataSourceConfig.getRoomArea()) return false;
            if (this.getWeight() != dataSourceConfig.getWeight()) return false;
            if (this.isEnable() != dataSourceConfig.isEnable()) return false;
            if (this.getTargetDataSource()!=dataSourceConfig.getTargetDataSource()) return false;
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int code = 0;
        code = 31 * code + (null != name ? name.hashCode() : 0);
        code = 31 * code + (null != roomArea ? roomArea.hashCode() : 0);
        code = 31 * code + weight;
        code = 31 * code + (enable?1:0);
        code = 31 * code + (null != targetDataSource ? targetDataSource.hashCode() : 0);
        return code;
    }

}
