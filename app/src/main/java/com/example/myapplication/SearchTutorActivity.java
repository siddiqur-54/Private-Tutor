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

public class SearchTutorActivity extends AppCompatActivity{
    private ListView listViewTutor;
    DatabaseReference databaseReference;
    private List<Tutor> tutorList;
    private CustomAdapterTutor customAdapterTutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_tutor);
        setTitle("TUTORS LIST");

        tutorList = new ArrayList<>();
        customAdapterTutor = new CustomAdapterTutor(SearchTutorActivity.this, tutorList);
        listViewTutor = findViewById(R.id.listViewTutorId);
    }
    public void onResume(){
        final String searchValueInstitution=getIntent().getStringExtra("searchValueInstitution");
        final String searchValueSubject=getIntent().getStringExtra("searchValueSubject");

        databaseReference=FirebaseDatabase.getInstance().getReference("tutor");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    Tutor tutor=dataSnapshot1.getValue(Tutor.class);

                    if(!tutor.getName().isEmpty()) {
                        String mainValueInstitution = tutor.getInstitution();
                        String mainValueSubject = tutor.getSubject();
                        if ((searchValueInstitution.compareToIgnoreCase(mainValueInstitution) == 0) && (searchValueSubject.compareToIgnoreCase(mainValueSubject) == 0)) {
                            tutorList.add(tutor);
                        } else if ((searchValueInstitution.compareToIgnoreCase(mainValueInstitution) == 0) && (searchValueSubject.isEmpty())) {
                            tutorList.add(tutor);
                        } else if ((searchValueSubject.compareToIgnoreCase(mainValueSubject) == 0) && (searchValueInstitution.isEmpty())) {
                            tutorList.add(tutor);
                        }
                    }
                }

                listViewTutor.setAdapter(customAdapterTutor);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        super.onResume();
    }
}