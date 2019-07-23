package com.imooc.sc.dao;

import com.imooc.sc.entity.Course;
import com.imooc.sc.entity.Student;

import java.util.List;

public interface CourseDao {
    void insert(Course course);
    void update(Course course);
    void delete(Course course);
    Course select(int id);
    List<Course> selectAll();
}
