import com.imooc.sc.entity.Student;
import javafx.application.Application;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Test {
    private JdbcTemplate jdbcTemplate;

    {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        jdbcTemplate = (JdbcTemplate) context.getBean("jdbcTemplate");

    }

    @org.junit.Test
    public void testExecute() {
        jdbcTemplate.execute("create table user1(id int,name varchar(20))");
    }

    @org.junit.Test
    public void testUpdate() {
        String sql = "insert into student(name,sex) values(?,?)";
        jdbcTemplate.update(sql, new Object[]{"张飞", "男"});
    }

    @org.junit.Test
    public void testUpdate2() {
        String sql = "update student set sex=? where id=?";
        jdbcTemplate.update(sql, "女", 1003);
    }

    @org.junit.Test
    public void testBatchUpdate() {
        String[] sqls = {
                "insert into student(name,sex) values('关羽','男')",
                "insert into student(name,sex) values('貂蝉','女')",
                "update student set sex='男' where id=1003"
        };
        jdbcTemplate.batchUpdate(sqls);
    }

    // 同构一次性插入多条
    @org.junit.Test
    public void testBatchUpdate2() {
        String sql = "insert into selection(student,course) values(?,?)";
        List<Object[]> list = new ArrayList<Object[]>();
        list.add(new Object[]{1005, 1001});
        list.add(new Object[]{1005, 1003});
        jdbcTemplate.batchUpdate(sql, list);
    }

    // 查询一个简单数据项
    @org.junit.Test
    public void testQuerySimple1() {
        String sql = "select count(*) from student";
        int count = jdbcTemplate.queryForObject(sql, Integer.class);
        System.out.println(count);
    }

    // 查询多个简单数据项
    @org.junit.Test
    public void testQuerySimple2() {
        String sql = "select name from student where sex=?";
        List<String> names = jdbcTemplate.queryForList(sql, String.class, "男");
        System.out.println(names);
    }

    // 查询一个复杂数据项(封装为Map)
    @org.junit.Test
    public void testQueryMap1() {
        String sql = "select * from student where id=?";
        Map<String, Object> stu = jdbcTemplate.queryForMap(sql, 1003);
        System.out.println(stu);
    }

    // 查询多个复杂数据项(封装为Map)
    @org.junit.Test
    public void testQueryMap2() {
        String sql = "select * from student";
        List<Map<String, Object>> students = jdbcTemplate.queryForList(sql);
        System.out.println(students);
    }

    //查询一个复杂对象（封装为实体对象）
    @org.junit.Test
    public void testQueryEntity1() {
        String sql = "select * from student where id=?";
        Student stu = jdbcTemplate.queryForObject(sql, new RowMapper<Student>() {
            public Student mapRow(ResultSet resultSet, int i) throws SQLException {
                //配置映射关系
                Student stu = new Student();
                stu.setId(resultSet.getInt("id"));
                stu.setName(resultSet.getString("name"));
                stu.setSex(resultSet.getString("sex"));
                stu.setBorn(resultSet.getDate("born"));
                return stu;
            }
        }, 1004);
        System.out.println(stu);
    }

    // 查询多个复杂对象（封装为实体对象）
    @org.junit.Test
    public void testQueryEntity2() {
        String sql = "select * from student";
        List<Student> students = jdbcTemplate.query(sql,new StudentRowMapper());
        System.out.println(students);
    }

    private class StudentRowMapper implements RowMapper<Student>{
        public Student mapRow(ResultSet resultSet, int i) throws SQLException {
            Student stu = new Student();
            stu.setId(resultSet.getInt("id"));
            stu.setName(resultSet.getString("name"));
            stu.setSex(resultSet.getString("sex"));
            stu.setBorn(resultSet.getDate("born"));
            return stu;
        }
    }


}