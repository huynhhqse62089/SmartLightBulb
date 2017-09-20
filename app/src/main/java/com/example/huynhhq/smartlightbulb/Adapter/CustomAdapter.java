package com.example.huynhhq.smartlightbulb.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huynhhq.smartlightbulb.MainActivity;
import com.example.huynhhq.smartlightbulb.Model.SpinnerModel;
import com.example.huynhhq.smartlightbulb.R;

import java.util.ArrayList;

/**
 * Created by HuynhHQ on 9/14/2017.
 */

public class CustomAdapter extends ArrayAdapter {
    private Activity activity;
    private ArrayList data;
    public Resources res;
    SpinnerModel tempValues = null;
    LayoutInflater inflater;

    public CustomAdapter(
            MainActivity activitySpin,
            int textViewResourceId,
            ArrayList objects,
            Resources resLocal
    ) {
        super(activitySpin, textViewResourceId, objects);
        activity = activitySpin;
        data = objects;
        res = resLocal;

        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getDropDownView(int position, View convertView,ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }


    @Override
    public View getView(int position, @Nullable View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }


    public View getCustomView(int position, View convertView, ViewGroup parent) {
        View row = inflater.inflate(R.layout.spinner_rows, parent, false);

        tempValues = null;
        tempValues = (SpinnerModel) data.get(position);

        TextView label = (TextView) row.findViewById(R.id.language);
        ImageView flagCountry = (ImageView) row.findViewById(R.id.flagImage);

        label.setText(tempValues.getLanguageName());
        flagCountry.setImageResource(res.getIdentifier("com.example.huynhhq.smartlightbulb:drawable/" + tempValues.getImage(), null, null));

        return row;
    }
}
