package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<Post> {

    private Activity context;
    private List<Post> postList;

    public CustomAdapter(Activity context,List<Post> postList) {
        super(context,R.layout.sample_layout,postList);
        this.context = context;
        this.postList = postList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater=context.getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.sample_layout,null,true);

        Post post=postList.get(position);

        TextView t1=view.findViewById(R.id.postDescriptionTextViewId);
        TextView t2=view.findViewById(R.id.postClassTextViewId);
        TextView t3=view.findViewById(R.id.postSubjectTextViewId);
        TextView t4=view.findViewById(R.id.postDaysTextViewId);
        TextView t5=view.findViewById(R.id.postSalaryTextViewId);
        TextView t6=view.findViewById(R.id.postAddressTextViewId);
        TextView t7=view.findViewById(R.id.postContactTextViewId);

        t1.setText(post.getDescription());
        t2.setText(post.getClasses());
        t3.setText(post.getSubject());
        t4.setText(post.getDays());
        t5.setText(post.getSalary());
        t6.setText(post.getAddress());
        t7.setText(post.getContact());

        return view;
    }
}
