package com.example.first_project.presentation.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.first_project.R;
import com.example.first_project.presentation.model.Pokemon;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private  List<Pokemon> values;
    private OnItemClickListener listener;


    public interface OnItemClickListener {
        void onItemClick(Pokemon item);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView txtHeader;
      //  TextView txtFooter;
        ImageView imageView;
        View layout;

        ViewHolder(View v) {
            super(v);
            layout = v;
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
         //   txtFooter = (TextView) v.findViewById(R.id.secondLine);
            imageView = (ImageView) v.findViewById(R.id.icon);
        }
    }

    public void add(int position, Pokemon item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ListAdapter(List<Pokemon> myDataset, OnItemClickListener listener) {
        this.values = myDataset;
        this.listener = listener;
    }

    public void setListener (OnItemClickListener listener) {
        this.listener = listener;
    }



    // Create new views (invoked by the layout manager)
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent,
            int viewType
    ) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Pokemon currentPokemon = values.get(position);
        holder.txtHeader.setText(currentPokemon.getName());
       // holder.txtFooter.setText(currentPokemon.getUrl());
        //holder.imageView.setImageBitmap(currentPokemon.getImage());
        Picasso.get().load(currentPokemon.getImage()).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(currentPokemon);
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }

}
