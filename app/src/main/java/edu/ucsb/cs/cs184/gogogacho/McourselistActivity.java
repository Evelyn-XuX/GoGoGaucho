package edu.ucsb.cs.cs184.gogogacho;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

public class McourselistActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private DatabaseReference database;

    ExpandableListView expandableListView;
    List<String> listGroup;
    HashMap<String, List<String>> listItem;
    MainAdapter adapter;

    List<String> CatList;

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
    }

    private void initListData(){
        listGroup.add(getString(R.string.require));
        listGroup.add(getString(R.string.elective));
        listGroup.add(getString(R.string.science));

        FirebaseUser user = auth.getCurrentUser();


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



        listItem.put(listGroup.get(0),list1);
        listItem.put(listGroup.get(1),list2);
        listItem.put(listGroup.get(2),list1);
        adapter.notifyDataSetChanged();


    }
}
