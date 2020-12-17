package edu.ucsb.cs.cs184.gogogacho;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import edu.ucsb.cs.cs184.gogogacho.Course;

public class CourseEditAdapter extends ArrayAdapter<Course> {
    private int resourceId;
    public CourseEditAdapter(Context context, int resourceId, List<Course> CourseList){
        super(context, resourceId,CourseList);
        this.resourceId = resourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Course course = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView tvcourse = view.findViewById(R.id.courseitem);

        tvcourse.setText(course.getClassName());

        if(course.getTaken()){
            view.setBackgroundColor(Color.parseColor("#EAECF5"));
        }else{
            view.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        }
        return view;
    }
}
