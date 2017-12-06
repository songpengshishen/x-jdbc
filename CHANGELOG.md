Change Log of X-JDBC Client
====
The current stable version : **[1.0.0.Alpha-SNAPSHOT]**

###x-jdbc-1.1.0.Alpha-SNAPSHOT (2017-12-06)
- 新增功能:
    - 添加数据源的传播性,可以在方法嵌套调用时传播数据源,解决方法嵌套调用时数据源乱了,以及数据源不够灵活问题。原因:每个方法执行时,都使用自己注解的数据源,这样嵌套调用时无法保证只使用一个数据源
      ,只能在外置大方法加数据源,内在小方法不加数据源,但是这样不够灵活.

###x-jdbc-1.0.0.Alpha-SNAPSHOT (2017-09-25)
- 原始版本。