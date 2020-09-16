package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editName,editInstitution,editClass,editContact,editSubject,editSalary;
    private Button updateButton;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    DatabaseReference databaseReference;
    String uid,name,ins,cla,sub,sal,con,pName,pEmail,pDate,pDes,pCla,pSub,pDay,pSal,pAdd,pCon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mAuth=FirebaseAuth.getInstance();

        editName=(EditText)findViewById(R.id.editNameId);
        editInstitution=(EditText)findViewById(R.id.editInstitutionId);
        editClass=(EditText)findViewById(R.id.editClassId);
        editContact=(EditText)findViewById(R.id.editContactId);
        editSubject=(EditText)findViewById(R.id.editSubjectId);
        editSalary=(EditText)findViewById(R.id.editSalaryId);
        updateButton=(Button)findViewById(R.id.updateButtonId);

        updateButton.setOnClickListener(this);
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
                    ins=tutor.getInstitution();
                    cla=tutor.getClasses();
                    sub=tutor.getSubject();
                    sal=tutor.getSalary();
                    con=tutor.getContact();

                    editName.setText(name);
                    editInstitution.setText(ins);
                    editClass.setText(cla);
                    editSubject.setText(sub);
                    editSalary.setText(sal);
                    editContact.setText(con);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
        });

        databaseReference= FirebaseDatabase.getInstance().getReference("tuition").child(uid);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Post post=dataSnapshot.getValue(Post.class);

                pName=post.getName();
                pEmail=post.getEmail();
                pDate=post.getDate();
                pDes=post.getDescription();
                pCla=post.getClasses();
                pSub=post.getSubjects();
                pDay=post.getDays();
                pSal=post.getSalary();
                pAdd=post.getAddress();
                pCon=post.getContact();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        super.onResume();
    }


    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.updateButtonId)
        {
            name=editName.getText().toString().trim();
            ins=editInstitution.getText().toString().trim();
            cla=editClass.getText().toString();
            sub=editSubject.getText().toString();
            sal=editSalary.getText().toString().trim();
            con=editContact.getText().toString().trim();

            int access=check(name,ins,cla,sub,sal,con);

            if(access==1){
                Tutor tutor=new Tutor(name,ins,cla,sub,sal,con);
                Post post=new Post(name,pEmail,pDate,pDes,pCla,pSub,pDay,pSal,pAdd,pCon);

                databaseReference=FirebaseDatabase.getInstance().getReference("tutor");
                databaseReference.child(uid).setValue(tutor);
                Toast.makeText(getApplicationContext(),"Profile is Updated",Toast.LENGTH_LONG).show();

                databaseReference=FirebaseDatabase.getInstance().getReference("tuition");
                databaseReference.child(uid).setValue(post);

                Intent intent=new Intent(getApplicationContext(),ProfileActivity.class);
                startActivity(intent);
            }
        }
    }

    public  int check(String name,String ins,String cla,String sub,String sal,String con)
    {
        if(name.isEmpty())
        {
            editName.setError("Please enter your name");
            editName.requestFocus();
            return 0;
        }
        if(ins.isEmpty())
        {
            editInstitution.setError("Please enter your institution");
            editInstitution.requestFocus();
            return 0;
        }
        if(cla.isEmpty())
        {
            editClass.setError("Please enter your class");
            editClass.requestFocus();
            return 0;
        }
        if(sub.isEmpty())
        {
            editSubject.setError("Please enter your subject");
            editSubject.requestFocus();
            return 0;
        }
        if(sal.isEmpty())
        {
            editSalary.setError("Please enter your salary");
            editSalary.requestFocus();
            return 0;
        }
        if(con.isEmpty())
        {
            editContact.setError("Please enter your contact");
            editContact.requestFocus();
            return 0;
        }
        return 1;
    }
}
