package edu.ucsb.cs.cs184.gogogacho;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText emailText;
    private EditText passwordText;
    private Button signupButton;
    private Button loginButton;
    private Button googleLogin;
    private DatabaseReference database;

    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 123;

    private int exist = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailText = findViewById(R.id.email_edt_text);
        passwordText = findViewById(R.id.pass_edt_text);
        signupButton = findViewById(R.id.signup_btn);
        loginButton = findViewById(R.id.login_btn);
        googleLogin = findViewById(R.id.button2);


        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        loginButton.setOnClickListener(task->login());
        signupButton.setOnClickListener(task->doSwitch());

        createRequest();

        googleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });


    }

    private void createRequest(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    private void doSwitch(){
        Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            Log.d("task id", task.getResult().getId());
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }

    //login with google
    private void firebaseAuthWithGoogle(String idToken) {
        Log.d("idToken",idToken);

        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                             /*
                            TODO: check if this google account already exit on FireBase
                                -yes, switch to MainActivity directly
                                -no, store the information into FireBase, and switch to MajorActivity to select major

                            */

                            FirebaseUser user = task.getResult().getUser();
                            String email = user.getEmail();
                            Boolean result = isExist(user.getUid());
                            Log.d("isExist",Boolean.toString(result));


                            if(result){
                                //this Google account already store in firebase
                                // Sign in success, update UI with the signed-in user's information
                                Context context = getApplicationContext();
                                CharSequence text = "Successfully Sign In";
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);

                            }else{
                                //this Google account haven't store in firebase yet-->new User
                                writeNewUser(user.getUid(),email);
                                Intent intent = new Intent(LoginActivity.this, MajorActivity.class);
                                startActivity(intent);
                            }
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Context context = getApplicationContext();
                            CharSequence text = "Google Sign In Fail";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }
                    }
                });



    }



    private boolean isExist(String userId){
        final boolean[] result = {false};
        database.child("users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    // use "username" already exists
                    result[0] = true;
                } else {
                    // User does not exist. NOW call createUserWithEmailAndPassword
                    result[0] = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return result[0];
    }


    private void writeNewUser(String userId, String email){
        User newUser = new User(email);
        database.child("users").child(userId).setValue(newUser);
    }


    private void login(){
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
            auth.signInWithEmailAndPassword(email, pw)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Context context = getApplicationContext();
                                CharSequence text = "Successfully Sign In";
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();

                            } else {
                                // If sign in fails, display a message to the user.
                                Context context = getApplicationContext();
                                CharSequence text = "Sign In Fail";
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                            }
                        }
                    });
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = auth.getCurrentUser();
    }




}
