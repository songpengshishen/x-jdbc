package com.wsp.xjdbc.config.bean;

import com.wsp.xjdbc.config.api.SlaveDataSourceConfig;
import org.springframework.beans.factory.BeanNameAware;

/**
 * slave数据源配置SpringBean
 * Date : 2018-06-19
 * @author wsp
 * @since 2.0
 */
public class SlaveDataSourceConfigBean extends SlaveDataSourceConfig implements BeanNameAware {

    private String targetDataSourceRef;

    private String beanName;

    public String getTargetDataSourceRef() {
        return targetDataSourceRef;
    }

    public void setTargetDataSourceRef(String targetDataSourceRef) {
        this.targetDataSourceRef = targetDataSourceRef;
    }

    public String getBeanName() {
        return beanName;
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
}
