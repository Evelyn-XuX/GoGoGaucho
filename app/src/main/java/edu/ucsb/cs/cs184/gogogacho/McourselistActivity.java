package edu.ucsb.cs.cs184.gogogacho;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class McourselistActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private DatabaseReference database;

    private ArrayList<String> mMajorCourse;

    ExpandableListView expandableListView;
    List<String> listGroup;
    HashMap<String, List<Course>> listItem;
    CourseAdapter adapter;

    private User student;
    private Button next;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mjcourselist);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        expandableListView = findViewById(R.id.expandable_ListView_majorCourse);
        next = findViewById(R.id.button5);
        listGroup = new ArrayList<>();
        listItem = new HashMap<>();
        mMajorCourse = new ArrayList<>();
        adapter =  new CourseAdapter(this,listGroup,listItem);
        expandableListView.setAdapter(adapter);

        String major = getIntent().getStringExtra("major");
        String college = getIntent().getStringExtra("college");
        if (major.equals("Computer Science")){
            major = "CS";
        }

        if(college.equals("College of Engineering")) {
            student = new COEstudent();
        }else if(college.equals("College of Creative Study")){
            student = new User(); // CCSstudent();
        }else{
            student = new User(); // LSstudent();
        }

        student.setCollege(college);
        student.setMajor(major);

        initListData();
        next.setOnClickListener(task->nextGE());

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                /*
                    TODO: 1. allow user have multiple choices, and stored their choices as String into a List;
                          2. Set up a button to indicate user have already make their decisions:
                            by clicking the "next" button, store the List into current user's Firebase
                 */
                
                Course selected_course = (Course) adapter.getChild(groupPosition,childPosition);
                selected_course.setTaken(!selected_course.getTaken());
                next.setOnClickListener(task->nextGE());

                return true;
            }
        });

    }
    private void nextGE(){
        Intent intent = new Intent(McourselistActivity.this, GEcourselistActivity.class);
        startActivity(intent);
        finish();
    }


    private void initListData(){
        student.mapListGroup(listGroup);
        /*
            TODO:
                  1. Get the major of current user from Firebase   (We only have CS required course_list on Firebase so far)
                  2. Fetch the required course_list of this major from Firebase
         */

        FirebaseUser user = auth.getCurrentUser();
        String userId = user.getUid();
        if (student.noCourse()){
            if(student.getCollege().equals("College of Engineering")) {
                getCoursesFromDB_COE();
            }else if(student.getCollege().equals("College of Creative Study")){
                getCoursesFromDB_CCS();
            }else{
                getCoursesFromDB_LS();
            }
        }else{
            // read user info
        }

        student.mapListItem(listItem);
        adapter.notifyDataSetChanged();
    }

    void getCoursesFromDB_LS(){}
    void getCoursesFromDB_CCS(){}
    void getCoursesFromDB_COE(){
        List<Course> MajorRequired = this.student.getMajorRequiredCourses();
        List<Course> MajorElective = this.student.getMajorElectiveCourses();
        List<Course> ScienceElective = ((COEstudent)this.student).getScienceElectiveCourses();

        database.child(student.getMajor() + "_major").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Course obj;
                for(DataSnapshot t : snapshot.child(student.getMajor() +"_Required").getChildren()) {
                    for (DataSnapshot c : t.getChildren()) {
                        obj = c.getValue(Course.class);
                        MajorRequired.add(obj);
                    }
                }
                for(DataSnapshot t : snapshot.child(student.getMajor() + "_elective").getChildren()) {
                    for (DataSnapshot c : t.getChildren()) {
                        obj = c.getValue(Course.class);
                        MajorElective.add(obj);
                    }
                }
                for(DataSnapshot t : snapshot.child(student.getMajor() +"_Science_elective").getChildren()) {
                    for (DataSnapshot c : t.getChildren()) {
                        obj = c.getValue(Course.class);
                        ScienceElective.add(obj);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}