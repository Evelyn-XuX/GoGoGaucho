package edu.ucsb.cs.cs184.gogogacho;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


//Used to display major list
//Divide into 3 college: ccs, coe, ls

public class MajorActivity extends AppCompatActivity {
    ExpandableListView expandableListView;
    List<String> listGroup;
    HashMap<String, List<String>> listItem;
    MainAdapter adapter;

    List<String> CatList;

    private FirebaseAuth auth;
    private DatabaseReference database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_majorselect);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        expandableListView = findViewById(R.id.expandable_ListView);
        listGroup = new ArrayList<>();
        listItem = new HashMap<>();
        CatList = new ArrayList<>();
        adapter =  new MainAdapter(this,listGroup,listItem);
        expandableListView.setAdapter(adapter);
        initListData();

         /*
            1. Set the child of ExpandableListView Clickable
            2. Store the selected major into Firebase for current user
         */
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                /*
                    TODO: (low priority) change background color when click a specific major
                 */
                final String selected_major = (String) adapter.getChild(groupPosition,childPosition);
                Log.d("selected major",selected_major);

                FirebaseUser user = auth.getCurrentUser();
                String email = user.getEmail();
                database.child("users").child(user.getUid()).child("major").setValue(selected_major);

                Intent intent = new Intent(MajorActivity.this,McourselistActivity.class);
                startActivity(intent);

                return true;
            }
        });

    }

    private void initListData(){
        listGroup.add(getString(R.string.college1));
        listGroup.add(getString(R.string.college2));
        listGroup.add(getString(R.string.college3));

        String[] array;

        List<String> list1 = new ArrayList<>();
        //store the names of department in CCS into list1
        array = getResources().getStringArray(R.array.c1);
        for(String item : array){
            list1.add(item);
        }


        List<String> list2 = new ArrayList<>();
        //store the names of department in COE into list2
        array = getResources().getStringArray(R.array.c2);
        for(String item : array){
            list2.add(item);
        }


        /*
        TODO: import the names of departments in college of letter and science
              （strings.xml里手动输入？）
         */
        List<String> list3 = new ArrayList<>();



        listItem.put(listGroup.get(0),list1);
        listItem.put(listGroup.get(1),list2);
        listItem.put(listGroup.get(2),list1);

        adapter.notifyDataSetChanged();

    }
}
