package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private Button findTuitionButton,findTutorButton,postTuitionButton
            ,contactButton,editProfileButton,myPostButton,signOutButton;
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
        signOutButton=(Button)findViewById(R.id.signOutButtonId);
        contactButton=(Button)findViewById(R.id.contactButtonId);
        editProfileButton=(Button)findViewById(R.id.editProfileButtonId);
        myPostButton=(Button)findViewById(R.id.myPostButtonId);

        findTuitionButton.setOnClickListener(this);
        findTutorButton.setOnClickListener(this);
        postTuitionButton.setOnClickListener(this);
        contactButton.setOnClickListener(this);
        editProfileButton.setOnClickListener(this);
        myPostButton.setOnClickListener(this);
        signOutButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.findTuitionButtonId)
        {
            Intent intent=new Intent(getApplicationContext(),FindTuitionActivity.class);
            startActivity(intent);
        }
        else if(view.getId()==R.id.signOutButtonId)
        {
            mAuth.signOut();
            Toast.makeText(getApplicationContext(),"Signed Out Successfully",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
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
        else if(view.getId()==R.id.myPostButtonId)
        {
            Intent intent=new Intent(getApplicationContext(),MyPostActivity.class);
            startActivity(intent);
        }
    }
}
