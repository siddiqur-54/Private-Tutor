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

import static com.example.myapplication.R.id.searchTuitionClassEditTextId;

public class FindTuitionActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView listView;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    private List<Post> postList;
    private CustomAdapter customAdapter;
    private Button searchTuitionButton;
    private EditText searchTuitionClassEditText,searchTuitionSubjectEditText;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_tuition);
        setTitle("TUITION LIST");

        mAuth=FirebaseAuth.getInstance();

        searchTuitionButton=(Button)findViewById(R.id.searchTuitionButtonId);
        searchTuitionClassEditText=(EditText)findViewById(R.id.searchTuitionClassEditTextId);
        searchTuitionSubjectEditText=(EditText)findViewById(R.id.searchTuitionSubjectEditTextId);

        postList = new ArrayList<>();
        customAdapter = new CustomAdapter(FindTuitionActivity.this, postList);
        listView = findViewById(R.id.listViewId);

        searchTuitionButton.setOnClickListener(this);
    }
    public void onStart(){
        uid=mAuth.getCurrentUser().getUid();
        databaseReference=FirebaseDatabase.getInstance().getReference("tuition").child(uid);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    Post post=dataSnapshot1.getValue(Post.class);

                    if(post.getDescription()!="")
                    {
                        postList.add(post);
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

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.searchTuitionButtonId)
        {
            Intent intent=new Intent(getApplicationContext(),SearchTuitionActivity.class);
            intent.putExtra("searchValueClass",searchTuitionClassEditText.getText().toString().trim());
            intent.putExtra("searchValueSubject",searchTuitionSubjectEditText.getText().toString().trim());
            startActivity(intent);
            postList.clear();
        }
    }
}
