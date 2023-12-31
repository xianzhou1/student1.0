package com.example.zhouallfiass.service;


import com.example.zhouallfiass.mapper.StuMapper;
import com.example.zhouallfiass.pojo.Stu;
import com.example.zhouallfiass.pojo.user;
import com.example.zhouallfiass.service.StuInterface.Stusev;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StuService implements Stusev {

    @Autowired
    private StuMapper stuMapper;

//    返回全部的数据
    @Override
    public List<Stu> getlistAll() {
        List<Stu> list = stuMapper.list();
        return list;
    }
//    根据ID返回学生信息
    @Override
    public List<Stu> getlistOne(String id){
        List<Stu> list = stuMapper.listOne(id);
        return list;
    }

//    姓名，ID，所在学院，所在班级
    @Override
    public List<Stu> getlistLike(String stuName, String stuId, String stuAcd, String stuClass) {
        List<Stu> stus = stuMapper.selectAll(stuName, stuId, stuAcd, stuClass);
        return stus;
    }

//    分页查询
    @Override
    public List<Stu> getlimitShow(Integer start, Integer nums) {
        if(start == null)
            start = 0;
        List<Stu> stus = stuMapper.limitList(start, nums);
        return stus;
    }

    //    根据选择的Id号进行批量删除
    @Override
    public void deleteSome(List<String> ids) {
        stuMapper.deleteAny(ids);
    }

//    添加信息
    @Override
    public void insertInto(Stu stu) {
        List<Stu> stus = stuMapper.listOne(stu.getStuId());
        if(stus.size() > 0){
            throw new RuntimeException("主键不能重复！");
        }else {
            stuMapper.insertOne(stu);
        }
    }

//    修改信息
    @Override
    public void updateNew(Stu stu) {
        stuMapper.updateOne(stu);
    }

    @Override
    public List<user> selectMan(String name, String id) {
        List<user> users = stuMapper.selectMan(name, id);
        return users;
    }

    @Override
    public List<Stu> selectStudent(String stuName, String stuId) {
        List<Stu> stus = stuMapper.selectStudent(stuName, stuId);
        return stus;
    }

    //    添加管理者
    @Override
    public void insertNewUser(user u) {
        stuMapper.insertUser(u);
    }

//    根据ID查询
    @Override
    public List<user> selectUserId(String id) {
        List<user> list = stuMapper.selectUserId(id);
        return list;
    }

//    根据用户名查询
    @Override
    public List<user> selectUserName(String name) {
        List<user> list = stuMapper.selectUser(name);
        return list;
    }

//    删除管理者
    @Override
    public void deleteUserName(String name) {
        stuMapper.deleteUser(name);
    }

    //    查询全部
    @Override
    public List<user> selectUserAll() {
        return stuMapper.selectUserAll();
    }

    @Override
    public int UserExist(String name, String password) {
        List<user> list = stuMapper.UserExist(name,password);
        return list.size();
    }
}
