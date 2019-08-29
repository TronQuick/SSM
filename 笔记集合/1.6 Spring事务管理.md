# Spring事务管理



## 事务简介

- **什么是事务？**

  - 事务是**正确执行**一系列的操作（或动作），使得数据库从一种状态转换成另一种状态，且保证操作**全部成功**，或者**全部失败**。

- **事务原则是什么**

  - 事务必须服从 ISO/IEC 所制定的 **ACID 原则**。

  

### ACID原则

- **原子性（ Atomicity ）**
  - 即不可分割性，事务要么全部被执行，要么就全部不被执行。
- **一致性（  Consistency ）**
  - 事务的执行使得数据库从一种正确状态转换成另一种正确状态。
- **隔离性（  Isolation** ）
  - 在事务正确提交之前，它可能的结果不应显示给其它任何事务
- **持久性（  Durability ）**
  - 事务正确提交后，其结果将永远保存在数据库中



### Java事务

- **Java事务的产生**
  - 程序操作数据库的需要。在Java编写的程序或系统中，实现ACID的操作
- **Java事务实现范围**
  - 通过JDBC相应方法间接来实现对数据库的增删改查，把事务转移到Java程序代码中进行控制

**总结：Java事务机制和原理就是确保数据库操作的ACID特性**



### Java事务实现模式

- Java事务的实现
  - 通过Java代码来实现对数据库的事务性操作。
- Java事务类型
  - JDBC事务：用 Connection 对象控制，包括手动模式和自动模式
  - JTA(Java Transaction API)事务：**与现实无关的，与协议无关的API**
  - 容器事务：应用服务器提供的，且大多是基于JTA完成（一般基于JNDI的，相当复杂的API实现）
- 三种事务的差异
  - JDBC事务：控制的局限性在一个数据库连接里，但是其使用简单
  - JTA事务：功能强大，可跨越多个数据库或多DAO，使用比较复杂
  - 容器事务：主要指的是J2EE应用服务器提供的事务管理，局限于EJB应用使用



## Spring事务核心接口

- **PlatformTransactionManager:平台事务管理器接口**
  - getTransaction() 
    - 获取事务
  - commit()
    - 提交
  - rollback()
    - 回滚



- **TransactionDefinition:事务定义接口（传参到事务管理器）**

- **TransactionStatus:事务状态接口（通过事务管理器获取TransactionStatus实例**）



### Spring事务管理器

- JDBC事务管理器
  - 通过掉用java.sql.Connection来管理事务
- Hibernate事务管理器
- JPA事务管理器
- JTA事务管理器

### Spring事务属性定义

#### 属性定义接口

- **int  getPropagationBehavior();**
  - 返回事务的传播行为
- **int  getIsolationLevel();**
  - 返回事务的隔离级别，事务管理器根据他来控制另一个事务可以看到本十五内的哪些数据
- **int  getTimeout();**
  - 返回事务必须在多少秒内完成
- **boolean  isReadOnly();**
  - 判断事务是否只读，事务管理器能够根据这个返回值进行优化，确保事务是只读的



#### 数据读取类型

- 脏读
  - 事务没提交，提前读取
- 不可重复读
  - 两次读取的数据不一致
- 幻读
  - 事务不是独立执行时发生的一种非预期现象



#### 事务隔离级别

隔离级别定义了一个事务可能受其他并发事务影响度的程度。

**隔离级别分为：**

- **ISOLATION_DEFAULT** 
  - 这是一个PlatfromTransactionManager默认的隔离级别，使用数据库默认的事务隔离级别.另外四个与JDBC的隔离级别相对应；
- **ISOLATION_READ_UNCOMMITTED** 
  - 这是事务最低的隔离级别，它充许别外一个事务可以看到这个事务未提交的数据。这种隔离级别**会产生脏读，不可重复读和幻读。**
- **ISOLATION_READ_COMMITTED** 
  - 保证一个事务修改的数据提交后才能被另外一个事务读取。另外一个事务不能读取该事务未提交的数据。这种事务隔离级别**可以避免脏读出现，但是可能会出现不可重复读和幻读。**
- **ISOLATION_REPEATABLE_READ** 
  - 这种事务隔离级别**可以防止脏读，不可重复读。但是可能出现幻读。**它除了保证一个事务不能读取另一个事务未提交的数据外，还保证了避免下面的情况产生(不可重复读)
