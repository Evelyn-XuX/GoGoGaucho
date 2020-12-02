package edu.ucsb.cs.cs184.gogogacho;

import android.app.AppComponentFactory;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

public class SignupActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private DatabaseReference database;
    private EditText emailText;
    private EditText passwordText;
    private Button signupButton;
    private Button loginButton;
    private Integer count=0;

    @IgnoreExtraProperties
    class User{
        String email = "";
        String password = "";

        public User(String email, String pw) {
            this.email = email;
            password = pw;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        emailText = findViewById(R.id.email_edt_text);
        passwordText = findViewById(R.id.pass_edt_text);
        signupButton = findViewById(R.id.signup_btn);
        loginButton = findViewById(R.id.login_btn);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        signupButton.setOnClickListener(task->signup());

        loginButton.setOnClickListener(task->doSwitch());




    }

    private void signup(){
        String email = emailText.getText().toString();
        String pw = passwordText.getText().toString();
        if(email.isEmpty()|| pw.isEmpty()){
            Context context = getApplicationContext();
            CharSequence text = "Please fill all the fields";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }else{
            // auth.signInWithEmailAndPassword(email,pw).addOnCompleteListener(this,login_check);
            auth.createUserWithEmailAndPassword(email, pw)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
//                                count++;
//                                String id = count.toString();
//                                writeNewUser(id,email,pw);
                                Context context = getApplicationContext();
                                CharSequence text = "Successfully Sign Up";
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();

                                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();

                            } else {
                                // If sign in fails, display a message to the user.
                                Context context = getApplicationContext();
                                CharSequence text = "Registration Fail";
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                            }
                        }
                    });
        }

    }

    private void writeNewUser(String userId, String email, String pw){
        User newuser = new User(email,pw);
        database.child("users").child(userId).setValue(newuser);
    }

    private void doSwitch(){
        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}