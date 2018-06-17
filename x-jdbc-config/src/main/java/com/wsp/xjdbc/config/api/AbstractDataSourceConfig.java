package com.wsp.xjdbc.config.api;

import javax.sql.DataSource;

/**
 * 抽象的数据源配置
 * Date : 2018-06-17
 * @author wsp
 * @since 2.0
 */
public abstract class AbstractDataSourceConfig {

    /**
     * DataSource 名称
     */
    protected String name;

    /**
     * 数据库所在机房区域
     */
    protected String roomArea;

    /**
     * 实际的数据源,实现了JDBC规范的DataSource都可以
     */
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
        if(obj == this){
            return true;
        }
        if(obj instanceof AbstractDataSourceConfig){
           final AbstractDataSourceConfig dataSourceConfig = (AbstractDataSourceConfig)obj;
           if(this.getName().equals(dataSourceConfig.getName()) &&
                   this.getRoomArea().equals(dataSourceConfig.getRoomArea())){
                   if(this.getTargetDataSource().equals(dataSourceConfig.getTargetDataSource())){
                       return true;
                   }
           }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int code = 0;
        code = 31 * code + (null != name ? name.hashCode() : 0);
        code = 31 * code + (null != roomArea ? roomArea.hashCode() : 0);
        code = 31 * code + (null != targetDataSource ? targetDataSource.hashCode():0);
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
