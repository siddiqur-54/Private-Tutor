package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PostActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText postDescriptionEditText,postClassEditText,postSubjectEditText,postDaysEditText,
            postSalaryEditText,postAddressEditText,postContactEditText;
    private Button postButton;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        setTitle("POST TUITION");

        databaseReference= FirebaseDatabase.getInstance().getReference("Posts");

        postDescriptionEditText=(EditText)findViewById(R.id.postDescriptionEditTextId);
        postClassEditText=(EditText)findViewById(R.id.postClassEditTextId);
        postSubjectEditText=(EditText)findViewById(R.id.postSubjectEditTextId);
        postDaysEditText=(EditText)findViewById(R.id.postDaysEditTextId);
        postSalaryEditText=(EditText)findViewById(R.id.postSalaryEditTextId);
        postAddressEditText=(EditText)findViewById(R.id.postAddressEditTextId);
        postContactEditText=(EditText)findViewById(R.id.postContactEditTextId);
        postButton=(Button)findViewById(R.id.postButtonId);

        postButton.setOnClickListener(this);
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
        String key=databaseReference.push().getKey();
        Post post=new Post(description,classes,subject,days,salary,address,contact);

        databaseReference.child(key).setValue(post);
        Toast.makeText(getApplicationContext(),"Post is added",Toast.LENGTH_LONG).show();
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
