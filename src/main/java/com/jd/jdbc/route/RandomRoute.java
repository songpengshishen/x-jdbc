package com.jd.jdbc.route;
import com.jd.jdbc.ds.DataSourceWrapper;
import javax.sql.DataSource;
import java.util.List;
import java.util.Random;

/**
 * 数据源权重随机路由算法基类.
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 1.0.0.Alpha
 */
public class RandomRoute extends AbstractRoute implements Route{

    /**
     * 随机数
     */
    private final Random random = new Random();

    /**
     * 根据路由算法获取真正的数据源
     * <tt>这里借用了JSF的权重随机路由算法.</tt>
     * @param dataSourceWrappers 可用的数据源集合
     * @return 数据源
     */
    @Override
    public String doRoute(List<DataSourceWrapper> dataSourceWrappers) {
        DataSourceWrapper dataSourceWrapper = null;//数据源包装类
        final List<DataSourceWrapper> ds = dataSourceWrappers;
        int length = ds.size(); // 数据源总个数
        int totalWeight = 0; // 总权重
        boolean sameWeight = true; //权重是否都一样
        for (int i = 0; i < length; i++) {
            int weight = getWeight(ds.get(i));
            totalWeight += weight; // 累计总权重
            if (sameWeight && i > 0 && weight != getWeight(ds.get(i - 1))) {
                sameWeight = false; // 计算所有权重是否一样
            }
        }
        if (totalWeight > 0 && !sameWeight) {
            // 如果权重不相同且权重大于0则按总权重数随机
            int offset = random.nextInt(totalWeight);
            // 并确定随机值落在哪个片断上,权重越大的数据源包含的随机片段就多,这样权重大的数据源就容易选举出.
            for (int i = 0; i < length; i++) {
                offset -= getWeight(ds.get(i));
                if (offset < 0) {
                    dataSourceWrapper = ds.get(i);
                    break;
                }
            }
        } else {
            // 如果权重相同或权重为0则均等随机
            dataSourceWrapper = ds.get(random.nextInt(length));
        }
        return dataSourceWrapper.getId();
    }

}

