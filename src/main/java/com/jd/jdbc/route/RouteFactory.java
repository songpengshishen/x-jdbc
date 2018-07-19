package com.jd.jdbc.route;
import com.jd.jdbc.enums.RouteEnum;
import com.jd.jdbc.exception.XJdbcConfigurationException;
/**
 * 路由算法工厂类,用来获取路由算法实例
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 1.0.0.Alpha
 */
public class RouteFactory {

    /**
     * 工厂获取路由算法类
     * @param routeName 路由算法名称
     * @return 实现了路由算法的子类
     */
    public static Route getInstance(String routeName) {
        Route route =  null;
        switch (RouteEnum.valueOfByCode(routeName)){
            case SIMPLE:route = new SimpleRoute();break;
            case RANDOM:route = new RandomRoute();break;
            case LOCALROOM:route = new LocalRoomRoute();break;
            default: throw new XJdbcConfigurationException("route Configuration Error!routeName : " + routeName);
        }
        return route;
    }
}
