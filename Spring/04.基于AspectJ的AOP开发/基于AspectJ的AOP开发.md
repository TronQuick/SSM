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

- 例：

```java
//切面类：

@Aspect
public class MyAspectAnno {

    @Before(value = "execution(* com.imooc.aspectJ.demo1.ProductDao.*(..))")
    public void before() {
        System.out.println("前置通知==================" );
    }

}
```



## @Before前置通知

- **可以在方法中传入JoinPoint对象，用来获得切点信息**

```java
//切面类：获取切点信息JoinPoint，并打印
@Before(value = "execution(* com.imooc.aspectJ.demo1.ProductDao.*(..))")
    public void before(JoinPoint joinPoint) {
        System.out.println("前置通知=================="+joinPoint);
}
```



## @AfterReturning 后置通知

- **通过returning属性，可以定义方法返回值，作为参数：**

  (因为可能是任意类型返回值，所以用Object类接收)

```java
//后置通知类型:获取目标方法中的返回值，命名其为result，并打印
@AfterReturning(value = "execution(* com.imooc.aspectJ.demo1.ProductDao.*(..))",returning = "result")
public void afterReturning(Object result) {
    System.out.println("后置通知========="+result);
}
```



## @Around 环绕通知

- **around方法的返回值就是目标代理方法执行返回值**
- **参数为ProceedingJoinPoint可以调用拦截目标方法执行**

- **如果不调用ProceedingJoinPoint的proceed()方法，目标方法被拦截（不执行）**

- 例：

  ```java
  //环绕通知类：
  @Around(value = "execution(* com.imooc.aspectJ.demo1.ProductDao.delete(..))")
  public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
      System.out.println("环绕前通知====");
  
      //如果不调用ProceedingJoinPoint的proceed()方法，目标方法被拦截（不执行）
      Object object= joinPoint.proceed();//执行目标方法
  
      System.out.println("环绕后通知====");
      return object;
  }
  ```

  

## @AfterThrowing 异常抛出通知

- **通过设置throwing属性，可以设置发生异常对象参数**

- 只有发生异常的时候才会被执行

- **可以通过属性throwing，返回异常信息Throwable类型值，接收异常信息**

  ```java
  //接收并打印异常信息
  @AfterThrowing(value = "execution(* com.imooc.aspectJ.demo1.ProductDao.findOne(..))",throwing = "e")
  public void afterThrowing(Throwable e) {
      System.out.println("异常抛出通知===="+e.getMessage());
  }
  ```



## @After 最终通知

- 无论是否出现异常，最终通知总是会被执行的

```java
@After(value = "execution(* com.imooc.aspectJ.demo1.ProductDao.findAll(..))")
public void after() {
    System.out.println("最终通知===");
}
```



## 通过 @Pointcut 为切点命名

- **在每个通知内定义切点，会造成工作量大，不易维护，对于重复的切点，可以使用@Pointcut进行定义**
- **切点方法：private void 无参数方法，方法名为切点名**
- **当通知多个切点时，可以使用||进行连接**

例：

```java
//定义切点
@Pointcut(value = "execution(* com.imooc.aspectJ.demo1.ProductDao.save(..))")
public void myPointcut1() {}

//使用切点进行前置增强
@Before(value = "myPointcut1()")
public void before(JoinPoint joinPoint) {
    System.out.println("前置通知=================="+joinPoint);
}

```

