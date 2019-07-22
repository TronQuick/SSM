package com.imooc.aspectJ.demo2;

public class CustomerDaoImpl  implements CustomerDao{
    public void save() {
        System.out.println("保存客户...");
    }

    public String update() {
        System.out.println("修改用户...");
        return "Spring";
    }

    public void delete() {
        System.out.println("删除用户...");
    }

    public void findOne() {
        System.out.println("查询一个用户...");
    }

    public void findAll() {
        System.out.println("查询所有用户...");
    }
}
