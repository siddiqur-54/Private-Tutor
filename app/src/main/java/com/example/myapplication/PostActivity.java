package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PostActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText postDescriptionEditText,postClassEditText,postSubjectEditText,postDaysEditText,
            postSalaryEditText,postAddressEditText,postContactEditText;
    private Button postButton;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    FirebaseUser user;
    String uid,name,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        setTitle("POST TUITION");

        mAuth=FirebaseAuth.getInstance();

        postDescriptionEditText=(EditText)findViewById(R.id.postDescriptionEditTextId);
        postClassEditText=(EditText)findViewById(R.id.postClassEditTextId);
        postSubjectEditText=(EditText)findViewById(R.id.postSubjectEditTextId);
        postDaysEditText=(EditText)findViewById(R.id.postDaysEditTextId);
        postSalaryEditText=(EditText)findViewById(R.id.postSalaryEditTextId);
        postAddressEditText=(EditText)findViewById(R.id.postAddressEditTextId);
        postContactEditText=(EditText)findViewById(R.id.postContactEditTextId);
        postButton=(Button)findViewById(R.id.postButtonId);

        postButton.setVisibility(View.INVISIBLE);
        postButton.setOnClickListener(this);
    }

    @Override
    protected void onResume() {

        uid=mAuth.getCurrentUser().getUid();

        databaseReference=FirebaseDatabase.getInstance().getReference("tutor").child(uid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Tutor tutor=dataSnapshot.getValue(Tutor.class);
                name=tutor.getName();

                if(name.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Please set up your profile first",Toast.LENGTH_LONG).show();
                    finish();
                    Intent intent=new Intent(getApplicationContext(),ProfileActivity.class);
                    startActivity(intent);
                }
                else
                {
                    postButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        super.onResume();
    }

    public void onClick(View view) {
        if(view.getId()==R.id.postButtonId)
        {
            savePost();
        }
    }

    public void savePost()
    {
        String description=postDescriptionEditText.getText().toString().trim();
        String classes=postClassEditText.getText().toString().trim();
        String subject=postSubjectEditText.getText().toString().trim();
        String days=postDaysEditText.getText().toString().trim();
        String salary=postSalaryEditText.getText().toString().trim();
        String address=postAddressEditText.getText().toString().trim();
        String contact=postContactEditText.getText().toString().trim();

        int access=check(description,classes,subject,days,salary,address,contact);
        if(access==1)
        {
            uid=mAuth.getCurrentUser().getUid();
            databaseReference=FirebaseDatabase.getInstance().getReference("tuition");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd--HH:mm:ss", Locale.getDefault());
            String date = sdf.format(new Date());

            user=mAuth.getCurrentUser();
            email=user.getEmail();

            Post post=new Post(name,email,date,description,classes,subject,days,salary,address,contact);

            databaseReference.child(uid).setValue(post);
            Toast.makeText(getApplicationContext(),"Post is added",Toast.LENGTH_LONG).show();

            Intent intent=new Intent(getApplicationContext(),ProfileActivity.class);
            startActivity(intent);
        }
    }


    public int check(String description,String classes,String subject,String days,String salary,
                      String address,String contact)
    {
        if(description.isEmpty())
        {
            postDescriptionEditText.setError("Please enter description");
            postDescriptionEditText.requestFocus();
            return 0;
        }
        if(classes.isEmpty())
        {
            postClassEditText.setError("Please enter class");
            postClassEditText.requestFocus();
            return 0;
        }
        if(subject.isEmpty())
        {
            postSubjectEditText.setError("Please enter subject");
            postSubjectEditText.requestFocus();
            return 0;
        }
        if(days.isEmpty())
        {
            postDaysEditText.setError("Please enter days");
            postDaysEditText.requestFocus();
            return 0;
        }
        if(salary.isEmpty())
        {
            postSalaryEditText.setError("Please enter salary");
            postSalaryEditText.requestFocus();
            return 0;
        }
        if(address.isEmpty())
        {
            postAddressEditText.setError("Please enter address");
            postAddressEditText.requestFocus();
            return 0;
        }
        if(contact.isEmpty())
        {
            postContactEditText.setError("Please enter contact no.");
            postContactEditText.requestFocus();
            return 0;
        }
        return 1;
    }
}
