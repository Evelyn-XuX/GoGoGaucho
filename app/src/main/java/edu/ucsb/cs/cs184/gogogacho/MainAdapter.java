package edu.ucsb.cs.cs184.gogogacho;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;

public class MainAdapter extends BaseExpandableListAdapter {

    Context context;
    List<String> listGroup;
    HashMap<String,List<String>> listItem;

    private FirebaseAuth auth;
    private DatabaseReference database;

    public MainAdapter(Context context, List<String> listGroup,HashMap<String, List<String>> listItem){
        this.context=context;
        this.listGroup=listGroup;
        this.listItem=listItem;
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

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
        String child = (String) getChild(groupPosition,childPosition);
        if(convertView==null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item, null);
        }

        TextView textView = convertView.findViewById(R.id.list_child);
        textView.setText(child);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = auth.getCurrentUser();
                String email = user.getEmail();
                database.child("users").child(user.getUid()).child("major").setValue(child);
                Intent intent = new Intent(context, McourselistActivity.class);
                context.startActivities(new Intent[]{intent});

            }
        });

        return convertView;

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
