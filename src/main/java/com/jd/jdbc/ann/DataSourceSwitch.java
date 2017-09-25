package com.jd.jdbc.ann;

import com.jd.jdbc.enums.ReadWriteDataSourceEnum;

import java.lang.annotation.*;

/**
 * 读写分离数据源切换
 * @author wsp
 * @since 2017/09/25
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSourceSwitch {

    ReadWriteDataSourceEnum type() default ReadWriteDataSourceEnum.WRITE; /*数据源的类型默认是写库*/

}
