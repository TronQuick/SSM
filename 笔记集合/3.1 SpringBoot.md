# SpringBoot

**SpringBoot应用开发流程**

1. 配置环境
2. Spring Initializr
3. 配置参数（可选）
4. 业务开发
5. 自动构建
6. 自动部署
7. 运维与监控





## 目录结构

| /src/main                         | 项目根目录         |
| --------------------------------- | ------------------ |
| /java                             | java源代码目录     |
| /resources                        | 资源目录           |
| /resources/static                 | 静态资源目录       |
| /resources/templates              | 表示层页面目录     |
| /resources/application.properties | Springboot配置文件 |
| /test                             | 测试文件目录       |



## 入口类

- 入口类命名通常以` *Application` 结尾
- 入口类上增加`@SpringBootApplication` 注解

- 利用`SpringApplication.run()`方法启动应用

例：

```java
@SpringBootApplication
public class MyspringbootApplication {
    public static void main(String[] args) {
   		SpringApplication.run(MyspringbootApplication.class, args);
    }
}
```



## 启动流程

![SpringBoot启动流程](.\SpringBoot启动流程.png)





## 常用配置

| 配置名称                    | 默认值 | 描述               |
| --------------------------- | ------ | ------------------ |
| server.port                 | 8080   | 端口号             |
| server.servlet.context-path | /      | 设置应用上下文     |
| logging.file                | 无     | 日志文件输出路径   |
| logging.level.root          | info   | 最低日志输出级别   |
| debug                       | false  | 开关调试模式       |
| spring.datasource.*         |        | 与数据库相关的设置 |
| ...                         |        | ...                |



例：

```dockerfile
#端口号
server.port=80

#应用上下文
server.servlet.context-path=/myspringboot

#日志文件输出路径，若不指定则为项目根目录
logging.file=myLog.log

#最低日志输出级别
#级别排序：debug -> info -> warn -> error ->fatal
logging.level.root=info

#询问是否开启调试模式
debug=false

#数据库设置
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/test
spring.datasource.username=root
spring.datasource.password=root
```



## YAML格式配置文件

YAML是一种简洁的非标记语言，YAML以数据为中心，使用空白，缩进，分行组织数据，从而使得表示更加简洁易读。



### **语法格式**

- 标准格式： **key:(空格)value**
- 使用空格代表层级关系，以 **“ : ”** 结束



例：application.yml

```yaml
debug: false

logging:
  level:
    root: info
  file: myLog.log

spring:
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://localhost:3306/test
    data-username: root
    data-password: root
```



### YAML自定义配置项

- Spring Boot允许我们自定义应用配置项，在程序运行时允许动态加载，这为程序提供了良好的可维护性。

- 在实际项目开发中，我们通常将项目的自定义信息放在配置文件中。

  

**使用**

例：

application.yml:

```yaml
mall:
  config:
    name: 爱美商城
    description: 这是一家化妆品特卖网站
    hot-sales: 20
    show-advert: true
```

Controller:

```java
@Controller
public class MyController {
    
    @Value("${mall.config.name}")
    private String name;
    
    @Value("${mall.config.description}")
    private String description;
    
    @Value("${mall.config.hot-sales}")
    private Integer hotSales;
    
    @Value("${mall.config.show-advert}")
    private Boolean showAdvert;


    @RequestMapping("/info")
    @ResponseBody
    public String info() {
         return  String.format("name:%s,description:%s,hot-sales:%s,show-advert:%s",
                name,description,hotSales,showAdvert);
    }
}
```

运行后，窗口内显示:

```html
name:爱美商城,description:这是一家化妆品特卖网站,hot-sales:20,show-advert:true
```



## 环境配置文件

- Spring Boot可针对不同的环境提供不同的Profile文件。
- Pro文件的默认命名格式为 **application-{环境名}.yml**。
- 使用 **spring.profiles.active** 选项来指定不同的profile。



如目录下有：application-dev.yml  和  application-prd.yml两个配置文件，在application.yml中：

```yaml
spring:
  profiles:
    active: dev
```

即可指定环境名为dev的配置文件



## 打包与运行

- 利用Maven的package命令，生成可独立运行的Jar包
- 利用java -jar xxx.jar 命令启动 Spring Boot应用
- Jar包可自动加载同目录的application配置文件