- **ISOLATION_SERIALIZABLE** 
  - 这是花费最高代价但是最可靠的事务隔离级别。事务被处理为顺序执行。**除了防止脏读，不可重复读外，还避免了幻读。**



#### 事务传播行为

当事务方法被另一个事务方法调用时，必须指定事务应该如何传播

Spring的7种传播行为：

| 事务传播行为类型          | 说明                                                         |
| :------------------------ | :----------------------------------------------------------- |
| PROPAGATION_REQUIRED      | 如果当前没有事务，就新建一个事务，如果已经存在一个事务中，加入到这个事务中。这是最常见的选择。 |
| PROPAGATION_SUPPORTS      | 支持当前事务，如果当前没有事务，就以非事务方式执行。         |
| PROPAGATION_MANDATORY     | 使用当前的事务，如果当前没有事务，就抛出异常。               |
| PROPAGATION_REQUIRES_NEW  | 新建事务，如果当前存在事务，把当前事务挂起。                 |
| PROPAGATION_NOT_SUPPORTED | 以非事务方式执行操作，如果当前存在事务，就把当前事务挂起。   |
| PROPAGATION_NEVER         | 以非事务方式执行，如果当前存在事务，则抛出异常。             |
| PROPAGATION_NESTED        | 如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则执行与PROPAGATION_REQUIRED类似的操作。 |



#### 事务只读

利用数据库事务的“只读”属性，进行特定优化处理

**注意**：

- 事务的是否“只读”属性，不同的数据库厂商支持不同。
- 通常而言：只读属性的应用要参考厂商的具体支持说明，比如：
  - Oracle的“readOnly”不起作用，不影响其增删改查。
  - MySql的“RealOnly’为true，只能查，增删改则出异常。

#### 事务超时

- 事务超时就是事务的一个定时器，在特定时间内事务如果没有执行完毕，那么就会自动回滚，而不是一直等待其结束。

- **设计事务时注意：**

  为了使应用程序很好地运行，事务不能运行太长的时间。因为事务可能涉及对后端数据库的锁定，所以长时间的事务会不必要的占用数据库资源

#### 事务回滚

- 默认情况下，事务只有遇到运行期异常时才会回滚，而在遇到检查型异常时不会回滚
- 自定义回滚策略：
  - 声明事务在遇到特定的检查型异常时像遇到运行期异常那样回滚；
  - 声明事务遇到特定的异常不回滚，即使这些异常时运行时异常。



### Spring事务状态

- 通过事务管理器获取 TransactionStatus 实例；
- 控制事务在回滚或提交的时候需要应用对应的事务状态；

#### 事务状态接口

- **boolean  isNewTransaction();**
  - 是否时新的事务
- **boolean  hasSavepoint；**
  - 是否有恢复点
- **void  setRollbackOnly()**
  - 设置只为回滚
- **boolean  isRollbackOnly()**
  - 是否只为回滚
- **boolean  isCompleted()**
  - 是否已完成



## 编程式事务管理方式

**实现方式：**

- 事务管理器方式
- 事务模板方式（推荐）



**总结：**

- 需要有效的数据源，具体数据源根据实际情况创建
- 创建编程事务管理对象：
  - 事务模板（TransactionTemplate）
  - 事务管理器（PlateformTransactionManager）
- 业务逻辑处理
  - 基于JdbcTemplate完成业务处理



### 事务管理器方式

**（PlatformTransactionManager）**

- 类似应用JTA UserTransaction API 方式，但异常处理更简洁；
- 核心类为：Spring事务管理的三个接口类以及jdbcTemplate类。



#### 步骤

1. **获取事务管理器**
2. **创建事务属性对象**
3. **获取事务状态对象**
4. **创建JDBC模板对象**
5. **业务数据操作处理**



#### 案例

```java
// 1、编程式事务管理：事务管理器PlatformTransactionManager方式实现
	public void updateBookByIsbn(Book book) {
		//第一步：获取JDBC事务管理器
		DataSourceTransactionManager dtm = TemplateUtils.getDataSourceTransactionManager();
		// 第二步：创建事务管理器属性对象
		DefaultTransactionDefinition transDef = new DefaultTransactionDefinition(); // 定义事务属性
		// 根据需要，设置事务管理器的相关属性
		// 设置传播行为属性
		transDef.setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRED);
		// 第三步：获得事务状态对象
		TransactionStatus ts = dtm.getTransaction(transDef);
		// 第四步：基于当前事务管理器,获取数据源，创建操作数据库的JDBC模板对象
		JdbcTemplate jt = new JdbcTemplate(dtm.getDataSource());
		try {//第五步：业务操作
			jt.update("update books set price="+book.getPrice()+",name='"+book.getName()
			              +"'  where isbn='"+book.getIsbn()+"' ");
			// 其它数据操作如增删
			//第六步：提交事务
			dtm.commit(ts); // 如果不commit，则更新无效果
		} catch (Exception e) {
			dtm.rollback(ts);
			e.printStackTrace();
		}
	}
```



