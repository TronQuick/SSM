package com.imooc.dao;

import com.imooc.entity.Course;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class CourseDao {
    // 用HashMap模拟数据库
    private Map<Integer, Course> courses= new HashMap<Integer,Course>();

    // 新增
    public void add(Course course) {
        courses.put(course.getId(),course);
    }

    // 查询所有课程
    public Collection<Course> getAll() {
        return courses.values();
    }

    // 通过id查询
    public Course getById(int id) {
        return courses.get(id);
    }

    // 修改
    public void update(Course course) {
        courses.put(course.getId(),course);
    }

    // 删除
    public void delete(int id) {
        courses.remove(id);
    }
}
