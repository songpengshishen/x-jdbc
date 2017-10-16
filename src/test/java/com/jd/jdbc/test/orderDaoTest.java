package com.jd.jdbc.test;

import javax.annotation.Resource;


public class orderDaoTest extends BaseTest{

    @Resource
    private orderDao orderDao;

    public void test(){
        orderDao.query();
    }

}
