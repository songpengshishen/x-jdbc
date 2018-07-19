package com.wsp.xjdbc.config.api;

import com.wsp.xjdbc.common.ann.DataSourceField;

import javax.sql.DataSource;

/**
 * 抽象的数据源配置
 * Date : 2018-06-17
 *
 * @author wsp
 * @since 2.0
 */
public abstract class AbstractDataSourceConfig extends AbstractConfig {

    /**
     * DataSource 名称
     * 必填项
     */
    @DataSourceField(required = true)
    protected String name;

    /**
     * 数据库所在机房区域
     * 选填项
     */
    @DataSourceField(required = false)
    protected String roomArea;

    /**
     * 实际的数据源,实现了JDBC规范的DataSource都可以
     * 必填项
     */
    @DataSourceField(required = true)
    protected DataSource targetDataSource;

    protected AbstractDataSourceConfig() {

    }

    protected AbstractDataSourceConfig(String name, String roomArea, DataSource targetDataSource) {
        this.name = name;
        this.roomArea = roomArea;
        this.targetDataSource = targetDataSource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DataSource getTargetDataSource() {
        return targetDataSource;
    }

    public void setTargetDataSource(DataSource targetDataSource) {
        this.targetDataSource = targetDataSource;
    }

    public String getRoomArea() {
        return roomArea;
    }

    public void setRoomArea(String roomArea) {
        this.roomArea = roomArea;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof AbstractDataSourceConfig) {
            final AbstractDataSourceConfig dataSourceConfig = (AbstractDataSourceConfig) obj;
            if (null!=this.getName()?!this.getName().equals(dataSourceConfig.getName()):null!=dataSourceConfig.getName()) return false;
            if (null!=this.getRoomArea()?!this.getRoomArea().equals(dataSourceConfig.getRoomArea()):null!=dataSourceConfig.getRoomArea()) return false;
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
        code = 31 * code + (null != targetDataSource ? targetDataSource.hashCode() : 0);
        return code;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getCanonicalName());
        sb.append(" : ")
                .append(" { ")
                .append(" name =  ").append(this.name).append(",")
                .append(" targetDataSource =  ").append(this.targetDataSource).append(",")
                .append(" roomArea =  ").append(this.roomArea).append(",")
                .append(" }");
        return sb.toString();
    }


}
