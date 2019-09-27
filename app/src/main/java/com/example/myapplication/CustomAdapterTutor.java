package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import static com.example.myapplication.R.layout.sampletutor_layout;

public class CustomAdapterTutor extends ArrayAdapter<Tutor> {

    private Activity context;
    private List<Tutor> tutorList;

    public CustomAdapterTutor(Activity context,List<Tutor> tutorList) {
        super(context,R.layout.sampletutor_layout,tutorList);
        this.context = context;
        this.tutorList = tutorList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater=context.getLayoutInflater();
        View view=layoutInflater.inflate(sampletutor_layout,null,true);

        Tutor tutor=tutorList.get(position);

        TextView tv1=view.findViewById(R.id.nameTextViewId);
        TextView tv2=view.findViewById(R.id.institutionTextViewId);
        TextView tv3=view.findViewById(R.id.classTextViewId);
        TextView tv4=view.findViewById(R.id.subjectTextViewId);
        TextView tv5=view.findViewById(R.id.salaryTextViewId);
        TextView tv6=view.findViewById(R.id.contactTextViewId);
        TextView tv7=view.findViewById(R.id.emailTextViewId);

        tv1.setText(tutor.getName());
        tv2.setText(tutor.getInstitution());
        tv3.setText(tutor.getClasses());
        tv4.setText(tutor.getSubject());
        tv5.setText(tutor.getSalary());
        tv6.setText(tutor.getContact());
        tv7.setText(tutor.getEmail());

        return view;
    }
}
