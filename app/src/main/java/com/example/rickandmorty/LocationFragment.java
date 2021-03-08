package com.example.rickandmorty;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class LocationFragment extends Fragment {
    AsyncHttpClient client = new AsyncHttpClient();
    private String url = "https://rickandmortyapi.com/api/location";

    private ArrayList<Location> locationArrayList;
    private RecyclerView recyclerView;
    private LocationAdapter adapter;
    View view;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_location, container, false);

        super.onCreateView(inflater, container, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        locationArrayList = new ArrayList<>();


        // go thro the first page of the locations and create an object and add to a list
        // call up LocationAdapter using locations array

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                JSONObject response = null;
                try {
                    response = new JSONObject(new String(responseBody));
                    JSONArray results = response.getJSONArray("results");

                    for (int i = 0; i < results.length(); i++){
                        JSONObject obj = results.getJSONObject(i);
                        String name = obj.getString("name");
                        String type = obj.getString("type");
                        String dimension = obj.getString("dimension");
                        Location location = new Location(name, type, dimension);
                        locationArrayList.add(location);
                        //Log.d("name", name);
                    }
                    adapter = new LocationAdapter(locationArrayList);

                    //Log.d("adapter", String.valueOf(adapter.getItemCount()));

                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }


        });

        //Log.d("location list size", String.valueOf(locationArrayList.size()));





        // Inflate the layout for this fragment
        return view;
    }
}