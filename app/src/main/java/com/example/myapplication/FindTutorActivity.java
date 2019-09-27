package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FindTutorActivity extends AppCompatActivity implements View.OnClickListener{
    private ListView listViewTutor;
    DatabaseReference databaseReference;
    private List<Tutor> tutorList;
    private CustomAdapterTutor customAdapterTutor;
    private Button searchTutorButton;
    private EditText searchTutorInstitutionEditText,searchTutorSubjectEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_tutor);
        setTitle("TUTORS LIST");

        searchTutorButton=(Button)findViewById(R.id.searchTutorButtonId);
        searchTutorInstitutionEditText=(EditText)findViewById(R.id.searchTutorInstitutionEditTextId);
        searchTutorSubjectEditText=(EditText)findViewById(R.id.searchTutorSubjectEditTextId) ;

        databaseReference = FirebaseDatabase.getInstance().getReference("Tutors");
        tutorList = new ArrayList<>();
        customAdapterTutor = new CustomAdapterTutor(FindTutorActivity.this, tutorList);
        listViewTutor = findViewById(R.id.listViewTutorId);

        searchTutorButton.setOnClickListener(this);
    }
    public void onStart(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    Tutor tutor=dataSnapshot1.getValue(Tutor.class);
                    tutorList.add(tutor);
                }

                listViewTutor.setAdapter(customAdapterTutor);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        super.onStart();
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.searchTutorButtonId)
        {
            Intent intent=new Intent(getApplicationContext(),SearchTutorActivity.class);
            intent.putExtra("searchValueInstitution",searchTutorInstitutionEditText.getText().toString().trim());
            intent.putExtra("searchValueSubject",searchTutorSubjectEditText.getText().toString().trim());
            startActivity(intent);
            tutorList.clear();
        }
    }
}