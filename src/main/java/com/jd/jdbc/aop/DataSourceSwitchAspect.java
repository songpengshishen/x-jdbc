package com.jd.jdbc.aop;
import com.jd.jdbc.ann.DataSourceSwitch;

import com.jd.jdbc.ds.DataSourceWrapper;
import com.jd.jdbc.ds.ReadWriteMultipleDataSource;
import com.jd.jdbc.enums.ReadWriteDataSourceEnum;

import com.jd.jdbc.spring.RwdsDefinitionParser;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

import java.lang.reflect.Method;
import java.util.List;


/**
 * 数据源切换切面类,代理应用的持久层操作,在操作前通过{@link com.jd.jdbc.ann.DataSourceSwitch}注解设置数据源
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 1.0.0.Alpha
 */
@Component
@Aspect
public class DataSourceSwitchAspect implements InitializingBean{

    private static final Logger log = LoggerFactory.getLogger(DataSourceSwitchAspect.class);

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
            if(!dataSource.isOnlyMaster()){
                if (e  == null || e.type().equals(ReadWriteDataSourceEnum.WRITE)) {
                    //如果注解为空,或者为WRITE 默认数据源为master
                    dataSource.setDataSourceBeanId(dataSource.getDataSourceCluterConfig().getMaster().getId());
                    dataSource.setMasterRouteOnly(Boolean.TRUE);
                }else{
                    //否则数据源为slave,我们通过路由算法选举出从库中的某一个数据源
                    String dsBeanId = dataSource.getRoute().route(dataSource.getDataSourceCluterConfig().getSlaveList());
                    //如果从库中没有获取到,则设置默认的主库
                    if(null == dsBeanId || dsBeanId.equals("")){
                        if(log.isInfoEnabled()){
                            log.info("Slave is Empty,Switch Master!");
                        }
                        dsBeanId = dataSource.getDataSourceCluterConfig().getMaster().getId();
                    }
                    dataSource.setDataSourceBeanId(dsBeanId);
                }
            }
            result = jp.proceed();
        } catch (Throwable t) {
            throw t;
        }
        return result;
    }

    private Method getMethod(JoinPoint jp) throws Exception {
        MethodSignature msig = (MethodSignature) jp.getSignature();
        Method method = msig.getMethod();
        return method;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        if(log.isInfoEnabled()){
            log.info("DataSourceSwitchAspect Init Success!dataSource is:"+dataSource);
        }
    }
}
