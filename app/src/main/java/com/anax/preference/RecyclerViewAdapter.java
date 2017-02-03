package com.anax.preference;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.anax.preference.Model.Country;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anas Alaa
 * @version 1.0.0
 * @since 27/01/2017
 */

class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<Country> Countries = new ArrayList<>();

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView countryNameTextView;
        TextView dialCodeTextView;
        TextView codeTextView;

        ViewHolder(View v) {
            super(v);
            countryNameTextView = (TextView) v.findViewById(R.id.country_name);
            dialCodeTextView = (TextView) v.findViewById(R.id.dial_code);
            codeTextView = (TextView) v.findViewById(R.id.code);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    RecyclerViewAdapter(List<Country> Countries) {
        this.Countries = Countries;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.countryNameTextView.setText(Countries.get(position).getName());
        holder.dialCodeTextView.setText(Countries.get(position).getDialCode());
        holder.codeTextView.setText(Countries.get(position).getCode());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return Countries.size();
    }
}