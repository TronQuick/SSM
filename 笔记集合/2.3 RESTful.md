# RESTful

## 风格概述

**RESTful概念**

- REST: Representational State Transfer（表述性状态转移）

- RESTful不是一套标准，指的是一组架构约束条件和原则，只是一种开发方式，架构思想。

- 符合REST的约束条件和原则的架构，称为RESTful架构

  

**RESTful核心内容**

- 资源与URI（资源地址）
- 资源的表述
- 状态转移



**RESTful架构特点**

- 统一了客户端访问资源的接口
- url更加简洁，易于理解，便于拓展
- 有利于不同系统之间的资源共享



**RESTful具体来讲就是HTTP协议的四种形式表示四种基本操作**

- **GET：获取资源**
- **POST：新建资源**
- **PUT：修改资源**
- **DELETE：删除资源**



**如：**

查询课程：http://localhost:8080/id method='get'

删除课程：http://localhost:8080/id method='delete'

修改课程：http://localhost:8080/course method='put'





## 实现案例

**首先在web.xml中添加过滤器：**

```xml
<filter>
    <filter-name>hiddenHttpMethodFilter</filter-name>
    <filter-class>org.springframework.web.filter.HiddenHttpMethodFilter</filter-class>
</filter>

<filter-mapping>
    <filter-name>hiddenHttpMethodFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
```



**通过添加隐藏标签转换请求：**

在表单中加入**隐藏标签**，过滤器可以转换请求，

如：将`<input type="hidden" name="_method" value="PUT"/>`

添加到`method="post"`的表单中，过滤器会将post转换为PUT请求

```html
<form action="/update" method="post">

<input type="hidden" name="_method" value="PUT"/>

<button type="submit" >提交</button>

</form>
```



**Controller:**

```java
@Controller
public class CourseController {

    @Autowired
    private CourseDao courseDao;

    // 添加课程
    @PostMapping(value = "/add")
    public String add(Course course) {
        courseDao.add(course);
        return "redirect:/getAll";
    }

    // 查询所有课程
    @GetMapping(value = "/getAll")
    public ModelAndView getAll() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("courses",courseDao.getAll());
        return modelAndView;
    }

    // 通过id查询课程
    @GetMapping(value = "/getById/{id}")
    public ModelAndView getById(@PathVariable(value = "id")int id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("edit");
        modelAndView.addObject("course",courseDao.getById(id));
        return modelAndView;
    }

    // 修改课程
    @PutMapping(value = "/update")
    public String update(Course course){
        courseDao.update(course);
        return "redirect:/getAll";
    }

    // 删除
    @DeleteMapping(value = "/delete/{id}")
    public String delete(@PathVariable(value = "id")int id) {
        courseDao.delete(id);
        return "redirect:/getAll";
    }

}
```



