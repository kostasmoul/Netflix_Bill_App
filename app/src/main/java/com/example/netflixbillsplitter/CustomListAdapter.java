package com.example.netflixbillsplitter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomListAdapter extends ArrayAdapter {
    //to reference the Activity
    private final Activity context;
    private final List nameArray;

    public CustomListAdapter(Activity context, List nameArrayParam){

        super(context,R.layout.list_row , nameArrayParam);
        this.context=context;
        this.nameArray = nameArrayParam;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.list_row, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView nameTextField = (TextView) rowView.findViewById(R.id.nameTextViewID);
        //ImageView img = (ImageView) rowView.findViewById(R.id.photo);

        //this code sets the values of the objects to values from the arrays
        nameTextField.setText(nameArray.get(position).toString());
        //img.setImageResource(R.drawable.user2);


        return rowView;

    };


}
