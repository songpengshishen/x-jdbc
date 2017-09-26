package com.jd.jdbc.ann;

import com.jd.jdbc.enums.ReadWriteDataSourceEnum;

import java.lang.annotation.*;

/**
 * 读写分离数据源切换
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 1.0.0.Alpha
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSourceSwitch {

    ReadWriteDataSourceEnum type() default ReadWriteDataSourceEnum.WRITE; /*数据源的类型默认是写库*/

}
