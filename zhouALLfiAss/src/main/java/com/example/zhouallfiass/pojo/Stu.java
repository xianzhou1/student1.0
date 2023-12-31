package com.example.zhouallfiass.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class Stu {
    private String stuName;
    private String stuId;
    private String stuAcd;
    private String stuClass;
    private Integer age;
    private String nomClass;

    public Stu() {
    }

    public Stu(String stuName, String stuId, String stuAcd, String stuClass, Integer age, String nomClass) {
        this.stuName = stuName;
        this.stuId = stuId;
        this.stuAcd = stuAcd;
        this.stuClass = stuClass;
        this.age = age;
        this.nomClass = nomClass;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getStuAcd() {
        return stuAcd;
    }

    public void setStuAcd(String stuAcd) {
        this.stuAcd = stuAcd;
    }

    public String getStuClass() {
        return stuClass;
    }

    public void setStuClass(String stuClass) {
        this.stuClass = stuClass;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getNomClass() {
        return nomClass;
    }

    public void setNomClass(String nomClass) {
        this.nomClass = nomClass;
    }
}
