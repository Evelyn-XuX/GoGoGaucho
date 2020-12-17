package edu.ucsb.cs.cs184.gogogacho;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User implements Serializable{

    private final static int unit = 182;
    public String email;
    public String major;
    public String college;
    public List<Course> majorRequiredCourses;
    public List<Course> majorElectiveCourses;

    public User() {
        this.email = "";
        major = "";
        college = "";
        majorRequiredCourses = new ArrayList<>();
        majorElectiveCourses = new ArrayList<>();
    }

    public User(String email){
        this.email = email;
        this.college = "";
        major="";
        majorRequiredCourses = new ArrayList<>();
        majorElectiveCourses = new ArrayList<>();
    }

    public User(String email, String college ,String major, List<Course> majorRequiredCourses, List<Course> majorElectiveCourses){
        this.email = email;
        this.college = college;
        this.major = major;
        this.majorRequiredCourses = majorRequiredCourses;
        this.majorElectiveCourses = majorElectiveCourses;
    }

    public void setEmail(String email) { this.email = email;}
    public void setCollege(String college) { this.college = college; }
    public void setMajor(String major){
        this.major = major;
    }
    public void setMajorRequiredCourses(List<Course> list){
        majorRequiredCourses = list;
    }
    public void setMajorElectiveCourses(List<Course> list){
        majorElectiveCourses = list;
    }

    public String getEmail(){return this.email;}
    public String getCollege(){return this.college;}
    public String getMajor(){return this.major;}
    public List<Course> getMajorRequiredCourses(){return majorRequiredCourses;}
    public List<Course> getMajorElectiveCourses(){return majorElectiveCourses;}

    public void mapListGroup(List<String> listGroup){}
    public void mapListGroup_GE(List<String> listGroup){}
    public void mapListItem(HashMap<String, List<Course>> listItem){}
    public void mapListItem_GE(HashMap<String, List<Course>> listItem){}
    public boolean noCourse(){return this.majorRequiredCourses.size() == 0;}

    public String requiredCourseCompleteness(){return "";}
    public String electiveCourseCompleteness(){return "";}
    public String getTotalUnit(){return "";}

}
