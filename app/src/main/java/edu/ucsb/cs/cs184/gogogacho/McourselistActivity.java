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

    ExpandableListView expandableListView;
    List<String> listGroup;
    HashMap<String, List<Course>> listItem;
    CourseAdapter adapter;

    private String college;
    private User student;
    private Button next;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mjcourselist);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
        listGroup = new ArrayList<>();
        listItem = new HashMap<>();
        adapter =  new CourseAdapter(this,listGroup,listItem);
        expandableListView = findViewById(R.id.expandable_ListView_majorCourse);
        expandableListView.setAdapter(adapter);
        next = findViewById(R.id.button5);
        next.setOnClickListener(task->nextGE());

        String major = getIntent().getStringExtra("major");
        if (major.equals("Computer Science")){ major = "CS"; }
        college = getIntent().getStringExtra("college");

        if(college.equals("College of Engineering")) {
            student = new COEstudent();
        }else if(college.equals("College of Creative Study")){
            student = new User(); // CCSstudent();
        }else{
            student = new User(); // LSstudent();
        }
        student.setCollege(college);
        student.setMajor(major);
        student.setEmail(auth.getCurrentUser().getEmail());
        initListData();

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                
                Course selected_course = (Course) adapter.getChild(groupPosition,childPosition);
                selected_course.setTaken(!selected_course.getTaken());
                if(selected_course.getTaken()){
                    v.setBackgroundColor(getColor(R.color.purple_500));
                }else{
                    v.setBackgroundColor(getColor(R.color.white));
                }
                FirebaseUser user = auth.getCurrentUser();
                String userId = user.getUid();
                database.child("users").child(userId).setValue(student);

                return true;
            }
        });

    }
    private void nextGE(){
        FirebaseUser user = auth.getCurrentUser();
        String userId = user.getUid();
        database.child("users").child(userId).setValue(student);
        Intent intent = new Intent(McourselistActivity.this, GEcourselistActivity.class);
        intent.putExtra("college", college);
        Log.v("read","send student");
        intent.putExtra("student",(COEstudent)student);
        Log.v("read","sent");
//        if(college.equals("College of Engineering")) {
//            intent.putExtra("student",(COEstudent)student);
//        }else if(college.equals("College of Creative Study")){
//            intent.putExtra("student",(COEstudent)student);
//        }else{
//            intent.putExtra("student",(COEstudent)student);
//        }
        startActivity(intent);
        finish();
    }


    private void initListData(){
        student.mapListGroup(listGroup);
        if(student.getCollege().equals("College of Engineering")) {
            getCoursesFromDB_COE();
        }else if(student.getCollege().equals("College of Creative Study")){
            getCoursesFromDB_CCS();
        }else{
            getCoursesFromDB_LS();
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
                        obj.setClassName(c.getKey());
                        MajorRequired.add(obj);
                    }
                }
                for(DataSnapshot t : snapshot.child(student.getMajor() + "_elective").getChildren()) {
                    for (DataSnapshot c : t.getChildren()) {
                        obj = c.getValue(Course.class);
                        obj.setClassName(c.getKey());
                        MajorElective.add(obj);
                    }
                }
                for(DataSnapshot t : snapshot.child(student.getMajor() +"_Science_elective").getChildren()) {
                    for (DataSnapshot c : t.getChildren()) {
                        obj = c.getValue(Course.class);
                        obj.setClassName(c.getKey());
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