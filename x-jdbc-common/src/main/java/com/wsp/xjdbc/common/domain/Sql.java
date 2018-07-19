package com.wsp.xjdbc.common.domain;


import com.wsp.xjdbc.common.enums.SQLType;

/**
 * Sql实体对象
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/07/19
 */
public class Sql implements Cloneable{

    /**
     * Sql本身字符串
     */
    private String sql;

    /**
     * Sql类型
     */
    private SQLType sqlType;


    public Sql(){

    }

    public Sql(String sql, SQLType sqlType){
        this.sql = sql;
        this.sqlType = sqlType;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Sql(sql,sqlType);
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public SQLType getSqlType() {
        return sqlType;
    }

    public void setSqlType(SQLType sqlType) {
        this.sqlType = sqlType;
    }

    @Override
    public String toString() {
        return "Sql{" +
                "sql='" + sql + '\'' +
                ", sqlType=" + sqlType +
                '}';
    }
}
