package com.imooc.sc.dao.Impl;

import com.imooc.sc.dao.StudentDao;
import com.imooc.sc.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository // 通知Spring，这个类为持久化操作对象
public class StudentDaoImpl implements StudentDao {

    @Autowired  // 自动注入jdbc Template组件
    private JdbcTemplate jdbcTemplate;

    // 配置RowMapper映射
    private class StudentRowMapper implements RowMapper<Student> {
        public Student mapRow(ResultSet resultSet, int i) throws SQLException {
            Student stu = new Student();
            stu.setId(resultSet.getInt("id"));
            stu.setName(resultSet.getString("name"));
            stu.setSex(resultSet.getString("sex"));
            stu.setBorn(resultSet.getDate("born"));
            return stu;
        }
    }

    public void insert(Student student) {
        String sql="insert into student(name,sex,born) values(?,?,?)";
        jdbcTemplate.update(sql,student.getName(),student.getSex(),student.getBorn());

    }

    public void update(Student student) {
        String sql="update student set name=?,sex=?,born=? where id=?";
        jdbcTemplate.update(sql,student.getName(),student.getSex(),student.getBorn(),student.getId());
    }

    public void delete(Student student) {
        String sql="delete from student where id=?";
        jdbcTemplate.update(sql,student.getId());
    }

    public Student select(int id) {
        String sql="select * from student where id=?";
        return jdbcTemplate.queryForObject(sql,new StudentRowMapper(),id);
    }

    public List<Student> selectAll() {
        String sql="select * from student";
        return jdbcTemplate.query(sql,new StudentRowMapper());
    }

}
