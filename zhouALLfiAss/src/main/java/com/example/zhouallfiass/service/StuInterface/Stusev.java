package com.example.zhouallfiass.service.StuInterface;


import com.example.zhouallfiass.pojo.Stu;
import com.example.zhouallfiass.pojo.user;

import java.util.List;

public interface Stusev {

    List<Stu> getlistAll();

    List<Stu> getlistOne(String id);

    List<Stu> getlistLike(String stuName,String stuId,String stuAcd,String stuClass);

    List<Stu> getlimitShow(Integer start, Integer nums);

    void deleteSome(List<String> ids);

    List<Stu> selectStudent(String stuName, String stuId);

    List<user> selectMan(String name, String id);

    void insertInto(Stu stu);

    void updateNew(Stu stu);

//    添加管理者
    void insertNewUser(user u);

//    查询全部
    List<user> selectUserAll();

//    根据身份查询
    List<user> selectUserId(String id);

//    根据用户名查询
    List<user> selectUserName(String name);

//    根据用户名删除
    void deleteUserName(String name);

    int UserExist(String name,String password);

}
