# SpringMVC数据绑定



## 原理

**什么是数据绑定？**

将HTTP请求中的参数绑定到Handler业务方法的形参



**常用的数据绑定类型：**

1. 基本数据类型
2. 包装类
3. 数组
4. 对象
5. 集合（List，Set，Map）
6. JSON



## 使用方法

Tips：

- @RequestParam(value="xxx")：

  相似于request.getParameter("xxx")



### 基本数据类型

```java
// 基本数据类型的数据绑定，不能为null
@RequestMapping(value = "/baseType")
@ResponseBody //返回本页面
public String baseType(@RequestParam(value = "id") int id){
    return "id:"+id;
}
```



### 包装类

```java
// 包装类的数据绑定，包装类可以为null
@RequestMapping(value = "/packageType")
@ResponseBody // 返回本页面
public String packageType(@RequestParam(value = "id") Integer id){
return "id:"+id;
}
```



### 数组

```java
// 数组的数据绑定
@RequestMapping(value = "/arrayType")
@ResponseBody
public String arrayType(String[] name){
StringBuffer sbf = new StringBuffer();
for (String item:name){
sbf.append(item).append(" ");
}
return sbf.toString();
}
```



### 对象



```java
// 对象的数据绑定
@RequestMapping(value = "/pojoType")
public ModelAndView pojoType(Course course){
courseDAO.add(course);
ModelAndView modelAndView = new ModelAndView();
modelAndView.setViewName("index");
modelAndView.addObject("courses",courseDAO.getAll());
return modelAndView;
}
```



**前台表单：**

通过表单中input的name属性与对象属性名一一对应，完成对象的属性传值封装，然后传递给controller。

```html
<form action="pojoType" method="post">

    <input type="text" name="id" placeholder="请输入课程编号">

    <input type="text" name="name" placeholder="请输入课程名称">

    <input type="text"  name="price" placeholder="请输入课程价格">

    <input type="text" name="author.id" placeholder="请输入讲师编号">

    <input type="text" name="author.name" placeholder="请输入讲师姓名">
    
    <button type="submit">提交</button>
</form>
```



### 集合

形参不能直接写List/Map/Set，需要自己编写一个包装类来完成绑定

#### **List**

```java
// 集合的数据绑定
@RequestMapping(value = "/listType")
public ModelAndView listType(CourseList courseList){
    for(Course course:courseList.getCourses()){
        courseDAO.add(course);
    }
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("index");
    modelAndView.addObject("courses",courseDAO.getAll());
    return modelAndView;
}
```

**前台表单：**

```html
<form action="listType" method="post">

<input type="text" name="courses[0].id" placeholder="请输入课程编号">
ontrol-label">课程1名称</label>

<input type="text" name="courses[0].name" placeholder="请输入课程名称">

<input type="text" name="courses[0].price" placeholder="请输入课程价格">

<input type="text" name="courses[0].author.id" placeholder="请输入讲师编号">

<input type="text" name="courses[0].author.name" placeholder="请输入讲师姓名">

<input type="text" name="courses[1].id" placeholder="请输入课程编号">

<input type="text" name="courses[1].name" placeholder="请输入课程名称">

<input type="text" name="courses[1].price" placeholder="请输入课程价格">

<input type="text" name="courses[1].author.id" placeholder="请输入讲师编号">

<input type="text" name="courses[1].author.name" placeholder="请输入讲师姓名">

<button type="submit">提交</button>
</form>
```



#### Map

```java
@RequestMapping(value = "/mapType")
public ModelAndView mapType(CourseMap courseMap){
    for(String key:courseMap.getCourses().keySet()){
        Course course = courseMap.getCourses().get(key);
        courseDAO.add(course);
    }
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("index");
    modelAndView.addObject("courses",courseDAO.getAll());
    return modelAndView;
}

```

**前台表单：**

```html
<form action="mapType" method="post">
<input type="text" name="courses['one'].id" placeholder="请输入课程编号">

<input type="text" name="courses['one'].name" placeholder="请输入课程名称">

<input type="text" name="courses['one'].price" placeholder="请输入课程价格">

<input type="text" name="courses['one'].author.id" placeholder="请输入讲师编号">

<input type="text" name="courses['one'].author.name" placeholder="请输入讲师姓名">

<input type="text" name="courses['two'].id" placeholder="请输入课程编号">

<input type="text" name="courses['two'].name" placeholder="请输入课程名称">

<input type="text" name="courses['two'].price" placeholder="请输入课程价格">

<input type="text" name="courses['two'].author.id" placeholder="请输入讲师编号">

<input type="text" name="courses['two'].author.name" placeholder="请输入讲师姓名">

<button type="submit">提交</button>
</form>
```



#### Set

```java
@RequestMapping(value = "/setType")
public ModelAndView setType(CourseSet courseSet){
    for (Course course:courseSet.getCourses()){
        courseDAO.add(course);
    }
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("index");
    modelAndView.addObject("courses",courseDAO.getAll());
    return modelAndView;
}
```

**前台表单：**

```html
<form action="setType" method="post">

<input type="text" name="courses[0].id" placeholder="请输入课程编号">

<input type="text" name="courses[0].name" placeholder="请输入课程名称">

<input type="text" name="courses[0].price" placeholder="请输入课程价格">

<input type="text" name="courses[0].author.id" placeholder="请输入讲师编号">

<input type="text" name="courses[0].author.name" placeholder="请输入讲师姓名">

<input type="text" name="courses[1].id" placeholder="请输入课程编号">

<input type="text" name="courses[1].name" placeholder="请输入课程名称">

<input type="text" name="courses[1].price" placeholder="请输入课程价格">

<input type="text" name="courses[1].author.id" placeholder="请输入讲师编号">

<input type="text" name="courses[1].author.name" placeholder="请输入讲师姓名">

<button type="submit">提交</button>

</form>
```





### JSON

1. 引入JSON依赖

```xml
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.8.3</version>
</dependency>
```



2. 在springmvc.xml中配置消息转换器，才能将Http请求转换为 JSON 格式

```xml
<mvc:annotation-driven>
    <mvc:message-converters>
        <bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter"></bean>
    </mvc:message-converters>
</mvc:annotation-driven>
```



3. 编写后台

   **@RequestBody：将JSON格式数据绑定到形参中**

   ```java
   // JSON
   @RequestMapping(value = "/jsonType")
   @ResponseBody
   public Course jsonType(@RequestBody Course course) {
       course.setPrice(course.getPrice()+100);
       return course;
   }
   ```

   **前端JS编写**

   ```html
   <script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
   <script type="text/javascript">
   
       $(function(){
           var course = {
               "id":"8",
               "name":"SSM框架整合",
               "price":"200"
           };
           $.ajax({
               url:"jsonType",
               data:JSON.stringify(course),
               type:"post",
               contentType:"application/json;charse=UTF-8",
               dataType:"json",
               success:function(data){
                   alert(data.name+"---"+data.price);
               }
           })
       })
   
   </script>
   ```

   

