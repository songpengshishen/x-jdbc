package com.jd.jdbc.ds;

import com.jd.jdbc.enums.DataSourceRoleEnum;
import com.jd.jdbc.exception.XJdbcConfigurationException;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 数据源集群配置类,配置了所有的数据源.
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 1.0.0.Alpha
 */
public class DataSourceCluterConfig implements InitializingBean{

   private static final Logger log = LoggerFactory.getLogger(DataSourceCluterConfig.class);

    /**
     * 提供给应用用户配置的目标数据源集合
     */
    private List<DataSourceWrapper> targetDataSources;

    /**
     * 默认的目标数据源
     */
    private DataSourceWrapper defaultTargetDataSource;

    /**
     * 主库的数据源集合
     */
    private DataSourceWrapper master;

    /**
     * 从库的数据源集合
     */
    private List<DataSourceWrapper> slaveList;

    /**
     * 数据源集群是否初始化成功,声明volatile避免指令重排序
     */
    public volatile static boolean initialization = false;


    /**
     * 初始化锁对象
     */
    private final Object lock = new Object();



    /**
     * 在类初始化后执行,将数据源 {@code targetDataSources} 设置到对应属性中
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if(!initialization){
            synchronized (lock){
                if(!initialization){
                    if(CollectionUtils.isEmpty(targetDataSources)){
                        throw new IllegalArgumentException("property targetDataSources is not null!");
                    }
                    this.slaveList = new CopyOnWriteArrayList<DataSourceWrapper>();//初始化从库list
                    //迭代所有目标数据源,set到各属性中
                    for(DataSourceWrapper dataSource:targetDataSources){
                        if(DataSourceRoleEnum.valueOf(dataSource.getRole()) == DataSourceRoleEnum.MASTER){
                            //只可以有一个主库数据源
                            if(null != this.master){
                                throw new XJdbcConfigurationException("master definition Repeat!",dataSource.getId());
                            }
                            if(null == this.master){
                                this.master = dataSource; //设置master
                            }
                        }else if(DataSourceRoleEnum.valueOf(dataSource.getRole()) == DataSourceRoleEnum.SLAVE){
                            slaveList.add(dataSource);//添加从库
                        }else{
                            throw new XJdbcConfigurationException("dataSource definition Error!No such Role : " + dataSource.getRole(),dataSource.getId());
                        }
                    }

                    if(null == this.master){
                        throw new XJdbcConfigurationException("master is Require!");
                    }
                    if(defaultTargetDataSource == null){
                        //没有设置默认数据源时,将主库数据源设置为默认数据源
                        defaultTargetDataSource = this.master;
                    }
                    initialization = true; //初始化成功
                    log.error("DataSourceCluterConfig Init Success!");
                }
            }
        }
    }

    public List<DataSourceWrapper> getTargetDataSources() {
        return targetDataSources;
    }

    public void setTargetDataSources(List<DataSourceWrapper> targetDataSources) {
        this.targetDataSources = targetDataSources;
    }

    public DataSourceWrapper getDefaultTargetDataSource() {
        return defaultTargetDataSource;
    }

    public void setDefaultTargetDataSource(DataSourceWrapper defaultTargetDataSource) {
        this.defaultTargetDataSource = defaultTargetDataSource;
    }

    public DataSourceWrapper getMaster() {
        return master;
    }

    public List<DataSourceWrapper> getSlaveList() {
        return slaveList;
    }


}
