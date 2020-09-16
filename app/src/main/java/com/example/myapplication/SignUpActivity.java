package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText signUpEmail,signUpPassword;
    private Button signUpButton;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    DatabaseReference databaseReference;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle("SIGN UP");

        mAuth = FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressBarId);

        signUpEmail=(EditText)findViewById(R.id.signUpEmailId);
        signUpPassword=(EditText)findViewById(R.id.signUpPasswordId);
        signUpButton=(Button)findViewById(R.id.signUpButtonId);

        signUpButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.signUpButtonId)
        {
            userRegister();
        }
    }

    public void userRegister()
    {
        String email=signUpEmail.getText().toString().trim();
        String password=signUpPassword.getText().toString().trim();

        if(email.isEmpty())
        {
            signUpEmail.setError("Please enter an email");
            signUpEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            signUpEmail.setError("Please enter a valid email");
            signUpEmail.requestFocus();
            return;
        }
        if(password.length()<6)
        {
            signUpPassword.setError("password length minimum 5 characters");
            signUpPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task){
                        progressBar.setVisibility(View.GONE);
                        if(task.isSuccessful())
                        {
                            saveTutor();
                            Toast.makeText(getApplicationContext(),"Sign Up is successful",Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(getApplicationContext(),VerifyActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            if(task.getException() instanceof FirebaseAuthUserCollisionException)
                            {
                                Toast.makeText(getApplicationContext(),"User is already registered",Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Sign Up is unsuccessful",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

    public void saveTutor()
    {
        Tutor tutor=new Tutor("","","","","","");

        Post post=new Post("","","","","","","","","","");

        uid=mAuth.getCurrentUser().getUid();
        databaseReference=FirebaseDatabase.getInstance().getReference("tutor");

        databaseReference.child(uid).setValue(tutor);
        Toast.makeText(getApplicationContext(),"Tutor is added",Toast.LENGTH_LONG).show();

        databaseReference=FirebaseDatabase.getInstance().getReference("tuition");

        databaseReference.child(uid).setValue(post);
    }
}