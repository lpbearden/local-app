package edu.apsu.csci.local_app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.apsu.csci.local_app.Models.Locality;
import edu.apsu.csci.local_app.R;

/**
 * Created by PC on 4/30/2016.
 */
public class LocalAdapter extends ArrayAdapter<Locality> {
    private Context context;

    public LocalAdapter(Context context, ArrayList<Locality> localities) {
        super(context, 0, localities);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //get data item for this positions
        Locality locality = getItem(position);

        //check if a view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_locality, parent, false);
        }

        //Lookup view for data population
        TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
        TextView tv_description = (TextView) convertView.findViewById(R.id.tv_description);
        ImageView image_view = (ImageView) convertView.findViewById(R.id.location_imageView);

        String imageResource = locality.img_paths[0];
        int resID = context.getResources().getIdentifier(imageResource, "drawable", context.getPackageName());

        //populate data into textviews using the Locality object
        tv_name.setText(locality.name);
        tv_description.setText(locality.description);
        image_view.setImageResource(resID);

        //return the completed view
        return convertView;
    }



}
