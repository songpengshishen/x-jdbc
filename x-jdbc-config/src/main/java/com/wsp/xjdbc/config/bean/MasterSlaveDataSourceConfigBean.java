package com.wsp.xjdbc.config.bean;

import com.wsp.xjdbc.common.utils.SpringBeanFactoryHolder;
import com.wsp.xjdbc.config.MasterSlaveDataSourceFactory;
import com.wsp.xjdbc.config.StandardMasterSlaveDataSourceFactory;
import com.wsp.xjdbc.config.api.MasterSlaveStrategyConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.sql.DataSource;
import java.util.Set;

/**
 * masterSlave数据源配置SpringBean
 * Date : 2018-06-18
 *
 * @author wsp
 * @since 2.0
 */
public class MasterSlaveDataSourceConfigBean implements FactoryBean<DataSource>,
        ApplicationContextAware, InitializingBean, ApplicationListener, BeanNameAware {

    private String beanName;

    /**
     * 数据源创建工厂实例,可以在spring配置文件中配置
     */
    private MasterSlaveDataSourceFactory dataSourceFactory;

    /**
     * 数据源创建工厂实例,可以在spring配置文件中配置Ref
     */
    private String dataSourceFactoryRef;

    /**
     * master数据源配置
     */
    private MasterDataSourceConfigBean master;

    /**
     * slave数据源配置
     */
    private Set<SlaveDataSourceConfigBean> slaves;

    /**
     * master\slave数据源策略配置
     */
    private MasterSlaveStrategyConfig strategyConfig;


    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public DataSource getObject() throws Exception {
        return dataSourceFactory.getDataSource(master, slaves, strategyConfig);
    }

    @Override
    public Class<?> getObjectType() {
        return DataSource.class;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBeanFactoryHolder.setApplicationContext(applicationContext);
    }

    /**
     * 在spring加载完毕后
     *
     * @param applicationEvent
     */
    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (applicationEvent instanceof ContextRefreshedEvent) {
            System.out.println("spring加载完毕");
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //设置目标DataSource
        ApplicationContext applicationContext = SpringBeanFactoryHolder.getApplicationContext();
        if (null == applicationContext) {
            throw new IllegalStateException("The Ioc factory is empty!!!");
        }
        master.setTargetDataSource(applicationContext.getBean(master.getTargetDataSourceRef(), DataSource.class));
        for (SlaveDataSourceConfigBean sourceConfigBean : slaves) {
            sourceConfigBean.setTargetDataSource(applicationContext.getBean(sourceConfigBean.getTargetDataSourceRef(), DataSource.class));
        }
        if (null == dataSourceFactoryRef || dataSourceFactoryRef.isEmpty()) {
            dataSourceFactory = new StandardMasterSlaveDataSourceFactory();
        } else {
            dataSourceFactory = applicationContext.getBean(dataSourceFactoryRef, MasterSlaveDataSourceFactory.class);
        }
        if (null == dataSourceFactory) {
            throw new IllegalStateException("Unable to find a configured MasterSlaveDataSourcefactory");
        }
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public MasterSlaveDataSourceFactory getDataSourceFactory() {
        return dataSourceFactory;
    }

    public void setDataSourceFactory(MasterSlaveDataSourceFactory dataSourceFactory) {
        this.dataSourceFactory = dataSourceFactory;
    }

    public String getDataSourceFactoryRef() {
        return dataSourceFactoryRef;
    }

    public void setDataSourceFactoryRef(String dataSourceFactoryRef) {
        this.dataSourceFactoryRef = dataSourceFactoryRef;
    }

    public MasterDataSourceConfigBean getMaster() {
        return master;
    }

    public void setMaster(MasterDataSourceConfigBean master) {
        this.master = master;
    }

    public Set<SlaveDataSourceConfigBean> getSlaves() {
        return slaves;
    }

    public void setSlaves(Set<SlaveDataSourceConfigBean> slaves) {
        this.slaves = slaves;
    }

    public MasterSlaveStrategyConfig getStrategyConfig() {
        return strategyConfig;
    }

    public void setStrategyConfig(MasterSlaveStrategyConfig strategyConfig) {
        this.strategyConfig = strategyConfig;
    }

    @Override
    public String toString() {
        return "MasterSlaveDataSourceConfigBean{" +
                "dataSourceFactory=" + dataSourceFactory +
                ", dataSourceFactoryRef='" + dataSourceFactoryRef + '\'' +
                ", master=" + master +
                ", slaves=" + slaves +
                ", strategyConfig=" + strategyConfig +
                '}';
    }


}
