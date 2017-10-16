package com.jd.jdbc.test;

import com.jd.jdbc.ann.DataSourceSwitch;
import com.jd.jdbc.enums.ReadWriteDataSourceEnum;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Repository
public class orderDao {
    @Resource
    private SqlMapClientTemplate SqlMapClientTemplate;

    @DataSourceSwitch(type = ReadWriteDataSourceEnum.WRITE)
    public void insert(){
        SqlMapClientTemplate.insert("order.insert",newOrder());
    }

    @DataSourceSwitch(type = ReadWriteDataSourceEnum.READ)
    public void query(){
       List<Order> orderList =  SqlMapClientTemplate.queryForList("order.select");
       System.out.println(orderList);
    }

    public Order newOrder(){
        Order o1 = new Order();
        o1.setId(Long.valueOf(23l));
        o1.setOrderId(11L);
        o1.setUserId(12l);
        o1.setPrice(100d);
        o1.setCreateDate(new Date());
        return o1;
    }
}
