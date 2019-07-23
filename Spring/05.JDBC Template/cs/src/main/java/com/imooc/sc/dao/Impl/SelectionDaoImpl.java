package com.imooc.sc.dao.Impl;

import com.imooc.sc.dao.SelectionDao;
import com.imooc.sc.entity.Selection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository // 通知Spring，这个类为持久化操作对象
public class SelectionDaoImpl implements SelectionDao {

    @Autowired  // 自动注入jdbc Template组件
    private JdbcTemplate jdbcTemplate;

    public void insert(List<Selection> selections) {
        String sql = "insert into selection values(?,?,?,?)";
        List<Object[]> list = new ArrayList<Object[]>();
        for (Selection selection : selections) {
            Object[] args = new Object[4];
            args[0]=selection.getSid();
            args[1]=selection.getCid();
            args[2]=selection.getSelTime();
            args[3]=selection.getScore();
            list.add(args);
        }
        jdbcTemplate.batchUpdate(sql, list);
    }

    public void delete(int sid, int cid) {
        String sql = "delete from selection where student=? and course=?";
        jdbcTemplate.update(sql,sid,cid);
    }

    public List<Map<String, Object>> selectByStudent(int sid) {
        String sql = "select se.*,stu.name sname,cou.name cname from selection se " +
                "left join student stu on se.student=stu.id " +
                "left join course cou on se.course=cou.id" +
                "where student=?";
        return jdbcTemplate.queryForList(sql,sid);
    }

    public List<Map<String, Object>> selectByCourse(int cid) {
        String sql = "select se.*,stu.name sname,cou.name cname from selection se " +
                "left join student stu on se.student=stu.id " +
                "left join course cou on se.course=cou.id" +
                "where course=?";
        return jdbcTemplate.queryForList(sql,cid);
    }
}
