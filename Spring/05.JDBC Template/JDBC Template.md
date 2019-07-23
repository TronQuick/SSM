# JDBC Template

- JDBC Template 提供统一的模板方法，在保留代码灵活性的基础上，尽量减少持久化代码
- JDBC Template是Spring框架对JDBC操作的封装，简单、灵活但不够强大
- 实际应用中还需要和其他ORM框架混合使用



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

- execute方法
- update & batchUpdate方法
- query & queryXXX方法
- call方法

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

#### update方法

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




#### batchUpdate方法

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
  public void testBatchUpdate2(){
          String sql = "insert into selection(student,course) values(?,?)";
          List<Object[]> list = new ArrayList<Object[]>();
          list.add(new Object[] {1005,1001});
          list.add(new Object[] {1005,1003});
          jdbcTemplate.batchUpdate(sql,list);
      }
  ```




### **query与queryXXX方法**

进行对数据进行查询

#### 查询简单数据项

- **获取一个**

```java
T queryForObject(String sql, Class<T> type);

T queryForObject(String sql, Object[] args,Class<T> type);

T queryForObject(String sql, Class<T> type,Object... arg);
```

例：

```java
// 查询一个简单数据项
public void testQuerySimple1() {
	String sql = "select count(*) from student";
	int count = jdbcTemplate.queryForObject(sql,Integer.class);
	System.out.println(count);
}
```



- **获取多个**

```java
List<T> queryForList(String sql,Class<T> type);

List<T> queryForList(String sql,Object[] args,Class<T> type);

List<T> queryForList(String sql, Class<T> type,Object... atg);
```

例：

```java
// 查询多个简单数据项
public void testQuerySimple2() {
	String sql = "select name from student where sex=?";
	List<String> names = jdbcTemplate.queryForList(sql,String.class,"男");
	System.out.println(names);
}
```



#### 查询复杂对象（封装为Map）

- **获取一个**

```java
Map queryForMap(String sql);

Map queryForMap(String sql , Object[] args);

Map queryForMap(String sql , Object... args);
```

例：

```java
// 查询一个复杂数据项(封装为Map)
public void testQueryMap1() {
	String sql = "select * from student where id=?";
	Map<String,Object> stu = jdbcTemplate.queryForMap(sql,1003);
	System.out.println(stu);
}
```



- **获取多个**

```java
List<Map<String,Object>> queryForList(String sql);

List<Map<String,Object>> queryForList(String sql , Object[] args);

List<Map<String,Object>> queryForList(String sql , Object... arg);
```

例：

```java
// 查询多个复杂数据项(封装为Map)
public void testQueryMap2() {
	String sql = "select * from student";
	List<Map<String,Object>> students = jdbcTemplate.queryForList(sql);
	System.out.println(students);
}
```



#### 查询复杂对象（封装为实体对象）

- **RowMapper接口**
- **获取一个**

```java
T queryForObject(String sql,RowMapper<T> mapper);

T queryForObject(String sql,Object[] args,RowMapper<T> mapper);

T queryForObject(String sql,RowMapper<T> mapper,Object... arg);
```

例：

```java
//获取单条记录
public void testQueryEntity1() {
	String sql = "select * from student where id=?";
	Student stu = jdbcTemplate.queryForObject(sql, new RowMapper<Student>() {
public Student mapRow(ResultSet resultSet, int i) throws SQLException {
        //配置映射关系
        Student stu = new Student();
        stu.setId(resultSet.getInt("id"));
        stu.setName(resultSet.getString("name"));
        stu.setSex(resultSet.getString("sex"));
        stu.setBorn(resultSet.getDate("born"));
        return stu;
        }
    }, 1004);
	System.out.println(stu);
}
```



- **获取多个**

```java
List<T> query(String sql,RowMapper<T> mapper);

List<T> query(String sql,Object[] args,RowMapper<T> mapper);

List<T> query(String sql,RowMapper<T> mapper,Object... arg);
```

例：

```java
// 获取多条记录（封装为实体）
public void testQueryEntity2() {
	String sql = "select * from student";
	List<Student> students = jdbcTemplate.query(sql, new StudentRowMapper());
	System.out.println(students);
}

// 不建议使用匿名内部类，可通过自定义一个类来描述映射关系:
private class StudentRowMapper implements RowMapper<Student>{
    public Student mapRow(ResultSet resultSet, int i) throws SQLException {
        Student stu = new Student();
        stu.setId(resultSet.getInt("id"));
        stu.setName(resultSet.getString("name"));
        stu.setSex(resultSet.getString("sex"));
        stu.setBorn(resultSet.getDate("born"));
        return stu;
    }
}
```



## JDBC Template持久层

### 项目结构

- 实体类
- DAO
  - 注入Jdbc Template
  - 声明RowMapper



### 案例

- 创建Dao接口

```java
public interface StudentDao {
    void insert(Student student);
    void update(Student student);
    void delete(Student student);
    Student select(int id);
    List<Student> selectAll();
}
```

- Dao接口实现类

```java
@Repository // 通知Spring，这个类为持久化操作对象
public class StudentDaoImpl implements StudentDao {

    @Autowired  // 自动注入jdbc Template组件
    private JdbcTemplate jdbcTemplate;

    // 配置RowMapper映射
    private class StudentRowMapper implements RowMapper<Student> {
        public Student mapRow(ResultSet resultSet, int i) throws SQLException {
            Student stu = new Student();
            stu.setId(resultSet.getInt("id"));
            stu.setName(resultSet.getString("name"));
            stu.setSex(resultSet.getString("sex"));
            stu.setBorn(resultSet.getDate("born"));
            return stu;
        }
    }

    public void insert(Student student) {
        String sql="insert into student(name,sex,born) values(?,?,?)";
        jdbcTemplate.update(sql,student.getName(),student.getSex(),student.getBorn());

    }

    public void update(Student student) {
        String sql="update student set name=?,sex=?,born=? where id=?";
        jdbcTemplate.update(sql,student.getName(),student.getSex(),student.getBorn(),student.getId());
    }

    public void delete(Student student) {
        String sql="delete from student where id=?";
        jdbcTemplate.update(sql,student.getId());
    }

    public Student select(int id) {
        String sql="select * from student where id=?";
        return jdbcTemplate.queryForObject(sql,new StudentRowMapper(),id);
    }

    public List<Student> selectAll() {
        String sql="select * from student";
        return jdbcTemplate.query(sql,new StudentRowMapper());
    }

}
```



## 优缺点分析

- 优点
  - 简答
  - 灵活
- 缺点
  - SQL与java代码参杂
  - 功能不丰富