### 事务模板的方式(推荐)

**（TransactionTemplate）**

- **Spring团队推荐的编程式事务管理方式**
- 主要工具为JdbcTemplate类。

#### 步骤

1. **获取事务管理器**
2. **获取事务属性对象**
3. **获取事务状态对象**
4. **创建JDBC模板对象**
5. **业务数据操作处理**



#### 案例1

```java
// 事务模板：第二种事务编程模式
// private TransactionTemplate transactionTemplate ;

// 数据持久化操作
public void addBook(Book book) {
    // 获取事务模板对象
    TransactionTemplate tt = TemplateUtils.getTransactionTemplate();
    // 可设置事务属性，如隔离级别、超时时间等,如：
    // tt.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
    tt.execute(new TransactionCallbackWithoutResult() {
        protected void doInTransactionWithoutResult(TransactionStatus s) {
            try {
                // 数据库操作1
                // JdbcTemplate jdbcTemplate
                // =TemplateUtils.getJdbcTemplate();
                // jdbcTemplate.execute(sql);
                // 简单模板化新增数据
                SimpleJdbcInsert simpleInsert = TemplateUtils.getSimpleJdbcTemplate();
                simpleInsert.withTableName("books").usingColumns("isbn", "name", "price", "pubdate");
                Map<String, Object> parameters = new HashMap<String, Object>();
                parameters.put("isbn", book.getIsbn());
                parameters.put("name", book.getName());
                parameters.put("price", book.getPrice());
                parameters.put("pubdate", book.getPubdate());
                simpleInsert.execute(parameters);
                System.out.println("新增数据成功！");
                // 或者DAO数据操作模式：
                // BookDAO.save(book);
            } catch (Exception e) {
                s.setRollbackOnly();
                e.printStackTrace();
            }
        }
    });
}
```



#### 案例2

```java
public Book findBookByIsbn(String isbn) {
    TransactionTemplate tt = TemplateUtils.getTransactionTemplate();
    Book book = null;
    @SuppressWarnings("unchecked")
    List<Map<String, Object>> books = (List<Map<String, Object>>) tt.execute(new TransactionCallback<Object>() {
        public Object doInTransaction(TransactionStatus arg0) {
            JdbcTemplate jdbcTemplate = TemplateUtils.getJdbcTemplate();
            return jdbcTemplate.queryForList("select isbn,name,price,pubdate from books where isbn ='" + isbn + "'");
        }
    });
    if (books != null) {// 封装获取的数据
        Map<String, Object> m = (Map) books.get(0);
        book = new Book();
        book.setIsbn(m.get("isbn").toString());
        book.setName(m.get("name").toString());
        book.setPrice((Float) m.get("price"));
        book.setPubdate((Date) m.get("pubdate"));
    }
    return book;
}
```





### TemplateUtil案例

