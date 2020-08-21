package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import models.Category;
import models.Venue;

/**
 * This is the Class that i create ExpandableListView in order to use to user all the requests from Call to Foursquare API
 */
class FoursquareExpandableListAdapter extends BaseExpandableListAdapter {
    Context context;
    List<Category> listGroup;
    HashMap<String,List<Venue>> listItem;

    public FoursquareExpandableListAdapter(Context context, List<Category> listGroup, HashMap<String,List<Venue>> listItem) {
        this.context = context;
        this.listGroup = listGroup;
        this.listItem = listItem;
    }

    @Override
    public int getGroupCount() {
        return listGroup.size();
    }


    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listItem.get(this.listGroup.get(groupPosition).getName()).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listGroup.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.listItem.get(this.listGroup.get(groupPosition).getName()).get(childPosition);
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

    /**
     * Here I say to ExpandableListView to set as Header title the Restaurant categories that i have called from Foursquare API which is inside a List of Category
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Category group = (Category) getGroup(groupPosition);
        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group,null);
        }
        TextView textView = convertView.findViewById(R.id.list_parent);
        /**
         * Here i create a variable which i pass the names of categories and below that i set it to textview in order to appear it on UI
         */
        String name = group.getName();
        textView.setText(name);
        /**
         * Here i create a variable which i pass the link for images from Foursquare Api and above that i pass it with Picasso library in imageview in order to appear it on UI
         */
        ImageView imageView = convertView.findViewById(R.id.list_parent_image);
        String image = group.getIcon().getPrefix() +"32"+ group.getIcon().getSuffix();
        Picasso.with(this.context).load(image).into(imageView);
        return convertView;
    }

    /**
     *  Here I say to ExpandableListView to set above the header (Restaurants Category) the Names of the Restaurants depending on the category of the restaurant
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Venue child = (Venue) getChild(groupPosition,childPosition);
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item,null);
        }

        TextView textView = convertView.findViewById(R.id.list_child);
        /**
         *  Here I create variable to take restaurant names
         */
        String name = child.getName();
        /**
         *  Here I create variable to take the distance from restaurants depends on location that user have and below that i convert it to specific format we want
         */
        Float distanc = child.getLocation().getDistance().floatValue()/1000;
        DecimalFormat f = new DecimalFormat("##,0");
        String distance = f.format(distanc)+" km";
        textView.setText(name);
        TextView textViewDistance = convertView.findViewById(R.id.list_child_distance);
        textViewDistance.setText(distance);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
