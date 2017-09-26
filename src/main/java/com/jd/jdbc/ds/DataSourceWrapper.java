package com.jd.jdbc.ds;

/**
 * 数据源包装类,记录了应用所定义的每个数据源的beanID及数据源信息
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 1.0.0.Alpha
 */
public class DataSourceWrapper {

    private String id; /*应用配置的数据源在spring中的beanID*/
    private String deployRoom; /*数据源部署机房*/
    private String role;/*数据源角色 master/slave */
    private int weight = 0;/*数据源权重值,没有选择时默认为0*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
