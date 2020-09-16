package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private Button findTuitionButton,findTutorButton,postTuitionButton,
            helpButton,contactButton,editProfileButton;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle("HOME PAGE");

        mAuth=FirebaseAuth.getInstance();

        findTuitionButton=(Button)findViewById(R.id.findTuitionButtonId);
        findTutorButton=(Button)findViewById(R.id.findTutorButtonId);
        postTuitionButton=(Button)findViewById(R.id.postTuitionButtonId);
        helpButton=(Button)findViewById(R.id.helpButtonId);
        contactButton=(Button)findViewById(R.id.contactButtonId);
        editProfileButton=(Button)findViewById(R.id.editProfileButtonId);

        findTuitionButton.setOnClickListener(this);
        findTutorButton.setOnClickListener(this);
        postTuitionButton.setOnClickListener(this);
        helpButton.setOnClickListener(this);
        contactButton.setOnClickListener(this);
        editProfileButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.findTuitionButtonId)
        {
            Intent intent=new Intent(getApplicationContext(),FindTuitionActivity.class);
            startActivity(intent);
        }
        else if(view.getId()==R.id.findTutorButtonId)
        {
            Intent intent=new Intent(getApplicationContext(),FindTutorActivity.class);
            startActivity(intent);
        }
        else if(view.getId()==R.id.postTuitionButtonId)
        {
            Intent intent=new Intent(getApplicationContext(),PostActivity.class);
            startActivity(intent);
        }
        else if(view.getId()==R.id.helpButtonId)
        {
            Intent intent=new Intent(getApplicationContext(),HelpActivity.class);
            startActivity(intent);
        }
        else if(view.getId()==R.id.contactButtonId)
        {
            Intent intent=new Intent(getApplicationContext(),ContactActivity.class);
            startActivity(intent);
        }
        else if(view.getId()==R.id.editProfileButtonId)
        {
            Intent intent=new Intent(getApplicationContext(),EditProfileActivity.class);
            startActivity(intent);
        }
    }
}
