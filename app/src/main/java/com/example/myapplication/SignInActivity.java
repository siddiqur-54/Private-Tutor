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

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText signInEmailEditText,signInPasswordEditText;
    private Button signInButton,signUpHereButton,myProfileButton;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        setTitle("SIGN IN");

        mAuth = FirebaseAuth.getInstance();

        signInButton=(Button)findViewById(R.id.signInButtonId);
        signUpHereButton=(Button)findViewById(R.id.signUpHereButtonId);
        signInEmailEditText=(EditText) findViewById(R.id.signInEmailEditTextId);
        myProfileButton=(Button)findViewById(R.id.myProfileButtonId);
        signInPasswordEditText=(EditText)findViewById(R.id.signInPasswordEditTextId);
        progressBar=findViewById(R.id.progressBarId);

        signInButton.setOnClickListener(this);
        signUpHereButton.setOnClickListener(this);
        myProfileButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.signInButtonId)
        {
            userLogin();
        }
        else if(view.getId()==R.id.signUpHereButtonId)
        {
            Intent intent=new Intent(getApplicationContext(),SignUpActivity.class);
            startActivity(intent);
        }
        else if(view.getId()==R.id.myProfileButtonId)
        {
            if(mAuth.getCurrentUser()!=null)
            {
                Intent intent=new Intent(getApplicationContext(),ProfileActivity.class);
                startActivity(intent);
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Sign in first",Toast.LENGTH_LONG).show();
            }
        }
    }

    public void userLogin()
    {
        String email=signInEmailEditText.getText().toString().trim();
        String password=signInPasswordEditText.getText().toString().trim();

        if(email.isEmpty())
        {
            signInEmailEditText.setError("Please enter an email");
            signInEmailEditText.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            signInEmailEditText.setError("Please eneter a valid email");
            signInEmailEditText.requestFocus();
            return;
        }
        if(password.length()<5)
        {
            signInPasswordEditText.setError("passwrod length minimum 5 characters");
            signInPasswordEditText.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if(task.isSuccessful())
                        {
                            finish();
                            Intent intent=new Intent(getApplicationContext(),ProfileActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Login unsuccessful",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
