package com.example.zhouallfiass;

import com.example.zhouallfiass.mapper.StuMapper;
import com.example.zhouallfiass.pojo.Stu;
import com.example.zhouallfiass.service.StuService;
import com.example.zhouallfiass.pojo.user;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ZhouAlLfiAssApplicationTests {

    @Autowired
    private StuMapper stuMapper;

//    查询管理者
    @Test
    void selectuser(){
        stuMapper.selectUserId("管理员");
    }

    @Test
    void select(){
        List<Stu> users = stuMapper.limitList(0,5);
        users.stream().forEach(a->{
            System.out.println(a);
        });
    }

    @Test
    void selectStudent(){
        List<Stu> stus = stuMapper.selectStudent("22级计算机一班", "22101201");
        stus.stream().forEach(a->{
            System.out.println(a);
        });
    }
//    删除管理者
    @Test
    void delete(){
        stuMapper.deleteUser("小陈");
    }

//    添加管理者
    @Test
    void insertUse(){

        List<user> tmp = stuMapper.UserExist("小周","123456");
        System.out.println(tmp.size());

    }

//    根据用户名查询
//    @Test
//    void selectName(){
//        int list = stuMapper.selectUser("小陈");
//        System.out.println(list);
//    }

//    @Test
//    void SelectAll() {
//        List<Stu> arr = stuMapper.list();
//        arr.stream().forEach(a -> {
//            System.out.println(a);
//        });
//    }

//    @Test
//    void select() {
//        List<Stu> list = stuMapper.selectAll("陈", null, null, null);
//        list.stream().forEach(a -> {
//            System.out.println(a);
//        });
//    }
//    @Test
//    void select(){
//        List<Stu> stus = stuMapper.selectAll("","","信息","");
//        stus.stream().forEach(a->{
//            System.out.println(a);
//        });
//    }

//    @Test
//    void delete() {
//        List<String> secend = new ArrayList<>();
//        secend.add("22101207");
//        try {
//            stuMapper.deleteAny(secend);
//        } catch (Exception e) {
//            System.out.println("删除失败");
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test
//    void insert() {
//        Stu stu = new Stu("陈文兴", "22101201", "信息与大数据", "22级计算机一班", 21, "计算机一班");
//        try {
//            stuMapper.insertOne(stu);
//        } catch (Exception e) {
//            System.out.println("插入失败");
//            throw new RuntimeException(e);
//        }
//
//    }
//
//    @Test
//    void update() {
//        Stu stu = new Stu("陈文兴", "1", "信息与大数据", "22级计算机一班", 21, "计算机一班");
//        try {
//            stuMapper.updateOne(stu);
//        } catch (Exception e) {
//            System.out.println("更新失败");
//            throw new RuntimeException(e);
//        }
//    }

}
