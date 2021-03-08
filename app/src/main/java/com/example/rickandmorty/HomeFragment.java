package com.example.rickandmorty;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class HomeFragment extends Fragment {
    private ImageView image;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        image = view.findViewById(R.id.imageView_home);
        Picasso.get().load("file:///android_asset/images/rick.jpg").into(image);

        // Inflate the layout for this fragment
        return view;
    }
}