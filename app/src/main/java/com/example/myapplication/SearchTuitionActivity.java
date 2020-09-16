package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchTuitionActivity extends AppCompatActivity {
    private ListView listView;
    DatabaseReference databaseReference;
    private List<Post> postList;
    private CustomAdapter customAdapter;
    FirebaseAuth mAuth;
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_tuition);
        setTitle("TUITION LIST");

        mAuth=FirebaseAuth.getInstance();

        postList = new ArrayList<>();
        customAdapter = new CustomAdapter(SearchTuitionActivity.this, postList);
        listView = findViewById(R.id.listViewId);
    }
    public void onStart(){

        final String searchValueClass=getIntent().getStringExtra("searchValueClass");
        final String searchValueSubject=getIntent().getStringExtra("searchValueSubject");

        uid=mAuth.getCurrentUser().getUid();
        databaseReference=FirebaseDatabase.getInstance().getReference("tuition").child(uid);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    Post post=dataSnapshot1.getValue(Post.class);

                    if(post.getDescription()!="") {

                        String mainValueClass = post.getClasses();
                        String mainValueSubject = post.getSubjects();
                        if ((searchValueClass.compareToIgnoreCase(mainValueClass) == 0) && (searchValueSubject.compareToIgnoreCase(mainValueSubject) == 0)) {
                            postList.add(post);
                        } else if ((searchValueClass.compareToIgnoreCase(mainValueClass) == 0) && (searchValueSubject.isEmpty())) {
                            postList.add(post);
                        } else if ((searchValueSubject.compareToIgnoreCase(mainValueSubject) == 0) && (searchValueClass.isEmpty())) {
                            postList.add(post);
                        }
                    }
                }

                listView.setAdapter(customAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        super.onStart();
    }
}

