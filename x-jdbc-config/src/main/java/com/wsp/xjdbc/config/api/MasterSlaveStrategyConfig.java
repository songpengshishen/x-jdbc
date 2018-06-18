package com.wsp.xjdbc.config.api;

import com.wsp.xjdbc.common.enums.RouteEnum;

/**
 * master\slave的数据源策略配置
 * Date : 2018-06-17
 * @author wsp
 * @since 2.0
 */
public class MasterSlaveStrategyConfig extends AbstractConfig{


    public MasterSlaveStrategyConfig() {
    }

    public MasterSlaveStrategyConfig(String route) {
        this.route = route;
    }

    /**
     * slave库路由策略算法
     */
    private String route = RouteEnum.RANDOM.name();

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    @Override
    public int hashCode() {
        int code = 0;
        code = 31 * code + (null == route ? 0 : route.hashCode());
        return code;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        if(obj instanceof MasterSlaveStrategyConfig){
            final MasterSlaveStrategyConfig strategyConfig = (MasterSlaveStrategyConfig)obj;
            if(strategyConfig.getRoute().equals(this.getRoute())){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "MasterSlaveStrategyConfig{" +
                "route='" + route + '\'' +
                '}';
    }
}
