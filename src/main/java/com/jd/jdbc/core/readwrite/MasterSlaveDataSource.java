package com.jd.jdbc.core.readwrite;
import javax.sql.DataSource;
import java.util.List;

/**
 * Title :主从数据源接口
 * Description: 扩展 JDBC {@link DataSource}接口</br>
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/03/28
 */
public interface MasterSlaveDataSource extends DataSource {

    /**
     * 获取所有的目标数据源
     * @return
     */
    List<DataSource> getAllTargetDataSource();

    /**
     * 决定真实的数据源
     * @return
     */
    DataSource determineRealDataSource(String sql);
}