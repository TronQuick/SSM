package com.imooc.controller;

import com.imooc.entity.Goods;
import com.imooc.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator.
 */

@RequestMapping("/AnnotationHandler")
@Controller
public class AnnotationHandler {
    
    @RequestMapping("/ModelTest")
    public String ModelTest(Model model){
        User user = new User();
        user.setName("Tom");
        model.addAttribute("user",user);
        return "index";
    }

    @RequestMapping("/MapTest")
    public String MapTest(Map<String,User> map){
        User user = new User();
        user.setName("Jerry");
        map.put("user",user);
        return "index";
    }

    @RequestMapping("/ModelAndViewTest")
    public ModelAndView ModelAndViewTest(){
        User user = new User();
        user.setName("Cat");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user",user);
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @RequestMapping("/add")
    public ModelAndView add(Goods goods){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("goods",goods);
        modelAndView.setViewName("show");
        return modelAndView;
    }

}
