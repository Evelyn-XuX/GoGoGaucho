package edu.ucsb.cs.cs184.gogogacho;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;

public class CourseAdapter extends BaseExpandableListAdapter {

        Context context;
        List<String> listGroup;
        HashMap<String,List<Course>> listItem;

        public CourseAdapter(Context context, List<String> listGroup,HashMap<String, List<Course>> listItem){
            this.context=context;
            this.listGroup=listGroup;
            this.listItem=listItem;
        }

        @Override
        public int getGroupCount() {
            return listGroup.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return this.listItem.get(this.listGroup.get(groupPosition)).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return this.listGroup.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return this.listItem.get(this.listGroup.get(groupPosition))
                    .get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            String group = (String) getGroup(groupPosition);
            if(convertView==null){
                LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.list_group,null);
            }

            TextView textView = convertView.findViewById(R.id.list_parent);
            textView.setText(group);
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            Course child = (Course) getChild(groupPosition,childPosition);
            if(convertView==null){
                LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.list_item, null);
            }

            TextView textView = convertView.findViewById(R.id.list_child);

            if(child.getTaken()){
                convertView.setBackgroundColor(Color.parseColor("#EAECF5"));
            }else{
                convertView.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
            }
            textView.setText(child.getClassName());

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }