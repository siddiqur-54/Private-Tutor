package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button findTuitionButton,findTutorButton,postTuitionButton,
            helpButton,contactButton,registerButton;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("HOME PAGE");

        mAuth=FirebaseAuth.getInstance();

        findTuitionButton=(Button)findViewById(R.id.findTuitionButtonId);
        findTutorButton=(Button)findViewById(R.id.findTutorButtonId);
        postTuitionButton=(Button)findViewById(R.id.postTuitionButtonId);
        helpButton=(Button)findViewById(R.id.helpButtonId);
        contactButton=(Button)findViewById(R.id.contactButtonId);
        registerButton=(Button)findViewById(R.id.registerButtonId);

        findTuitionButton.setOnClickListener(this);
        findTutorButton.setOnClickListener(this);
        postTuitionButton.setOnClickListener(this);
        helpButton.setOnClickListener(this);
        contactButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.findTuitionButtonId)
        {
            Intent intent=new Intent(MainActivity.this,FindTuitionActivity.class);
            startActivity(intent);
        }
        else if(view.getId()==R.id.findTutorButtonId)
        {
            Intent intent=new Intent(MainActivity.this,FindTutorActivity.class);
            startActivity(intent);
        }
        else if(view.getId()==R.id.postTuitionButtonId)
        {
            if(mAuth.getCurrentUser()!=null)
            {
                Intent intent=new Intent(MainActivity.this,PostActivity.class);
                startActivity(intent);
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Sign in first",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(getApplicationContext(),SignInActivity.class);
                startActivity(intent);
            }
        }
        else if(view.getId()==R.id.helpButtonId)
        {
            Intent intent=new Intent(MainActivity.this,HelpActivity.class);
            startActivity(intent);
        }
        else if(view.getId()==R.id.contactButtonId)
        {
            Intent intent=new Intent(MainActivity.this,ContactActivity.class);
            startActivity(intent);
        }
        else if(view.getId()==R.id.registerButtonId)
        {
            Intent intent=new Intent(MainActivity.this,SignInActivity.class);
            startActivity(intent);
        }
    }
}
