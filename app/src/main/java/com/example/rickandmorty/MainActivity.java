package com.example.rickandmorty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.ResponseHandler;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;

    private static String  url_character = "https://rickandmortyapi.com/api/character";
    private String size;
    private static String url_episode = "https://rickandmortyapi.com/api/episode";


    private Bundle bundle;

    AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // render a fragment homepage


        bundle = new Bundle();
        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                                               @Override
                                               public void onTabSelected(TabLayout.Tab tab) {
                                                   switch(tab.getPosition()){
                                                       case 0:
                                                           break;
                                                       case 1:
                                                           client.get(url_character, new AsyncHttpResponseHandler() {
                                                               @Override
                                                               public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                                                   // grabbing the character count
                                                                   try {
                                                                       JSONObject response = new JSONObject(new String(responseBody));
                                                                       JSONObject info = response.getJSONObject("info");
                                                                       size = info.getString("count");
                                                                       bundle.putString("count", size);
                                                                       // shared preferences
                                                                       // letting fragment know
                                                                       // within on sucess, everything is done
                                                                       CharacterFragment characterFragment = new CharacterFragment();
                                                                       characterFragment.setArguments(bundle);

                                                                       loadFragment(characterFragment, R.id.fragContainerView);

                                                                   } catch (JSONException e) {
                                                                       e.printStackTrace();
                                                                   }
                                                               }
                                                               @Override
                                                               public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                                               }
                                                           });

                                                           break;
                                                       case 2:
                                                           client.get(url_episode, new AsyncHttpResponseHandler() {
                                                               @Override
                                                               public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                                                   // grabbing the character count
                                                                   try {
                                                                       JSONObject response = new JSONObject(new String(responseBody));
                                                                       JSONObject info = response.getJSONObject("info");
                                                                       size = info.getString("count");
                                                                       bundle.putString("count", size);
                                                                       // shared preferences
                                                                       // letting fragment know
                                                                       // within on sucess, everything is done
                                                                       EpisodeFragment episodeFragment = new EpisodeFragment();
                                                                       episodeFragment.setArguments(bundle);

                                                                       loadFragment(episodeFragment, R.id.fragContainerView);
                                                                   } catch (JSONException e) {
                                                                       e.printStackTrace();
                                                                   }
                                                               }
                                                               @Override
                                                               public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                                               }
                                                           });
                                                           break;
                                                       case 3:
                                                           loadFragment(new LocationFragment(), R.id.fragContainerView);
                                                           Log.d("pressed", String.valueOf(tab.getPosition()));
                                                           break;
                                                   }
                                               }
                                               @Override
                                               public void onTabUnselected(TabLayout.Tab tab) {
                                               }

                                               @Override
                                               public void onTabReselected(TabLayout.Tab tab) {
                                               }
                                           }

        );

    }

    public void loadFragment(Fragment fragment, int id){
        FragmentManager fragmentManager = getSupportFragmentManager();
        // create a fragment transaction to begin the transaction and replace the fragment
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //replacing the placeholder - fragmentContainterView with the fragment that is passed as parameter
        fragmentTransaction.replace(id, fragment);
        fragmentTransaction.commit();
    }

    // put everything in on success
    // load something else for failure

    public String getCount(String url){
        // input the url for either the character or the episode
        // returns the count of either or

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                // grabbing the character count
                try {
                    JSONObject response = new JSONObject(new String(responseBody));
                    JSONObject info = response.getJSONObject("info");
                    size = info.getString("count");
                    Log.d("size", size);
                    bundle.putString("count", size);
                    // shared preferences
                    // letting fragment know
                    // within on sucess, everything is done

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            }
        });
        return size;

    }

}