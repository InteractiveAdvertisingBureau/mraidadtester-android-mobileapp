package com.android.iab.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by pinky on 7/10/2015.
 */

import com.android.iab.R;
import com.android.iab.bean.Creatives_List_Bean;

import java.util.ArrayList;



public class CreativeListAdapter extends BaseAdapter{

    ArrayList<Creatives_List_Bean>  _creativeList;
    Context _mContext;

    public CreativeListAdapter(Context c, ArrayList<Creatives_List_Bean> creativeList)
    {
        _creativeList = creativeList;
        _mContext = c;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if (v == null)
        {
            LayoutInflater vi = (LayoutInflater)_mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.nav_drawer_row,null);
        }


        TextView textview_modelname=(TextView)v.findViewById(R.id.textView1);


        Creatives_List_Bean entry=_creativeList.get(position);
        textview_modelname.setText(entry.getCreativeName());




        return v;

    }
}
