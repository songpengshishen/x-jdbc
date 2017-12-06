package com.jd.jdbc.enums;


/**
 * 数据源传播性枚举类
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 1.1.0.Alpha 2017/12/06
 */
public enum Propagation {
    REQUIRED(0), /*如果当前没有数据源,就根据注解配置的数据源新建一个数据源，如果已经存在一个数据源,则使用这个数据源*/
    REQUIRES_MASTER(1)/*永远传播主库数据源*/
    ;

    Propagation(int value) {
        this.value = value;
    }

    private final int value;

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Propagation{" +
                "value=" + value +
                '}';
    }
}
