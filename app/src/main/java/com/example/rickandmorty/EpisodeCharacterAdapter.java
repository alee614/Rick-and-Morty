package com.example.rickandmorty;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class EpisodeCharacterAdapter extends RecyclerView.Adapter<EpisodeCharacterAdapter.ViewHolder> {
    private List<EpisodeCharacter> episodeCharacters;

    public EpisodeCharacterAdapter(ArrayList<EpisodeCharacter> episodeCharacters){
        this.episodeCharacters = episodeCharacters;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // inflate the custom layout
        View locationView = inflater.inflate(R.layout.item_episode_character, parent, false);
        // return the new ViewHolder
        ViewHolder viewHolder = new EpisodeCharacterAdapter.ViewHolder(locationView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EpisodeCharacter episodeCharacter = episodeCharacters.get(position);
        Log.d("from adapter", episodeCharacter.getImage_url());
        Picasso.get().load(episodeCharacter.getImage_url()).into(holder.imageView_profile);
    }

    @Override
    public int getItemCount() {
        return episodeCharacters.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView_profile;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView_profile = itemView.findViewById(R.id.imageView_epchar);

        }
    }
}
