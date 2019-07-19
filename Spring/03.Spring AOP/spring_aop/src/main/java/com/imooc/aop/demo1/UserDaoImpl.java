package com.imooc.aop.demo1;

import com.imooc.aop.demo1.UserDao;

public class UserDaoImpl implements UserDao {

    public void save() {
        System.out.println("save...");
    }

    public void update() {
        System.out.println("update...");
    }

    public void delete() {
        System.out.println("delete...");
    }

    public void find() {
        System.out.println("find...");
    }
}
