package com.wsp.xjdbc.core;

/**
 * 基于ThreadLocal进行线程内隐式传参
 * 每一个XjdbcContext对象都存放在当先线程的ThreadLocal中
 * @author wsp
 * @date 2018/07/20
 */
public final class XJdbcContext {

    private static final ThreadLocal<XJdbcContext> LOCAL = new ThreadLocal<XJdbcContext>(){
        @Override
        protected XJdbcContext initialValue() {
            return new XJdbcContext();
        }
    };

    private XJdbcContext(){}


    private Boolean onlyMasterFlag;


    /**
     * 获取xjdbcContext
     * @return context context
     */
    public static XJdbcContext getContext() {
        return LOCAL.get();
    }

    /**
     * 清除xjdbcContext
     * remove context.
     */
    public static void removeContext() {
        LOCAL.remove();
    }


    public XJdbcContext setOnlyMasterFlag(Boolean onlyMasterFlag) {
        this.onlyMasterFlag = onlyMasterFlag;
        return this;
    }

    public Boolean getOnlyMasterFlag() {
        return onlyMasterFlag;
    }
}
