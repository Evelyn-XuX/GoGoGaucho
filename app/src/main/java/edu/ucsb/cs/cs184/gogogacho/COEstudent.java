package edu.ucsb.cs.cs184.gogogacho;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class COEstudent extends User {
    //private static int majorRequiredUnit;
    private static int majorElective = 7;
    private static int scienceElective = 2;
    private static int areaA1 = 1;
    private static int areaA2 = 1;
    private static int areaD = 2;
    private static int areaE = 2;
    private static int areaF = 1;
    private static int areaG = 1;

    public List<Course> scienceElectiveCourses;

    public List<Course> geAreaA1;
    public List<Course> geAreaA2;
    public List<Course> geAreaD;
    public List<Course> geAreaE;
    public List<Course> geAreaF;
    public List<Course> geAreaG;

    public COEstudent(){
        super();
        scienceElectiveCourses = new ArrayList<>();
    }

    public COEstudent(String email, String college, String major, List<Course> majorRequiredCourses,
                      List<Course> majorElectiveCourses,
                      List<Course> geCourses,
                      List<Course> scienceElectiveCourses){
        super(email,college,major,majorRequiredCourses,majorElectiveCourses,geCourses);
        this.scienceElectiveCourses = scienceElectiveCourses;
    }

    public void setScienceElectiveCourses(List<Course> Courses){this.scienceElectiveCourses = Courses;}

    public void setGeAreaA1(List<Course> geAreaA1) { this.geAreaA1 = geAreaA1; }
    public void setGeAreaA2(List<Course> geAreaA2) { this.geAreaA2 = geAreaA2; }
    public void setGeAreaD(List<Course> geAreaD) { this.geAreaD = geAreaD; }
    public void setGeAreaE(List<Course> geAreaE) { this.geAreaE = geAreaE; }
    public void setGeAreaF(List<Course> geAreaF) { this.geAreaF = geAreaF; }
    public void setGeAreaG(List<Course> geAreaG) { this.geAreaG = geAreaG; }

    public List<Course> getGeAreaA1() { return geAreaA1; }
    public List<Course> getGeAreaA2() { return geAreaA2; }
    public List<Course> getGeAreaD() { return geAreaD; }
    public List<Course> getGeAreaE() { return geAreaE; }
    public List<Course> getGeAreaF() { return geAreaF; }
    public List<Course> getGeAreaG() { return geAreaG; }
    public List<Course> getScienceElectiveCourses(){return scienceElectiveCourses;}

    @Override
    public void mapListGroup(List<String> listGroup) {
        listGroup.add("Major Require");
        listGroup.add("Major Elective");
        listGroup.add("Science Elective");
    }

    @Override
    public void mapListItem(HashMap<String, List<Course>> listItem) {
        listItem.put("Major Require", this.majorRequiredCourses);
        listItem.put("Major Elective", this.majorElectiveCourses);
        listItem.put("Science Elective", this.scienceElectiveCourses);
    }
}
