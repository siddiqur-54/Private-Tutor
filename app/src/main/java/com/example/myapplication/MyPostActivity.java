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
    String uid,des,postName,postEmail,
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
        classPost=(TextView)findViewById(R.id.classPostId);
        subjectPost=(TextView)findViewById(R.id.subjectPostId);
        dayPost=(TextView)findViewById(R.id.dayPostId);
        salaryPost=(TextView)findViewById(R.id.salaryPostId);
        addressPost=(TextView)findViewById(R.id.addressPostId);
        contactPost=(TextView)findViewById(R.id.contactPostId);

        mAuth=FirebaseAuth.getInstance();

        deletePost=(Button)findViewById(R.id.deletePostButtonId);

        deletePost.setVisibility(View.INVISIBLE);
        deletePost.setOnClickListener(this);
    }

    @Override
    protected void onResume() {

        uid=mAuth.getCurrentUser().getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference("tuition").child(uid);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Post post=dataSnapshot.getValue(Post.class);

                if(!post.getDescription().isEmpty())
                {
                    deletePost.setVisibility(View.VISIBLE);

                    namePost.setText(post.getName());
                    emailPost.setText(post.getEmail());
                    datePost.setText(post.getDate());
                    desPost.setText(post.getDescription());
                    classPost.setText(post.getClasses());
                    subjectPost.setText(post.getSubjects());
                    dayPost.setText(post.getDays());
                    salaryPost.setText(post.getSalary());
                    addressPost.setText(post.getAddress());
                    contactPost.setText(post.getContact());
                }
                else
                {
                    emptyPost.setText("No Post Available");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        /*databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Post post=dataSnapshot.getValue(Post.class);

                if(post.getDescription()!="")
                {
                    deletePost.setVisibility(View.VISIBLE);

                    namePost.setText(post.getName());
                    emailPost.setText(post.getEmail());
                    datePost.setText(post.getDate());
                    desPost.setText(post.getDescription());
                    classPost.setText(post.getClasses());
                    subjectPost.setText(post.getSubjects());
                    dayPost.setText(post.getDays());
                    salaryPost.setText(post.getSalary());
                    addressPost.setText(post.getAddress());
                    contactPost.setText(post.getContact());
                }
                else
                {
                    emptyPost.setText("No Post Available");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });*/
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
