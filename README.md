x-jdbc介绍
===
一: spring集成mybatis面向接口编程
---

    新版后台管理系统使用了mybatis面向接口编程.
    编写Dao层时只需要创建接口,并继承IBaseDao就可以.
    具体mybatis面向接口编程,自行查阅或者看下 http://www.2cto.com/database/201505/398983.html
    因为使用了面向接口,service层引Dao时,直接引接口就可以.
    service层和Controller层的注入注解最好使用@Resource,可以根据名称来注入.
    避免使用@Autowire只会根据类型匹配
    
二: 关于项目目录
---
    项目代码目录分为以下各目录:
    Dao层:数据持久层
    Service:业务逻辑层
    Web层:包括业务控制器,以及拦截器,监听器等.
    Common层:包含了系统使用的各种公用的JAVA Bean比如aop,常量类,异常,枚举,注解以及工具类.
    dto层:数据传输模型,用来向前台传输后台数据.尤其是前台的各种插件.
    model层:数据库映射实体
    其中除了Common层,都分为了
    mars目录:后台代码的主要目录
    tiku目录:题库后台代码的目录
    bang目录:对啊帮后台代码的目录
    因为使用了动态数据源,所以Dao层代码必须按照mars和tiku和bang目录来划分.对mars库进行操作的Dao类写在mars目录,对tiku库进行操作的Dao卸载tiku目录等.
三: 后台shiro权限
---
    权限的基本规则是:菜单:操作:sku实例来表明用户的一次操作的合法性
    其中菜单,操作在创建菜单以及菜单下操作时会填写菜单码和操作码.
    权限需要在后台代码以及前台都做限制.
    后台Controlle中方法使用权限(权限注解只能在控制器中使用):
    下面说下例子,比如我点击员工管理,触发了一个控制器的跳转方法
    在方法执行前需要认证权限可以这样写:
    在方法头部写权限注解:
    @PermissionAnn(menuCode = "user",operCode = PermissionConstant.DEFAULT_OPER,sku= PermissionConstant.DEFAULT_SKUID,msg = "您没有进入员工管理页面的权限!")
    menuCode是菜单码.operCode是操作码,如果是进入菜单操作码默认是view.sku如果没有默认为0.operCode以及sku在没有使用时可以省略.
    operCode和sku支持动态绑定参数,比如控制器中有个方法需要执行修改和增加你的操作码就是动态的,sku也是这样.动态使用的例子
    @PermissionAnn(menuCode = "user",operCode = "#key",sku= "#key",msg = "您没有进入员工管理页面的权限!")
    这时候你的operCode以及sku后面跟着的就是"#你前台传来的参数key".
    前台jsp页面使用权限
    如果在JSP页面使用权限,可以这样做,
    1:先导入<%@include file="/WEB-INF/view/include/taglib.jsp" %>
    
    2:在需要权限判断的操作按钮中添加
      <shiro:hasPermission name="菜单码:操作码:sku">
            xxxxx
      </shiro:hasPermission>
    3:sku下拉框
      <shiro:hasPermission name="菜单码:操作码:sku">
                  xxxxx
     </shiro:hasPermission>  sku为循环时每个sku的值
    
四: 日志管理
---
    后台有很多操作都需要记录日志
    我们在记录日志时可以通过在Controller或Service层中添加日志注解来完成
    在Controller,Service层中的方法上,如果你认为当前方法需要记录日志,可以这么做:
    添加注解@AdminLogAnn(enable= true,operType = OperType.AUTH,operLevel =OperLevel.MAX,operDescribe = "添加角色")
    字段详解:
    enable:当前日志是否启动.true:启动false:不启动 启动会持久化到数据库.可以不填,默认为true.
    operType:操作类型.必填
    operLevel:操作级别.必填
    operDescribe:操作描述.必填,也是最重要的.一定要仔细描述你方法所做的操作.
五: 关于responseJsonModel类
---
    responseJsonModel类用来传送我们后台返回的json数据.无特殊情况大家尽量使用该类返回json.因为要保持前台的良好提示
    其中有字段:
    msg:返回的消息信息
    code:返回的消息吗,现在有200成功,500失败
    Throwable:返回的异常,一般自己不会使用,在异常解析器进行返回.
    result:返回的json数据.
    后台在使用了responseJsonModel类后,前台可以对其返回进行验证.例子:
    $.ajax({
                        ....
                        success: function (d) {
                            if (d.code == HttpUtil.success_code) {
                                //重新加载下用户表格
                                userTable.ajax.reload();
                            }
                            if (d.code == HttpUtil.error_code) {
                                console.info(d.msg);
                                //重新加载下用户表格
                                userTable.ajax.reload();
                            }
                        }
    })
    通过HttpUtil.success_code与HttpUtil.error_code来验证成功失败,通过d.msg来显示后台处理结果
六: 关于ShiroUtils类
---
     ShiroUtils类中有很多方便的静态方法:
     ShiroUtils.isLogin():验证是否登录过,true登录false未登录.
     ShiroUtils.getSessionUser():获取登录后放在session中的用户.
     ShiroUtils.getSession():获取session,这个返回的是shiro的session,其实是shiro托管了Tomcat创建的session.
七: 关于Ajax异步上传
---
    文件异步上传抽出工具类UploadUtils,只需要传入请求和存储目录，返回一个文件对象（包含名字，大小，服务器存储路径，读文件路径等）list
    controller方法调用 UploadUtils.uploadImg方法
    前端使用jquery-file-upload插件，详细见已完成的页面使用方法

八: 关于redis
---
    连接配置在redis.properties
    模板配置，工厂配置在spring-redis.xml
    操作对象类放在common》redis下


    