# README

## 项目结构

**三层架构**

- 持久层：mybatis
- 表现层：Servlect + JSP

- Spring：管理对象、切面处理



**基于MVC模式**

- 视图：JSP
- 模型：JavaBean
- 控制器：Servlet+JavaBean



## 配置与注解

**配置**

- 非java代码
- 便于后期维护

**注解**

- 灵活、精简



**应用场合**

- 一处配置与多处注解
- 代码精简
- 注解需要复杂的非java代码



## 创建项目

- sm
  - 父moudle
  - 全局定义与组织
- sm_service
  - 持久层、业务层
  - Mybatis依赖、Spring依赖
- sm_web
  - 表现层
  - Servlet依赖



## Spring全局配置

- Spring整合Mybatis
- 声明式事务

- 全局扫描

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans.xsd
    http://www.springframework.org/schema/context
    http://www.springframework.org/schema/context/spring-context.xsd
    http://www.springframework.org/schema/aop
    http://www.springframework.org/schema/aop/spring-aop.xsd
    http://www.springframework.org/schema/tx
    http://www.springframework.org/schema/tx/spring-tx.xsd">
    
    <!--Spring整合Mybatis-->
    <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql://localhost:3306/sm?useUnicode=true&amp;characterEncoding=utf-8"/>
        <property name="username" value="root"/>
        <property name="password" value="root"/>
    </bean>
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <property name="typeAliasesPackage" value="com.imooc.sm.entity"/>
    </bean>
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="com.imooc.sm.dao"/>
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>
    </bean>

    <!--声明式事务-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>
    <tx:advice id="txAdvice" transaction-manager="transactionManager">
        <tx:attributes>
            <tx:method name="get*" read-only="true"/>
            <tx:method name="find*" read-only="true"/>
            <tx:method name="search*" read-only="true"/>
            <tx:method name="*" propagation="REQUIRED"/>
        </tx:attributes>
    </tx:advice>
    <aop:config>
    	<!--业务层所有的方法作为切点-->
        <aop:pointcut id="txPointcut" expression="execution(* com.imooc.sm.service.*.*(..))"/>
        <aop:advisor advice-ref="txAdvice" pointcut-ref="txPointcut"/>
    </aop:config>
    

    <!-- 全局扫描 -->
    <!--注解扫描-->
    <context:component-scan base-package="com.imooc.sm"/>
    <!--AOP处理日志-->
    <aop:aspectj-autoproxy/>
</beans>
```



## 工具类

- 编码过滤器
- 核心控制器
  - Servlet对象由Web容器管理，Service由IOC容器管理