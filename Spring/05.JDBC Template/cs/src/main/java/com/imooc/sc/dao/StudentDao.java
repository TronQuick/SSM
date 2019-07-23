package com.imooc.sc.dao;

import com.imooc.sc.entity.Student;

import java.util.List;

public interface StudentDao {
    void insert(Student student);
    void update(Student student);
    void delete(Student student);
    Student select(int id);
    List<Student> selectAll();
}