```java
public class TemplateUtils {
	private final static  String dbDriver = "com.mysql.jdbc.Driver" ;
	private final static  String dbUrl = "jdbc:mysql://127.0.0.1:3306/test" ;
	private final static  String dbUser = "root";
	private final static  String dbPwd = "root";
	
	private static BasicDataSource dataSource ;
	//静态初识：创建连接数据源
	static {
	//创建DBCP简单数据源并初始化相关数据源属性
	//private void createSimpleDataSource(){
		dataSource = new   BasicDataSource() ;
		dataSource.setDriverClassName(dbDriver);
		dataSource.setUrl(dbUrl);
		dataSource.setUsername(dbUser);
		dataSource.setPassword(dbPwd);
		//指定数据库连接池初始连接数
		dataSource.setInitialSize(10);
		//设定同时向数据库申请的最大连接数
		dataSource.setMaxTotal(50);
		//设置连接池中保持的最少连接数量
		dataSource.setMinIdle(5);
	//}
	}
	public static TransactionTemplate getTransactionTemplate() {  
        PlatformTransactionManager txManager = new DataSourceTransactionManager(  
                dataSource);  
        return new TransactionTemplate(txManager);  
    }  
  
    public static JdbcTemplate getJdbcTemplate() {  
        return new JdbcTemplate(dataSource);  
    }  
  
    public static NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {  
        return new NamedParameterJdbcTemplate(dataSource);  
    }  
  
    public static SimpleJdbcInsert getSimpleJdbcTemplate() {  
        return new SimpleJdbcInsert(dataSource);  
    }  
    
    /**
     * //获取事务管理器：TransactionManager
     * 根据需要，可以是如JDBC、Hibernate,这里定义JDBC事务管理其
     * @return DataSourceTransactionManager
     */
    public static DataSourceTransactionManager getDataSourceTransactionManager(){
    	 DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
    	 // 设置数据源:此事务数据源须和正式事务管理器的数据源一致
    	 dataSourceTransactionManager.setDataSource(dataSource);
    	 return dataSourceTransactionManager;
    }
}
```



## 声明式事务管理方式

**实现原理：**基于AOP模式机制，对方法前后进行拦截。



**声明式事务管理的配置类型**：

- 独立代理；共享代理；拦截器；（旧，都不推荐）
- **tx拦截器；全注释（2.0版后，推荐）**



### 配置参考案例

```xml
<?xml version="1.0" encoding="UTF-8"?>  
<beans xmlns="http://www.springframework.org/schema/beans"  
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:aop="http://www.springframework.org/schema/aop"
    xmlns:tx="http://www.springframework.org/schema/tx"  
    xsi:schemaLocation="http://www.springframework.org/schema/beans   
    http://www.springframework.org/schema/beans/spring-beans-4.3.xsd
    http://www.springframework.org/schema/aop
    http://www.springframework.org/schema/aop/spring-aop-4.3.xsd
    http://www.springframework.org/schema/tx     
    http://www.springframework.org/schema/tx/spring-tx-4.3.xsd">

    <!-- 引入数据库连接属性配置文件 -->
    <bean id="propertyConfigurer"
          class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
        <property name="location" value="classpath:database.properties" />
    </bean>
    <bean id="dataSource" class="org.apache.commons.dbcp2.BasicDataSource"  destroy-method="close">
        <property name="driverClassName" value="${driver}" />
		<property name="url" value="${url}" />
		<property name="username" value="${username}" />
		<property name="password" value="${password}" />
    </bean>
	<!-- 配置数据源 -->
	<!-- #DBCP数据库连接池配置属性详细内容可参考官网描述：
       #http://commons.apache.org/proper/commons-dbcp/configuration.html 
       -->
    <!--<bean id="dataSource" class="org.apache.commons.dbcp2.BasicDataSource"
          destroy-method="close">
        <property name="driverClassName" value="com.mysql.jdbc.Driver" />
        <property name="url" value="com.mysql.jdbc.Driver" />
        <property name="username" value="root" />
        <property name="password" value="mysql" />
        &lt;!&ndash; 初始化连接大小 &ndash;&gt;
        <property name="initialSize" value="5"></property>
        &lt;!&ndash; 连接池最大空闲 &ndash;&gt;
        <property name="maxIdle" value="3"></property>
        &lt;!&ndash; 连接池最小空闲 &ndash;&gt;
        <property name="minIdle" value="2"></property>
    </bean>-->
      
    <!-- jdbc事务管理器 -->  
    <bean id="txManager"
        class="org.springframework.jdbc.datasource.DataSourceTransactionManager">  
        <property name="dataSource" ref="dataSource" />
    </bean>  
    <!--事务模板 -->
    <!-- 2、注释模式事务必要：启动使用注解实现声明式事务管理的支持   -->
    <tx:annotation-driven transaction-manager="txManager" />
    <!-- 想创建的服务对象：this is the service object that we want to make transactional -->
    <bean id="fooService" class="com.mooc.service.DefaultFooService"/>
    <bean id="xbeanService" class="com.mooc.service.XbeanServiceImpl"/>

    <!-- 1、通过事务通知的模式实现事务
    事务通知：the transactional advice (what 'happens'; see the <aop:advisor/> bean below) -->
    <tx:advice id="txAdvice" transaction-manager="txManager">
        <!-- the transactional semantics... -->
        <tx:attributes>
            <!-- 以get开头的所有方法都为只读事务：all methods starting with 'get' are read-only -->
            <tx:method name="get*" read-only="true"/>
            <!-- 其它方法使用默认事务设置：other methods use the default transaction settings (see below) -->
            <tx:method name="*"/>
        </tx:attributes>
    </tx:advice>
    <!-- 确保上述事务通知对定义在FooService接口中的方法都起作用(
    ensure that the above transactional advice runs for any execution
    of an operation defined by the FooService interface) -->
    <aop:config>
        <aop:pointcut id="fooServiceOperation" expression="execution(* com.mooc.service.FooService.*(..))"/>
        <aop:advisor advice-ref="txAdvice" pointcut-ref="fooServiceOperation"/>
    </aop:config>
</beans>  
```



