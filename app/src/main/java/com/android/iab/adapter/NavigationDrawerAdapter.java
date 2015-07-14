package com.android.iab.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.iab.R;
import com.android.iab.model.NavDrawerItem;

import java.util.Collections;
import java.util.List;



/**
 * Navigation Draewr Adapter for loading layouts resources
 *
 * This activity is used to display navigation drawer.
 *
 * @author Syed
 * @version 2015.
 * @since 1.0
 */


public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.MyNavViewHolder> {
    List<NavDrawerItem> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;

    public NavigationDrawerAdapter(Context context, List<NavDrawerItem> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    public void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }


    /**
     * Called by RecyclerView to display the data at the specified position
     *
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
     *
     */
    @Override
    public void onBindViewHolder(MyNavViewHolder holder, int position) {
        NavDrawerItem current = data.get(position);
        holder.title.setText(current.getTitle());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyNavViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public MyNavViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
