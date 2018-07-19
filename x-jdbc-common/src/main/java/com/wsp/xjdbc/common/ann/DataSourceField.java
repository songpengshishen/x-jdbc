package com.wsp.xjdbc.common.ann;

/**
 * 数据源字段注解
 * Date : 2018-06-18
 * @author wsp
 * @since 2.0
 */
public @interface DataSourceField {

    boolean required() default false;
}
