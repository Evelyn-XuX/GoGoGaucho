package edu.ucsb.cs.cs184.gogogacho;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class COEstudent extends User {
    public List<Course> scienceElectiveCourses;
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
