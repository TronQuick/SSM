# 基于AspectJ的AOP开发

## AspectJ简介

- AspectJ 是一个基于 java 语言得 AOP 框架
- Spring 2.0 以后新增了对 AspectJ 切点表达式支持
- @AspectJ 是 AspectJ 1.5 新增功能，通过 JDK5 注解技术，允许在Bean类中定义切面
- 新版本Spring框架，建议使用AspectJ方式来开发AOP
- 使用 AsepctJ 需要导入 Spring AOP 和 AspectJ 相关jar包 
  - spring-aop-4.2.4.RELEASE.jar
  - com.springsource.org.aopalliance-1.0.0.jar
  - spring-aspects-4.2.4.RELEASE.jar
  - com.springsource.org.aspectj.weaver-1.6.8.RELEASE.jar



## AspectJ 的注解开发AOP

### 环境准备

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:aop="http://www.springframework.org/schema/aop" xsi:schemaLocation="
	http://www.springframework.org/schema/beans	http://www.springframework.org/schema/beans/spring-beans.xsd                             http://www.springframework.org/schema/aop                                        	http://www.springframework.org/schema/aop/spring-aop.xsd"> 
  
  <!--开启AspectJ自动代理-->
  <aop:aspectj-autoproxy />
  
</beans>
```



## AspectJ提供不同的通知类型

- **@Before 前置通知，相当于BeforeAdvice**
- **@AfterReturning 后置通知，相当于AfterReturningAdvice**

- **@Around 环绕通知，相当于MethodIntercepter**
- **@AfterThrowing异常抛出通知，相当于ThrowAdvice**
- **@After 最终final通知，不管是否异常，该同志都会执行**

- @DeclareParents 引介通知，IntroductionInterceptor，不要求



## 切入点表达式的定义

### 在通知中通过 value 属性定义切点

- **通过execution函数，可以定义切点的方式切入**

- **语法 : - execution( <访问修饰符>?<返回类型><方法名>(<参数>)<异常>)**

- 例如：

  - 匹配所有类public方法：execution(public * * (..))

  - 匹配指定包下所有类方法：

    execution(* com.imooc.dao.*(..))**不包含子包**

  - execution(* com.imooc.dao..*(..))           **..\*表示包、子包下所有类**

  - 匹配指定类所有方法：

    execution(* com.imooc.service.UserService.*(..))

  - 匹配实现特定接口所有类方法：

    execution(* com.imooc.dao.GenericDAO+.*(..))

  - 匹配所有save开头的方法 exection(* save*(..))