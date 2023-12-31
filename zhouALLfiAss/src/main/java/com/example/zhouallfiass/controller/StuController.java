package com.example.zhouallfiass.controller;


import com.example.zhouallfiass.pojo.Stu;
import com.example.zhouallfiass.pojo.user;
import com.example.zhouallfiass.service.StuService;
import com.example.zhouallfiass.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class StuController {

    @Autowired
    private StuService stuService;

    //  添加管理员信息
    @RequestMapping("/insertUser")
    public Result insertUser(user u){
        try {
            stuService.insertNewUser(u);
            return Result.success();
        } catch (Exception e) {
            return Result.error("添加失败");
        }
    }

    //   显示管理员的信息
    @RequestMapping("/showAllUser")
    public Result showAllUser(){
        try {
            List<user> users = stuService.selectUserAll();
            return Result.success(users);
        } catch (Exception e) {
            return Result.error("查询失败");
        }
    }

    //   根据管理员的用户名查询
    @RequestMapping("/selectUserName")
    public Result selectUserName(String userName){
        try {
            List<user> users = stuService.selectUserName(userName);
            return Result.success(users);
        } catch (Exception e) {
            return Result.error("查询失败");
        }
    }

    @RequestMapping("/isexiest")
    public Result isexiest(String name,String password){
        try {
            int i = stuService.UserExist(name, password);
            return Result.success(i);
        } catch (Exception e) {
            return Result.error("未知错误");
        }
    }

    //    根据管理员的身份查询
    @RequestMapping("/selectUserId")
    public Result selectUserId(String id){
        try {
            List<user> users = stuService.selectUserId(id);
            return Result.success(users);
        } catch (Exception e) {
            return Result.error("查询失败");
        }
    }

    @RequestMapping("/selectManager")
    public Result selectManager(String name, String id){
        List<user> users = stuService.selectMan(name, id);
        return Result.success(users);
    }

    //    删除管理员
    @RequestMapping("/deleteUserName")
    public Result deleteUserName(String name){
        try {
            stuService.deleteUserName(name);
            return Result.success();
        } catch (Exception e){
            return Result.error("删除失败");
        }
    }

    //   全部查询
    @RequestMapping("/selectShow")
    public Result show(){
        List<Stu> stus = stuService.getlistAll();
        return Result.success(stus);
    }

//   根据ID查询
    @RequestMapping("/selectId")
    public Result selectId(String id){
        List<Stu> stu = null;
        try {
            stu = stuService.getlistOne(id);
            return Result.success(stu);
        } catch (Exception e) {
            return Result.error("查询失败，数据格式不对");
        }

    }

//    分页查询
    @RequestMapping("/limitShow")
    public Result limitShow(Integer start, Integer nums){
        try {
            List<Stu> stus = stuService.getlimitShow(start, nums);
            return Result.success(stus);
        } catch (Exception e) {
            return Result.error("查询失败");
        }
    }

//   根据姓名进行模糊查询或者ID号或者所在学院或者所在班级进行一个查询
    @RequestMapping("/selectAll")
    public Result selectAll(String name,String id,String acd,String sClass){
        List<Stu> stu = null;
        try {
            stu = stuService.getlistLike(name,id,acd,sClass);
        } catch (Exception e) {
            return Result.error("查询失败！");
        }
        return Result.success(stu);
    }

//    查询学生账号
    @RequestMapping("/selectStudent")
    public Result selectStudent(String stuClass, String stuId){
        try {
            List<Stu> stus = stuService.selectStudent(stuClass, stuId);
            return Result.success(stus);
        } catch (Exception e) {
            return Result.error("查询出错");
        }
    }

//  根据Id批量删除
    @RequestMapping("/deleteAny")
    public Result deleteAny(String[] ids){
        List<String> list = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            list.add(ids[i]);
        }
        try {
            stuService.deleteSome(list);
            return Result.success();
        } catch (Exception e) {
            return Result.error("请求错误,数据错误");
        }
    }

//  添加数据
    @RequestMapping("/insertNew")
    public Result insertNew(Stu stu){
        try {
            stuService.insertInto(stu);
            return Result.success();
        } catch (Exception e) {
            return Result.error("添加失败,数据错误");
        }
    }

//    修改信息
    @RequestMapping("/updataNew")
    public Result updataNew(Stu stu){
        try {
            stuService.updateNew(stu);
            return Result.success();
        } catch (Exception e) {
            return Result.error("操作失败");
        }
    }

}
