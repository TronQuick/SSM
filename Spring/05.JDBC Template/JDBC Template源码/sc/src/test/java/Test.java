import com.imooc.sc.entity.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Test {
    private JdbcTemplate jdbcTemplate;
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        jdbcTemplate = (JdbcTemplate)context.getBean("jdbcTemplate");
    }

    public void testExecute(){
        jdbcTemplate.execute("create table user1(id int,name varchar(20))");
    }

    public void testUpdate(){
        String sql = "insert into student(name,sex) values(?,?)";
        jdbcTemplate.update(sql,new Object[]{"张飞","男"});
    }

    public void testUpdate2(){
        String sql = "update student set sex=? where id=?";
        jdbcTemplate.update(sql,"女",1003);
    }

    public void testBatchUpdate(){
        String[] sqls={
                "insert into student(name,sex) values('关羽','女')",
                "insert into student(name,sex) values('刘备','男')",
                "update student set sex='女' where id=2001"
        };
        jdbcTemplate.batchUpdate(sqls);
    }

    public void testBatchUpdate2(){
        String sql = "insert into selection(student,course) values(?,?)";
        List<Object[]> list = new ArrayList<Object[]>();
        list.add(new Object[]{1005,1001});
        list.add(new Object[]{1005,1003});
        jdbcTemplate.batchUpdate(sql,list);
    }

    public void testQuerySimple1(){
        String sql = "select count(*) from student";
        int count = jdbcTemplate.queryForObject(sql,Integer.class);
        System.out.println(count);
    }

    public void testQuerySimple2(){
        String sql = "select name from student where sex=?";
        List<String> names = jdbcTemplate.queryForList(sql,String.class,"女");
        System.out.println(names);
    }

    public void testQueryMap1(){
        String sql = "select * from student where id = ?";
        Map<String,Object> stu = jdbcTemplate.queryForMap(sql,1003);
        System.out.println(stu);
    }

    public void testQueryMap2(){
        String sql = "select * from student";
        List<Map<String,Object>> stus = jdbcTemplate.queryForList(sql);
        System.out.println(stus);
    }

    public void testQueryEntity1(){
        String sql = "select * from student where id = ?";
        Student stu = jdbcTemplate.queryForObject(sql, new StudentRowMapper(), 1004);
        System.out.println(stu);
    }
    @org.junit.Test
    public void testQueryEntity2(){
        String sql = "select * from student";
        List<Student> stus = jdbcTemplate.query(sql,new StudentRowMapper());
        System.out.println(stus);
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