package edu.ucsb.cs.cs184.gogogacho;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth auth;
    private Context context;
    private DatabaseReference database;

    private User student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        context =this;
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        if(auth.getCurrentUser()==null){
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
            finish();
        }else{
            Context c = getApplicationContext();
            CharSequence text = "Already Sign In";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(c, text, duration);
            toast.show();
        }

        database.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String userId = auth.getCurrentUser().getUid();
                String college = snapshot.child(auth.getUid()).child("college").getValue(String.class);
                Log.v("read",college);
                if(college.equals("College of Engineering")) {
                    student = snapshot.child(userId).getValue(COEstudent.class);
                }else if(college.equals("College of Creative Study")){
                    student = snapshot.child(userId).getValue(User.class);
                }else if(college.equals("College of Letters and Science")){
                    student = snapshot.child(userId).getValue(User.class);
                }else{
                    User temp = snapshot.child(userId).getValue(User.class);
                }
                Log.v("read", student.getEmail());
                Log.v("read", student.majorRequiredCourses.get(0).toString());
                Log.v("read", String.valueOf(student.majorRequiredCourses.get(0).getTaken()));
                Log.v("read", student.majorRequiredCourses.get(1).toString());
                Log.v("read", String.valueOf(student.majorRequiredCourses.get(1).getTaken()));
                Log.v("read", student.majorRequiredCourses.get(2).toString());
                Log.v("read", String.valueOf(student.majorRequiredCourses.get(2).getTaken()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        Log.v("read", String.valueOf(student == null));
//        Log.v("read", student.getEmail());
//        Log.v("read", student.majorRequiredCourses.get(0).toString());
//        Log.v("read", String.valueOf(student.majorRequiredCourses.get(0).getTaken()));
//        Log.v("read", student.majorRequiredCourses.get(1).toString());
//        Log.v("read", String.valueOf(student.majorRequiredCourses.get(1).getTaken()));
//        Log.v("read", student.majorRequiredCourses.get(2).toString());
//        Log.v("read", String.valueOf(student.majorRequiredCourses.get(2).getTaken()));


        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                logout();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.logout_btn:
//                        Context context = getApplicationContext();
//                        CharSequence text = "Whyyy";
//                        int duration = Toast.LENGTH_SHORT;
//                        Toast toast = Toast.makeText(context, text, duration);
//                        toast.show();
//                        logout();
//                        return true;
//                    default:
//                        return MainActivity.super.onNavigateUp();
//                }
//            }
//        });

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.logout_btn)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    private void logout(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        //startActivity(intent);
        startActivityForResult(intent, 2);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2)
        {
            mGoogleSignInClient.signOut();
        }
    }


}