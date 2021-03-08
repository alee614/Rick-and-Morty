package com.example.rickandmorty;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Parameter;
import java.util.Random;

import cz.msebera.android.httpclient.Header;


public class CharacterFragment extends Fragment {
    private View view;
    private ImageView profile;
    private TextView textView_name, textView_status, textView_species, textView_gender, textView_origin, textView_location, textView_episode;

    AsyncHttpClient client = new AsyncHttpClient();

    private String url = "https://rickandmortyapi.com/api/character/";
    private String count;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_character, container, false);

        textView_name = view.findViewById(R.id.textView_name);
        textView_status = view.findViewById(R.id.textView_status);
        textView_species = view.findViewById(R.id.textView_species);
        textView_gender = view.findViewById(R.id.textView_gender);
        textView_origin = view.findViewById(R.id.textView_origin);
        textView_location = view.findViewById(R.id.textView_location);
        textView_episode = view.findViewById(R.id.textView_episodes);
        profile = view.findViewById(R.id.imageView_profile);

        count = getArguments().getString("count");
        int size = Integer.parseInt(count);
        Random rand = new Random();
        String randomCount = String.valueOf(rand.nextInt(size) + 1);
        Log.d("count in character", randomCount);
        //client.setEnableRedirects(true, true, true);;
        url = url + randomCount;


        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONObject response = new JSONObject(new String(responseBody));
                    String name = response.getString("name");
                    String status = response.getString("status");
                    String species = response.getString("species");
                    String gender = response.getString("gender");
                    JSONObject origin = response.getJSONObject("origin");
                    String origin_name = origin.getString("name");
                    JSONObject location = response.getJSONObject("location");
                    String location_name = location.getString("name");
                    String image = response.getString("image");

                    JSONArray episodeList = response.getJSONArray("episode");
                    String episodes = "Appeared in Episodes: ";
                    for (int i = 0; i < episodeList.length(); i++){
                        String episodeNumber = (String) episodeList.get(i);
                        String episode = episodeNumber.substring(40);
                        if (i != episodeList.length() - 1){
                            episodes = episodes + episode + ", ";
                        }
                        else{
                            episodes = episodes + episode;
                        }
                    }
                    textView_name.setText(name);
                    textView_status.setText(status);
                    textView_species.setText(species);
                    textView_gender.setText(gender);
                    textView_origin.setText(origin_name);
                    textView_location.setText(location_name);
                    textView_episode.setText(episodes);
                    Picasso.get().load(image).into(profile);


//                    Log.d("name", name);
//                    Log.d("status", status);
//                    Log.d("gender", gender);
//                    Log.d("origin", origin_name);
//                    Log.d("location", location_name);
//                    Log.d("episode", episodes);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

        return view;
    }
}