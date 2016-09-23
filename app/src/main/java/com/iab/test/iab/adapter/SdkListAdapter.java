/**
 * ****************************************************************************
 * Copyright (c) 2015, Interactive Advertising Bureau
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * <p>
 * ****************************************************************************
 */

package com.iab.test.iab.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.iab.test.iab.R;
import com.iab.test.iab.bean.SDKBean;
import com.iab.test.iab.main.MainActivity;
import com.iab.test.iab.utility.SharePref;

import java.util.ArrayList;

/**This Class is an Adapter which is used to display List of SDK
 * */
public class SdkListAdapter extends ArrayAdapter<SDKBean> {
    /**
     * Fields which are used in this Class
     *
     * @param entries                         This is a Collection ArrayList having  SDKBean type Bean
     * @param mActivity                       This is an instnace of Activity which is Pass by Activity
     * @param mPrefs                          This is a SharedPreferences object to store common sharable data.
     **/

    private LayoutInflater mInflater;
    ArrayList<SDKBean> entries;
    Activity mActivity;
    SharedPreferences mPrefs;

    public SdkListAdapter(Activity context, ArrayList<SDKBean> values) {
        super(context, R.layout.sdk_items, values);
        mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        entries = values;
        mActivity = context;
        mPrefs = mActivity.getSharedPreferences(SharePref.MODE_TYPE, Context.MODE_PRIVATE);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Holder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.sdk_items, parent, false);
            holder = new Holder();
            holder.Name = (TextView) convertView.findViewById(R.id.name);
            holder.version = (TextView) convertView.findViewById(R.id.version);
            holder.selectIcon = (TextView) convertView.findViewById(R.id.selectIcon);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.Name.setText(entries.get(position).getSdkName());
        holder.version.setText(entries.get(position).getSdkversion());
        if (entries.get(position).isSelected()) {
            holder.Name.setTextColor(mActivity.getResources().getColor(R.color.red));
            holder.selectIcon.setBackgroundResource(R.drawable.tick_red);
        } else {
            holder.Name.setTextColor(mActivity.getResources().getColor(R.color.black));
            holder.selectIcon.setBackgroundResource(R.drawable.tick_grey);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectSdk(position);
            }
        });
        return convertView;
    }

    /** View holder for the views we need access to */
    private static class Holder {
        TextView Name;
        TextView version;
        TextView selectIcon;
    }

    /**This methoed is used to Set SDK */
    public void selectSdk(int position) {
        for (int i = 0; i < entries.size(); i++) {
            entries.get(i).setIsSelected(false);
        }

        entries.get(position).setIsSelected(true);
        notifyDataSetChanged();
        MainActivity.getInstance().ResetAdType();
    }

    /**This method Check whic SDK is selected*/
    public SDKBean getSelectedSdk() {
        SDKBean sdk_bean = null;
        for (int i = 0; i < entries.size(); i++) {
            if (entries.get(i).isSelected()) {
                return entries.get(i);
            }
        }
        return sdk_bean;
    }

    public void setSelectedSdk(String sdkId) {
        for (int i = 0; i < entries.size(); i++) {
            if (!entries.get(i).getSdkName().equals(sdkId)) {
                entries.get(i).setIsSelected(false);
            } else {
                entries.get(i).setIsSelected(true);
            }
        }
        notifyDataSetChanged();
    }
}
