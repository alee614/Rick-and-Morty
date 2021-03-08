package com.example.rickandmorty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {
    private List<Location> locations;

    public LocationAdapter(ArrayList<Location> locations){
        this.locations = locations;

    }
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView_name;
        TextView textView_type;
        TextView textView_dimension;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            textView_name = itemView.findViewById(R.id.textView_locationName);
            textView_type = itemView.findViewById(R.id.textView_locationType);
            textView_dimension = itemView.findViewById(R.id.textView_locationDimension);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // inflate the custom layout
        View locationView = inflater.inflate(R.layout.item_location, parent, false);
        // return the new ViewHolder
        ViewHolder viewHolder = new ViewHolder(locationView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LocationAdapter.ViewHolder holder, int position) {
        Location location = locations.get(position);
        holder.textView_name.setText(location.getName());
        holder.textView_type.setText(location.getType());
        holder.textView_dimension.setText(location.getDimension());

    }

    @Override
    public int getItemCount() {
        return locations.size();
    }
}
