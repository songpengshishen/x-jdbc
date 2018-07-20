package com.wsp.xjdbc.core;

import javax.sql.DataSource;
import java.util.List;

/**
 * Title :主从数据源接口
 * Description: 扩展 JDBC {@link DataSource}接口</br>
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/07/19
 */
public interface MasterSlaveDataSource extends DataSource {

    /**
     * 获取master数据源
     * @return
     */
    DataSource getMasterDataSource();


    /**
     * 获取从数据源
     * @return
     */
    List<DataSource> getSlaveTargetDataSources();




    /**
     * 通过Sql决策真实的数据源
     * @return
     */
    DataSource determineRealDataSource(String sql);
}