### 声明式事务管理完整案例

**书籍管理操作**

SpringContext:

```xml
<!-- 引入数据库连接属性配置文件 -->
<bean id="propertyConfigurer"
      class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
    <property name="location" value="classpath:database.properties" />
</bean>
<!-- 配置数据源 -->  
<!-- #DBCP数据库连接池配置属性详细内容可参考官网描述：
       #http://commons.apache.org/proper/commons-dbcp/configuration.html 
       -->
<bean id="dataSource" class="org.apache.commons.dbcp2.BasicDataSource"
      destroy-method="close">
    <property name="driverClassName" value="${driver}" />
    <property name="url" value="${url}" />
    <property name="username" value="${username}" />
    <property name="password" value="${password}" />
    <!-- 初始化连接大小 -->
    <property name="initialSize" value="${initialSize}"></property>
    <!-- 连接池最大空闲 -->
    <property name="maxIdle" value="${maxIdle}"></property>
    <!-- 连接池最小空闲 -->
    <property name="minIdle" value="${minIdle}"></property>
</bean>

<!-- jdbc事务管理器 -->  
<bean id="txManager"
      class="org.springframework.jdbc.datasource.DataSourceTransactionManager">  
    <property name="dataSource" ref="dataSource" />	
</bean>  

<!-- 2、注释模式事务：启动使用注解实现声明式事务管理的支持   -->
<tx:annotation-driven transaction-manager="txManager" />
<!-- 要创建的事务服务对象 -->
<bean id="bookService" class="com.mooc.service.BookServiceImpl">
    <property name="dataSource" ref="dataSource"/>
</bean>
<!-- 1、通过事务通知的(AOP)模式实现事务管理
    事务通知-->
<tx:advice id="txAdvice" transaction-manager="txManager">
    <!--事务语义定义... -->
    <tx:attributes>
        <!-- 以get开头的所有方法都为只读事务 -->
        <tx:method name="find*"  read-only="true"/>
        <!-- 其它方法使用默认事务设置 -->
        <tx:method name="*"/>
    </tx:attributes>
</tx:advice>
<!-- 确保上述事务通知对定义在BookService接口中的方法都起作用，
           即对每个方法都开启一个对应的事务 -->
<aop:config>
    <aop:pointcut id="bookServiceOperation" expression="execution(* com.mooc.service.BookService.*(..))"/>
    <aop:advisor advice-ref="txAdvice" pointcut-ref="bookServiceOperation"/>
</aop:config>
```



## 两种事务管理的区别

- 编程式事务允许用户在代码中精确定义事务的边界
- 声明式事务有助于用户将操作与事务规则进行解耦
  - 基于AOP交由Spring容器实现；
  - 实现关注点聚焦在业务逻辑上。
- 概括而言：
  - 编程式事务侵入到业务代码里面，但是提供了更加详细的事务管理控制；而声明式事务由于基于AOP，所以既能起到事务管理的作用，又可以不影响业务代码的具体实现。



## 两种事务管理的选择

- 小型应用、事务操作少：
  - 建议**编程式事务管理**实现：TransactionTemplate；
  - 简单、显式操作、直观明显、可以设置事务名称。
- 大型应用，事务操作量多：
  - 业务复杂度高，关联性紧密，建议**声明式事务管理**实现
  - 关注点聚焦到业务层面，实现业务和事务的解耦



## 通用问题解决方案

- 事务管理器类型
  - 基于不同的数据源选择对应的事务管理器；
  - 选择正确的PlatformTransactionManager实现类；
  - 全局事务的选择：JtaTransactionManager。