package com.android.iab.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.iab.R;
import com.android.iab.bean.SDK_Bean;

import java.util.ArrayList;


/** An array adapter that knows how to render views when given CustomData classes */
public class SdkListAdapter extends ArrayAdapter<SDK_Bean>  {
    private LayoutInflater mInflater;

    ArrayList<SDK_Bean> entries;
    Activity mActivity;

    ArrayList<SDK_Bean> array_list_vehicale=new ArrayList<SDK_Bean>();


    public SdkListAdapter(Activity context, ArrayList<SDK_Bean> values) {
        super(context, R.layout.sdk_items, values);
        mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        entries=values;

        mActivity=context;

    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Holder holder;

        if (convertView == null) {
            // Inflate the view since it does not exist
            convertView = mInflater.inflate(R.layout.sdk_items, parent, false);
            holder = new Holder();
            // Create and save off the holder in the tag so we get quick access to inner fields
            // This must be done for performance reasons
       holder.Name=(TextView) convertView.findViewById(R.id.name);

            holder.version=(TextView) convertView.findViewById(R.id.version);
            holder.selectIcon=(TextView) convertView.findViewById(R.id.selectIcon);

            holder.Name.setText(entries.get(position).getSdkName());
            holder.version.setText(entries.get(position).getSdkversion());






            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }


        return convertView;
    }



    /** View holder for the views we need access to */
    private static class Holder {
        TextView Name;
        TextView version;
        TextView   selectIcon;


    }




}
