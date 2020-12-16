package edu.ucsb.cs.cs184.gogogacho;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import edu.ucsb.cs.cs184.gogogacho.ui.home.HomeFragment;

public class GEcourselistActivity extends AppCompatActivity {

    private Button next;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gelist);
        next = findViewById(R.id.button4);

        next.setOnClickListener(task->nextHome());



    }
    private void nextHome(){
        Intent intent = new Intent(GEcourselistActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
