x-jdbc介绍
===
一: x-jdbc
---
  一个jdbc的小型框架,主要是为了实现读写分离,读多个从库(线性提升读性能)并提供了3种路由策略算法,默认的读写分离,权重值的随机算法,同机房调用.读库的高可用(一期未实现).
  x-jdbc的想法:当应用想使用读写分离功能时,可以很方便的接入,因为x-jdbc就是一个jar包,并扩展了Spring的自定义标签方便应用通过xjdbc:cluter,xjdbc:rwds来定义
              数据源,并将x-jdbc的数据源配置在你应用的ORM框架中.
二: 关于x
---
   之所以起名x是因为这个小框架我写的时候就是为了玩(提升下自己技术).对就是这么随意.





    