package com.jd.jdbc.utils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Component;
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
    private static ApplicationContext applicationContext;
    /**
     * 获取spring容器时的控制锁.
     */
    private final static Object Lock = new Object();

    /**
     * 获取spring容器时,线程等待时间.
     */
    private final static long waitTime = 60000;//等待一分钟


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationContext = applicationContext;
        synchronized (Lock) {
            Lock.notifyAll();
        }
    }



    /**
     * 获取spring容器,可能出现延迟赋值问题,即获取spring容器时,还未执行setApplicationContext方法,applicationContext是空.
     * 此时让当前拿容器的线程睡眠,通过内置锁的wait,notifyAll实现线程通信,让设置容器的线程唤醒拿容器线程.
     */
    public static ApplicationContext getApplicationContext() {
        if (null == applicationContext) {
            try {
                synchronized (Lock) {
                    Lock.wait(waitTime);
                }
            } catch (InterruptedException e) {
                throw new ApplicationContextException("get spring context error!", e);
            }
        }
        return applicationContext;
    }


    public static <T> T getBean(Class<T> tClass)throws BeansException{
        return getApplicationContext().getBean(tClass);
    }

    public static <T> T getBean(String beanId, Class<T> tClass) throws BeansException{
        return getApplicationContext().getBean(beanId,tClass);
    }
}
