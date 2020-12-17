package edu.ucsb.cs.cs184.gogogacho;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AreaF extends AppCompatActivity {

    private FirebaseAuth auth;
    private DatabaseReference database;
    ListView listView;
    CourseEditAdapter adapter;
    private User student;
    private String college;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("track","D");
        setContentView(R.layout.activity_courseedit);
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

        initView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Course selected_course = (Course) adapter.getItem(position);
                selected_course.setTaken(!selected_course.getTaken());
                if(selected_course.getTaken()){
                    view.setBackgroundColor(getColor(R.color.select));
                }else{
                    view.setBackgroundColor(getColor(R.color.white));
                }
                FirebaseUser user = auth.getCurrentUser();
                String userId = user.getUid();
                database.child("users").child(userId).setValue(student);
            }
        });
    }

    private void initView(){
        listView = findViewById(R.id.ListView_edit);
        Log.v("track",String.valueOf(((COEstudent)student).getGeAreaD().size()));
        adapter =  new CourseEditAdapter(this,R.layout.course_item, ((COEstudent)student).getGeAreaF());
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
