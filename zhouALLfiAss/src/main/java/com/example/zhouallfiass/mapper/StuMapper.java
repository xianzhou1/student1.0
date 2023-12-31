package com.example.zhouallfiass.mapper;


import com.example.zhouallfiass.pojo.Stu;
import com.example.zhouallfiass.pojo.user;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StuMapper {
//    查询目前数据库所有的数据进行渲染
    @Select("select * from stu")
    List<Stu> list();

//    根据stuId查询
    @Select("select * from stu where stuId=#{id}")
    List<Stu> listOne(String id);

//    模糊查询信息
    List<Stu> selectAll(String stuName,String stuId,String stuAcd,String stuClass);


//    批量删除
    void deleteAny(List<String> ids);

//    分页显示
    @Select("select * from stu limit #{start},#{nums}")
    List<Stu> limitList(Integer start,Integer nums);

    @Select("select * from stu where stuClass=#{stuClass} and stuId=#{stuId}")
    List<Stu> selectStudent(String stuClass, String stuId);

    @Select("select * from user where userName=#{name} and id=#{id}")
    List<user> selectMan(String name, String id);

//    修改信息
    @SuppressWarnings({"all"})
    @Update("update stu " +
            "set stuName=#{stuName}," +
            "stuId=#{stuId}, stuAcd=#{stuAcd}," +
            "stuClass=#{stuClass}, " +
            "age=#{age}," +
            "nomClass=#{nomClass} where stuId=#{stuId}")
    void updateOne(Stu stu);

//    添加数据
    void insertOne(Stu stu);

//    添加用户
    @Insert("insert into user values(#{userName},#{password},#{id})")
    void insertUser(user user);

//    查询已经登录注册管理员的账号
    @Select("select * from user where userName=#{userName}")
    List<user> selectUser(String userName);

//    当超级管理员登录时显示目前管理员的账号
    @Select("select * from user")
    List<user> selectUserAll();

//    根据身份进行查询
    @Select("select * from user where id=#{id}")
    List<user> selectUserId(String id);

    //根据用户名进行删除
    @Delete("delete from user where userName=#{userName}")
    void deleteUser(String userName);

    @Select("select * from user where userName=#{userName} and password=#{password}")
    List<user> UserExist(String userName, String password);

}
