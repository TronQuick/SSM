package com.imooc.controller;

import com.imooc.dao.CourseDAO;
import com.imooc.entity.Course;
import com.imooc.entity.CourseList;
import com.imooc.entity.CourseMap;
import com.imooc.entity.CourseSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DataBindController {

    @Autowired
    private CourseDAO courseDAO;

    // 基本数据类型的数据绑定，不能为null
    @RequestMapping(value = "/baseType")
    @ResponseBody
    public String baseType(@RequestParam(value = "id") int id){
        return "id:"+id;
    }

    // 包装类的数据绑定，
    @RequestMapping(value = "/packageType")
    @ResponseBody
    public String packageType(@RequestParam(value = "id") Integer id){
        return "id:"+id;
    }

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

    // 对象的数据绑定
    @RequestMapping(value = "/pojoType")
    public ModelAndView pojoType(Course course){
        courseDAO.add(course);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("courses",courseDAO.getAll());
        return modelAndView;
    }

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

    // JSON
    @RequestMapping(value = "/jsonType")
    @ResponseBody
    public Course jsonType(@RequestBody Course course) {
        course.setPrice(course.getPrice()+100);
        return course;
    }

}
