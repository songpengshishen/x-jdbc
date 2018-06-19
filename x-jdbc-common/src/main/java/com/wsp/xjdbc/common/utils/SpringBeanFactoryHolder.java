package com.wsp.xjdbc.common.utils;

import org.springframework.context.ApplicationContext;

/**
 * Spring的bean工厂持有者
 * Date : 2018-06-19
 * @author wsp
 * @since 2.0
 */
public class SpringBeanFactoryHolder {

    private SpringBeanFactoryHolder(){};

    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringBeanFactoryHolder.applicationContext = applicationContext;
    }
}
