package edu.ucsb.cs.cs184.gogogacho;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_majorselect);

        expandableListView = findViewById(R.id.expandable_ListView);
        listGroup = new ArrayList<>();
        listItem = new HashMap<>();
        CatList = new ArrayList<>();
        adapter =  new MainAdapter(this,listGroup,listItem);
        expandableListView.setAdapter(adapter);
         initListData();
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



        listItem.put(listGroup.get(0),list1);
        listItem.put(listGroup.get(1),list2);
        listItem.put(listGroup.get(2),list1);
        adapter.notifyDataSetChanged();

        expandableListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("item click","true");
                Log.d("position", Integer.toString(position));
                Log.d("id", Long.toString(id));
            }
        });


    }
}
