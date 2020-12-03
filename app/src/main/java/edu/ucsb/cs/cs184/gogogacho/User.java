package edu.ucsb.cs.cs184.gogogacho;

import java.util.ArrayList;
import java.util.List;

public class User {

    public String email;
    public String major;
    public List<String> majorCourses;
    public List<String> geCourses;

    public User(){ }

    public User(String email){
        this.email = email;
        major="";
        List<String> mList = new ArrayList<>();
        List<String> geList = new ArrayList<>();
        majorCourses = mList;
        geCourses = geList;
    }

    public User(String email, String major, List<String> majorCourses, List<String> geCourses){
        this.email = email;
        this.major = major;
        this.majorCourses = majorCourses;
        this.geCourses = geCourses;
    }

    public void setMajor(String major){
        this.major = major;
    }

    public void setMajorCourseList(List<String> list){
        majorCourses = list;
    }

    public void setGECourseList(List<String> list){
        geCourses = list;
    }





}
