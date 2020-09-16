package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class MyPostActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView emptyPost,namePost,emailPost,datePost,desPost,classPost,
            subjectPost,dayPost,salaryPost,addressPost,contactPost;
    private Button deletePost;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    String uid,name,email,date,des,cla,sub,day,sal,add,con,postName,postEmail,
            postDate,postDescription,postClass,postSubject,postDay,postSalary,postAddress,postContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post);

        emptyPost=(TextView)findViewById(R.id.emptyPostId);
        namePost=(TextView)findViewById(R.id.namePostId);
        emailPost=(TextView)findViewById(R.id.emailPostId);
        datePost=(TextView)findViewById(R.id.datePostId);
        desPost=(TextView)findViewById(R.id.desPostId);
        classPost=(TextView)findViewById(R.id.classTextViewId);
        subjectPost=(TextView)findViewById(R.id.subjectTextViewId);
        dayPost=(TextView)findViewById(R.id.dayPostId);
        salaryPost=(TextView)findViewById(R.id.salaryTextViewId);
        addressPost=(TextView)findViewById(R.id.addressPostId);
        contactPost=(TextView)findViewById(R.id.contactTextViewId);

        mAuth=FirebaseAuth.getInstance();

        deletePost=(Button)findViewById(R.id.deletePostButtonId);

        deletePost.setVisibility(View.INVISIBLE);
        deletePost.setOnClickListener(this);
    }

    @Override
    protected void onResume() {

        uid=mAuth.getCurrentUser().getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference("tuition").child(uid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Post post=dataSnapshot.getValue(Post.class);
                name=post.getName();
                email=post.getEmail();
                date=post.getDate();
                des=post.getDescription();
                cla=post.getClasses();
                sub=post.getSubjects();
                day=post.getDays();
                sal=post.getSalary();
                add=post.getAddress();
                con=post.getContact();

                if(des!="")
                {
                    deletePost.setVisibility(View.VISIBLE);
                    namePost.setText(name);
                    emailPost.setText(email);
                    datePost.setText(date);
                    desPost.setText(des);
                    classPost.setText(cla);
                    subjectPost.setText(sub);
                    dayPost.setText(day);
                    salaryPost.setText(sal);
                    addressPost.setText(add);
                    contactPost.setText(con);
                }
                else
                {
                    emailPost.setText("No Post Available");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.deletePostButtonId)
        {


            postName="";
            postEmail="";
            postDate="";
            postDescription="";
            postClass="";
            postSubject="";
            postDay="";
            postSalary="";
            postAddress="";
            postContact="";

            Post post=new Post(postName,postEmail,postDate,postDescription,postClass,postSubject,postDay,postSalary,postAddress,postContact);

            uid=mAuth.getCurrentUser().getUid();

            databaseReference=FirebaseDatabase.getInstance().getReference("tuition");
            databaseReference.child(uid).setValue(post);
            Toast.makeText(getApplicationContext(),"Post is Deleted",Toast.LENGTH_LONG).show();

            deletePost.setVisibility(View.INVISIBLE);

            Intent intent=new Intent(getApplicationContext(),ProfileActivity.class);
            startActivity(intent);
        }
    }
}
