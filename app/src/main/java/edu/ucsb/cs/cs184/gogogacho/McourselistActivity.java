package edu.ucsb.cs.cs184.gogogacho;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class McourselistActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private DatabaseReference database;

    private ArrayList<String> mMajorCourse;

    ExpandableListView expandableListView;
    List<String> listGroup;
    HashMap<String, List<String>> listItem;
    MainAdapter adapter;

    ArrayAdapter<String> arrayAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mjcourselist);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        expandableListView = findViewById(R.id.expandable_ListView_majorCourse);
        listGroup = new ArrayList<>();
        listItem = new HashMap<>();
        mMajorCourse = new ArrayList<>();
        adapter =  new MainAdapter(this,listGroup,listItem);
        expandableListView.setAdapter(adapter);

        initListData();

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                /*
                    TODO: 1. allow user have multiple choices, and stored their choices as String into a List;
                          2. Set up a button to indicate user have already make their decisions:
                            by clicking the "next" button, store the List into current user's Firebase
                 */
                
                final String selected_major = (String) adapter.getChild(groupPosition,childPosition);
                Log.d("Inside major course list",selected_major);
                Intent intent = new Intent(McourselistActivity.this, GEcourselistActivity.class);
                startActivity(intent);

                return true;
            }
        });

    }

    private void initListData(){
        listGroup.add(getString(R.string.require));
        listGroup.add(getString(R.string.elective));
        listGroup.add(getString(R.string.science));


        /*
            TODO:
                  1. Get the major of current user from Firebase   (We only have CS required course_list on Firebase so far)
                  2. Fetch the required course_list of this major from Firebase
         */

        FirebaseUser user = auth.getCurrentUser();
        String userId = user.getUid();


        String[] array;

        //store the names of major require courses
        List<String> list1 = new ArrayList<>();
        array = getResources().getStringArray(R.array.c1);
        for(String item : array){
            list1.add(item);
        }


        //store the names of major elective course
        List<String> list2 = new ArrayList<>();
        array = getResources().getStringArray(R.array.c2);
        for(String item : array){
            list2.add(item);
        }

        //store the names of science elective
        //List<String> list3 = new ArrayList<>();

        listItem.put(listGroup.get(0),list1);
        listItem.put(listGroup.get(1),list2);
        listItem.put(listGroup.get(2),list1);
        adapter.notifyDataSetChanged();


    }
}
