package edu.ucsb.cs.cs184.gogogacho;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShowMajorElective extends AppCompatActivity {
    private FirebaseAuth auth;
    private DatabaseReference database;
    ExpandableListView expandableListView;
    List<String> listGroup;
    HashMap<String, List<Course>> listItem;
    CourseAdapter adapter;
    private User student;
    private String college;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activite_editmult);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        college = getIntent().getStringExtra("college");
        if(college.equals("College of Engineering")) {
            student = (COEstudent)getIntent().getSerializableExtra("student");
        }else if(college.equals("College of Creative Study")){
            student = (COEstudent)getIntent().getSerializableExtra("student");
        }else{
            student = (COEstudent)getIntent().getSerializableExtra("student");
        }

        initListData();

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                Course selected_course = (Course) adapter.getChild(groupPosition,childPosition);
                selected_course.setTaken(!selected_course.getTaken());
                if(selected_course.getTaken()){
                    v.setBackgroundColor(getColor(R.color.select));
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

    private void initListData(){

        listGroup = new ArrayList<>();
        listItem = new HashMap<>();
        listGroup.add("Major Elective");
        listGroup.add("Science Elective");
        listItem.put("Major Elective", ((COEstudent)student).getMajorElectiveCourses());
        listItem.put("Science Elective", ((COEstudent)student).getScienceElectiveCourses());
        adapter =  new CourseAdapter(this,listGroup,listItem);
        expandableListView = findViewById(R.id.expandable_ListView_edit);
        expandableListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
