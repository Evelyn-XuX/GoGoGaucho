package edu.ucsb.cs.cs184.gogogacho;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.ucsb.cs.cs184.gogogacho.ui.home.HomeFragment;

public class GEcourselistActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private DatabaseReference database;

    ExpandableListView expandableListView;
    List<String> listGroup;
    HashMap<String, List<Course>> listItem;
    CourseAdapter adapter;
    private User student;
    private String college;

    private Button next;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gelist);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
        next = findViewById(R.id.button4);
        next.setOnClickListener(task->nextHome());
        listGroup = new ArrayList<>();
        listItem = new HashMap<>();
        adapter =  new CourseAdapter(this,listGroup,listItem);
        expandableListView = findViewById(R.id.expandable_ListView_ge);
        expandableListView.setAdapter(adapter);

        college = getIntent().getStringExtra("college");
        if(college.equals("College of Engineering")) {
            student = (COEstudent)getIntent().getSerializableExtra("student");
        }else if(college.equals("College of Creative Study")){
            student = (COEstudent)getIntent().getSerializableExtra("student");
        }else{
            student = (COEstudent)getIntent().getSerializableExtra("student");
        }

        Log.v("read", student.getEmail());
        Log.v("read", String.valueOf( student.getMajorRequiredCourses().size()));

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
        student.mapListGroup_GE(listGroup);
        if(student.getCollege().equals("College of Engineering")) {
            getGEFromDB_COE();
        }else if(student.getCollege().equals("College of Creative Study")){
            getGEFromDB_CCS();
        }else{
            getGEFromDB_LS();
        }
        student.mapListItem_GE(listItem);
        adapter.notifyDataSetChanged();
    }

    void getGEFromDB_LS(){}
    void getGEFromDB_CCS(){}
    void getGEFromDB_COE(){
        List<Course> A1 = ((COEstudent)this.student).getGeAreaA1();
        List<Course> A2 = ((COEstudent)this.student).getGeAreaA2();
        List<Course> D = ((COEstudent)this.student).getGeAreaD();
        List<Course> E = ((COEstudent)this.student).getGeAreaE();
        List<Course> F = ((COEstudent)this.student).getGeAreaF();
        List<Course> G = ((COEstudent)this.student).getGeAreaG();

        database.child("GE").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Course obj;
                for(DataSnapshot t : snapshot.child("Area A1").getChildren()) {
                    obj = t.getValue(Course.class);
                    obj.setClassName(t.getKey());
                    A1.add(obj);
                }
                for(DataSnapshot t : snapshot.child("Area A2").getChildren()) {
                    obj = t.getValue(Course.class);
                    obj.setClassName(t.getKey());
                    A2.add(obj);
               }
                for(DataSnapshot t : snapshot.child("Area D").getChildren()) {
                    obj = t.getValue(Course.class);
                    obj.setClassName(t.getKey());
                    D.add(obj);
                }
                for(DataSnapshot t : snapshot.child("Area E").getChildren()) {
                    obj = t.getValue(Course.class);
                    obj.setClassName(t.getKey());
                    E.add(obj);
                }
                for(DataSnapshot t : snapshot.child("Area F").getChildren()) {
                    obj = t.getValue(Course.class);
                    obj.setClassName(t.getKey());
                    F.add(obj);
                }
                for(DataSnapshot t : snapshot.child("Area G").getChildren()) {
                    obj = t.getValue(Course.class);
                    obj.setClassName(t.getKey());
                    G.add(obj);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void nextHome(){
        Intent intent = new Intent(GEcourselistActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
