package com.android.iab.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.iab.R;
import com.android.iab.bean.Ad_Type_Bean;
import com.android.iab.bean.SDK_Bean;
import com.android.iab.helper.HelperMethods;

import java.util.ArrayList;


/**
 * AdType Adapter for rendering views i.e. ad type
 *
 * @author Syed
 * @version 2015.
 * @since 1.0
 */

public class AdTypeAdapter extends ArrayAdapter<Ad_Type_Bean>  {
    private LayoutInflater mInflater;

    ArrayList<Ad_Type_Bean> entries;
    Activity mActivity;

    /*
     *    Ad_Type_Bean for storing  ad type list
     */
    ArrayList<Ad_Type_Bean> array_list_ad_type=new ArrayList<Ad_Type_Bean>();

    public static String AD_TYPE_TAG ="Ad_Type";
    /**
     * SharedPreferences object to store coomon sharable data.
     */
    SharedPreferences mPrefs;

    public AdTypeAdapter(Activity context, ArrayList<Ad_Type_Bean> values) {
        super(context, R.layout.ad_type_items, values);
        mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        entries=values;

        mActivity=context;

        mPrefs= mActivity.getSharedPreferences(HelperMethods.MODE_TYPE, Context.MODE_PRIVATE);
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final Holder holder;

        if (convertView == null) {
            // Inflate the view since it does not exist
            convertView = mInflater.inflate(R.layout.ad_type_items, parent, false);
            holder = new Holder();
            // Create and save off the holder in the tag so we get quick access to inner fields
            // This must be done for performance reasons

            holder.Type=(TextView) convertView.findViewById(R.id.name);

            holder.selectIcon=(TextView) convertView.findViewById(R.id.selectIcon);

            holder.Type.setText(entries.get(position).getAdtype());

            convertView.setTag(holder);
        }

        else {
            holder = (Holder) convertView.getTag();
        }


        /*
        * click listner event for  ad type item
        * */
        convertView.findViewById(R.id.selectIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SharedPreferences.Editor editor = mPrefs.edit();
                editor.putInt(AD_TYPE_TAG,  position);
                editor.commit();

                Log.e("Selected Item", "" + entries.get(position).getAdtype());



            }
        });


        return convertView;
    }





    /** View holder for the views we need access to */
    private static class Holder {

        TextView Type;
        TextView   selectIcon;


    }




}
