package com.wsp.xjdbc.config.api;

/**
 * 抽象的配置,包含一个spring id
 * Date : 2018-06-18
 * @author wsp
 * @since 2.0
 */
public class AbstractConfig {

    /**
     * spring id
     */
    protected String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
