package com.imooc.controller;

import com.imooc.dao.CourseDao;
import com.imooc.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
