package com.imooc.sm.dao;

import com.imooc.sm.entity.Log;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogDao {
    void insert(Log log);
    List<Log> selectByType(String type);
}
