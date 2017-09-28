package com.jd.jdbc.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-xjdbc-config.xml"})
public class BaseTest extends AbstractJUnit4SpringContextTests {

    @Before
    public void setUp() {

    }

    @Test
    public void test() {
        Assert.assertTrue(true);
    }
}