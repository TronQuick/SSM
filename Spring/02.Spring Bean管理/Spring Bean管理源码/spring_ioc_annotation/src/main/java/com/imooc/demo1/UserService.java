package com.imooc.demo1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Spring的Bean管理的注解方式：
 *  * 传统方式需要去XML中配置<bean id="" class=""></bean>
 */
//@Component("userService")
@Service("userService")
public class UserService {
    @Value("米饭")
    private String something;
/*    @Autowired
    @Qualifier("userDao")*/
    @Resource(name="userDao")
    private UserDao userDao;

    public String sayHello(String name){
        return "Hello" + name;
    }

    public void eat(){
        System.out.println("eat:"+something);
    }

    public void save(){
        System.out.println("Service中保存用户...");
        userDao.save();
    }
}
