package com.jd.jdbc.ds;

import com.jd.jdbc.enums.DataSourceRoleEnum;
import com.jd.jdbc.exception.XJdbcConfigurationException;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 数据源集群配置类,配置了所有的数据源.
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 1.0.0.Alpha
 */
public class DataSourceCluterConfig implements InitializingBean{

   private static final Logger log = LoggerFactory.getLogger(DataSourceCluterConfig.class);

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
                    checkMasterDataSource();
                    checkSlaveDataSource();
                    CopyOnWriteArraySet<DataSourceWrapper> slaveSetCopy = new CopyOnWriteArraySet<DataSourceWrapper>();//初始化从库Set
                    //迭代所有目标数据源,set到各属性中
                    for(DataSourceWrapper dataSource:slaveList){
                        if(DataSourceRoleEnum.valueOf(dataSource.getRole()) == DataSourceRoleEnum.SLAVE){
                            slaveSetCopy.add(dataSource);//添加从库
                        }else if(DataSourceRoleEnum.valueOf(dataSource.getRole()) == DataSourceRoleEnum.MASTER){
                            throw new XJdbcConfigurationException("dataSource definition Error!Slave Contain Master: " + dataSource.getRole(),dataSource.getId());
                        }else{
                            throw new XJdbcConfigurationException("dataSource definition Error!No such Role : " + dataSource.getRole(),dataSource.getId());
                        }
                    }
                    slaveList = new CopyOnWriteArrayList<DataSourceWrapper>(slaveSetCopy);//重新引用赋值
                    initialization = true; //初始化成功
                    if(log.isInfoEnabled()){
                        log.info("DataSourceCluterConfig Init Success!");
                    }
                }
            }
        }
    }


    private void checkMasterDataSource(){
        if(null == this.master){
            throw new XJdbcConfigurationException("master is Require!");
        }
    }

    private void checkSlaveDataSource(){
        if(CollectionUtils.isEmpty(slaveList)){
            throw new IllegalArgumentException("property targetDataSources is not null!");
        }
    }

    public DataSourceWrapper getMaster() {
        return master;
    }

    public void setMaster(DataSourceWrapper master) {
        this.master = master;
    }

    public List<DataSourceWrapper> getSlaveList() {
        return slaveList;
    }

    public void setSlaveList(List<DataSourceWrapper> slaveList) {
        this.slaveList = slaveList;
    }
}
