package com.imooc.handler;

import com.imooc.entity.Goods;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;


@Controller
public class AnnotationHandler {
    /**
     *  业务方法： 使用ModelAndView完成数据的传递，视图的解析（逻辑视图 -> 物理视图）；
     */
    // 配置url映射
    @RequestMapping("/modelAndViewTest")
    public ModelAndView modelAndViewTest() {
        // 创建ModelAndView对象
        ModelAndView modelAndView = new ModelAndView();
        // 填充模型数据
        modelAndView.addObject("name","Tom");
        // 设置逻辑视图
        modelAndView.setViewName("show");

        return modelAndView;
    }


    /**
     *  业务方法：使用Model传值，返回String进行视图解析；
     *  相当于拆分了ModelAndView，用String作为View；
     * */
    @RequestMapping("/modelTest")
    public String ModelTest(Model model) {
        // 填充模型数据
        model.addAttribute("name","Jerry");
        // 设置逻辑视图
        return "show";
    }

    /**
     *  业务方法：Map传值，String进行视图解析；
     * */
    @RequestMapping("/mapTest")
    public String MapTest(Map<String,String> map) {
        // 填充数据模型
        map.put("name","Cat");
        // 设置逻辑视图
        return "show";
    }

    /**
     *  业务方法：添加商品并展示
     * */
    @RequestMapping("/addGoods")
    public ModelAndView addGoods(Goods goods) {
        System.out.println(goods.getName()+"----"+goods.getPrice());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("goods",goods);
        modelAndView.setViewName("show");
        return modelAndView;
    }

}
