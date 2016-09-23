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

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iab.test.iab.R;
import com.iab.test.iab.model.NavDrawerItem;
import com.iab.test.iab.utility.GlobalInstance;

import java.util.Collections;
import java.util.List;

/**This Class is an Adapter which is used to display List of Creative
 * */
public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.MyNavViewHolder> {
    /**
     * Fields which are used in this Class
     *
     * @param creativeItems                         This is a Collection ArrayList having  NavDrawerItem type Bean
     * @param isEdit                                This is a String which tell Edit Option is on or Off
     **/
    List<NavDrawerItem> creativeItems = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;
    public boolean isEdit;

    public NavigationDrawerAdapter(Context context, List<NavDrawerItem> data, boolean isEdit) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.creativeItems = data;
        this.isEdit = isEdit;
    }

    /**
     * Called by RecyclerView to display the data at the specified position
     */
    @Override
    public MyNavViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.nav_drawer_row, parent, false);
        MyNavViewHolder holder = new MyNavViewHolder(view);
        return holder;
    }

    /**
     * This method calls to create a new
     * ViewHolder and initializes some private fields to be used by RecyclerView.
     */
    @Override
    public void onBindViewHolder(MyNavViewHolder holder, int position) {
        NavDrawerItem current = creativeItems.get(position);
        holder.title.setText(current.getTitle());
        if (isEdit && !creativeItems.get(position).getIsDeleted().equals(GlobalInstance.TYPE_DEFAULT_CREATIVE))
            holder.delete_iconImageView.setVisibility(View.VISIBLE);
        else
            holder.delete_iconImageView.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return creativeItems.size();
    }


    class MyNavViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView delete_iconImageView;

        public MyNavViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            delete_iconImageView = (ImageView) itemView.findViewById(R.id.delete_iconImageView);
        }
    }

    public void setNotifyDataSetChanged(List<NavDrawerItem> navDrawerItems) {
               this.creativeItems = navDrawerItems;
        notifyDataSetChanged();
    }
}