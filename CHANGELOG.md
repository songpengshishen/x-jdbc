Change Log of X-JDBC Client
====
The current stable version : **[1.0.0.Alpha-SNAPSHOT]**

###x-jdbc-2.0.0.Alpha-SNAPSHOT (2018-01-17)
- 整体代码架构变更:
    - 去除注解方式来路由主库或从库,改变为解析执行sql来路由主库或从库.
    - 重写所有jdbc规范接口(DataSource,Connection,Statement,ResultSet),使其更灵活的解析sql,进行路由,合并结果以及增强其他功能,为分库分表做准备.
    - 借鉴JSF关于配置类的设计模式,提供程序编码方式,Spring配置方式来接入
###x-jdbc-1.1.0.Alpha-SNAPSHOT (2018-01-15)
- 新增功能:
    - 同个线程出现写后,后续读操作均从主库读取保证数据一致性
###x-jdbc-1.0.0.Alpha-SNAPSHOT (2017-09-25)
- 原始版本。