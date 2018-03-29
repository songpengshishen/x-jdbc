package com.jd.jdbc.core;

import com.jd.jdbc.utils.SystemContants;

import javax.sql.DataSource;


/**
 * 数据源定义类.包含了
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 1.0.0.Alpha
 */
public final class DataSourceDefinition{

    private String name; /*数据源定义名称全局唯一,当通过Spring配置的方式是,该字段为容器的ID*/
    private String deployRoom; /*数据源部署机房*/
    private String role;/*数据源角色 master/slave */
    private int weight = 0;/*数据源权重值,没有选择时默认为0*/
    private DataSource dataSource;


    public DataSourceDefinition(String name,String deployRoom,String role,DataSource dataSource){
       this(name,deployRoom,role, SystemContants.MIDDLE_WEIGHT,dataSource);
    }


    public DataSourceDefinition(String name,String deployRoom,String role,int weight,DataSource dataSource){
           this.name = name;
           this.deployRoom = deployRoom;
           this.role = role;
           this.weight = weight;
           this.dataSource = dataSource;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeployRoom() {
        return deployRoom;
    }

    public void setDeployRoom(String deployRoom) {
        this.deployRoom = deployRoom;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }


    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DataSourceWrapper:[id = ").append(this.name).append(",");
        sb.append("deployRoom = ").append(this.deployRoom).append(",");
        sb.append("role = ").append(this.role).append(",");
        sb.append("weight = ").append(this.weight).append("]");
        sb.append("dataSource = ").append(this.dataSource).append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {return true;}
        if (!(obj instanceof DataSourceDefinition)) {return false;}
        DataSourceDefinition dataSourceDefinition = (DataSourceDefinition) obj;
        if(this.name != dataSourceDefinition.getName())return false;
        return true;
    }

    @Override
    public int hashCode() {
        return 31 * this.name.hashCode();
    }



}
