package com.jd.jdbc.aop;
import com.jd.jdbc.ann.DataSourceSwitch;

import com.jd.jdbc.ds.ReadWriteMultipleDataSource;
import com.jd.jdbc.enums.ReadWriteDataSourceEnum;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import javax.annotation.Resource;

import java.lang.reflect.Method;


/**
 * 数据源切换切面类,代理应用的持久层操作,在操作前通过{@link com.jd.jdbc.ann.DataSourceSwitch}注解设置数据源
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 1.0.0.Alpha
 */
@Aspect
public class DataSourceSwitchAspect {

    @Resource
    private ReadWriteMultipleDataSource dataSource;

    public DataSourceSwitchAspect() {
    }

    @Pointcut("@annotation(com.jd.jdbc.ann.DataSourceSwitch)")
    public void DataSourceSwitch() {
    }

    @Around("DataSourceSwitch()")
    public Object execJAnnotation(ProceedingJoinPoint jp) throws Throwable {
        Method method = this.getMethod(jp);
        Object result = null;
        try {
            //获取执行方法上的注解
            DataSourceSwitch e = method.getAnnotation(DataSourceSwitch.class);
            if (e  == null || e.type().equals(ReadWriteDataSourceEnum.WRITE)) {
                //如果没有配置注解,或者为WRITE 默认数据源为master
                dataSource.setDataSourceBeanId(dataSource.getDataSourceCluterConfig().getMaster().getId());
            }else{
                //否则数据源为slave,我们通过路由从从库中拿一个数据源
                dataSource.setDataSourceBeanId( dataSource.getRoute().route(dataSource.getDataSourceCluterConfig().getSlaveList()));
            }
        } catch (Throwable t) {
            dataSource.clearDataSource();
            throw t;
        }finally {
            dataSource.clearDataSource();
        }
        return result;
    }

    private Method getMethod(JoinPoint jp) throws Exception {
        MethodSignature msig = (MethodSignature) jp.getSignature();
        Method method = msig.getMethod();
        return method;
    }






}
