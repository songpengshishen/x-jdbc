package com.jd.jdbc.utils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Spring工具类,提供获取当前spring容器
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 1.0.0.Alpha
 */
@Component("springUtils")
public class SpringUtils implements ApplicationContextAware {
    /**
     * spring当前容器.
     */
    private ApplicationContext ioc;
    /**
     * 获取spring容器时的控制锁.
     */
    private final ReentrantLock Lock = new ReentrantLock();

    /**
     * ReentrantLock条件,进行线程间通信的条件
     */
    private final Condition condition = Lock.newCondition();

    /**
     * 容器是否启动,声明volatile避免指令重排序
     */
    public volatile boolean started = false;

    /**
     * 获取spring容器时,线程等待时间.
     */
    private final long waitTime = 60000;//等待一分钟


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ioc = applicationContext;
        started = true;
        try {
            Lock.lock();
            condition.signal();
        }finally {
            Lock.unlock();
        }

    }



    /**
     * 获取spring容器,可能出现延迟赋值问题,即获取spring容器时,还未执行setApplicationContext方法,applicationContext是空.
     * 此时让当前拿容器的线程睡眠,通过condition的wait,signal实现线程通信,让设置容器的线程唤醒拿容器线程.
     */
    public ApplicationContext getApplicationContext() {
        while(!started){
            try {
                Lock.lock();
                condition.await(waitTime, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                throw new ApplicationContextException("get spring context error!", e);
            }finally {
                Lock.unlock();
            }
        }

        return ioc;
    }


    public <T> T getBean(Class<T> tClass)throws BeansException{
        return getApplicationContext().getBean(tClass);
    }

    public <T> T getBean(String beanId, Class<T> tClass) throws BeansException{
        return getApplicationContext().getBean(beanId,tClass);
    }
}
