package com.example.poo_p2_g01_android.ControladorReporte;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.poo_p2_g01_android.R;

import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> groupList;
    private List<List<String>> childItemLists;

    public ExpandableListAdapter(Context context, List<String> groupList, List<List<String>> childItemLists) {
        this.context = context;
        this.groupList = groupList;
        this.childItemLists = childItemLists;
    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return childItemLists.get(i).size();
    }

    @Override
    public Object getGroup(int i) {
        return groupList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return childItemLists.get(i).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String groupTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandable_group_list, parent, false);
        }
        TextView textView = convertView.findViewById(R.id.group_text);
        textView.setTextColor(ContextCompat.getColor(parent.getContext(), R.color.md_theme_onBackground));
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText(groupTitle);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String childTitle = (String) getChild(groupPosition, childPosition);
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandable_child_list, parent, false);
        }
        TextView textView = convertView.findViewById(R.id.child_text);
        textView.setTextColor(ContextCompat.getColor(parent.getContext(), R.color.md_theme_inverseSurface));
        textView.setText(childTitle);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
