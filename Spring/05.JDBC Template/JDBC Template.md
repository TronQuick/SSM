# JDBC Template

- JDBC Template 提供统一的模板方法，在保留代码灵活性的基础上，尽量减少持久化代码

```java
Integer count = jt.queryForObject("select count(*) from student",Integer.class)
```



## 项目搭建

- Maven
  - Mysql驱动
  - Spring组件(core / beans / context / aop)
  - JDBC Template(jdbc /tx)

- Spring配置

  - 数据源

  - JDBC Templat

  - 例：

    ```xml
    <!--配置数据源-->
    <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
        <!--选择selection_course数据库-->
        <property name="url" value="jdbc:mysql://localhost:3306/selection_course?useUnicode=true&amp;characterEncoding=utf-8"/>
        <property name="username" value="root"/>
        <property name="password" value="root"/>
    </bean>
    
    <!--jdbc template-->
    <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
        <property name="dataSource" ref="dataSource"></property>
    </bean>
    ```



## JDBC Template基本使用

### execute方法

- 运行 sql 语句

- 例：

  ```java
  public void testExecute() {
      ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
      JdbcTemplate jdbcTemplate = (JdbcTemplate) context.getBean("jdbcTemplate");
      // jdbcTemplate.executer(SQL语句)
      jdbcTemplate.execute("create table user1(id int,name varchar(20))");
  }
  ```

  

### update 与 batchUpdate 方法

- **update方法**

  - **用于对数据进行增删改操作**

    ```java
    // 1.
    int update(String sql,Object[] args)
    // 2.
    int update(String sql,Object... args)
    ```

  - 例：

    ```java
    // 1.
    public void testUpdate() {
        String sql = "insert into student(name,sex) values(?,?)";
        jdbcTemplate.update(sql,new Object[]{"张飞","男"});
    }
    ```

    ```java
    // 2.
    public void testUpdate2() {
        String sql = "update student set sex=? where id=?";
        jdbcTemplate.update(sql,"女",1003);
    }
    ```

    

- **batchUpdate方法**

  - **批量增删改操作**

    ```java
    // 3.
    int[] batchUpdate(String[] sql)
    
    // 4.适合同构sql，批量插入相同结构不同值数据
    int[] batchUpdate(String sql,List<Object[]> args)
    ```

  - 例：

    ```java
    // 3.
    public void  testBatchUpdate() {
        String[] sqls={
            "insert into student(name,sex) values('关羽','男')",
            "insert into student(name,sex) values('貂蝉','女')",
            "update student set sex='男' where id=1003"
        };
        jdbcTemplate.batchUpdate(sqls);
    }
    ```

    ```java
    // 4.
    
    ```

    



### **query与queryXXX方法**

- 对数据进行查询

### call方法