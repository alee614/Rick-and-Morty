package com.example.rickandmorty;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.ResponseHandlerInterface;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import cz.msebera.android.httpclient.Header;

import static androidx.core.content.ContextCompat.getSystemService;

public class EpisodeFragment extends Fragment {
    private View view;
    private TextView textView_episodeName, textView_episode, textView_airedDate;
    private Button button;
    private RecyclerView recyclerView;
    private EpisodeCharacterAdapter adapter;

    private String count;
    private ArrayList<EpisodeCharacter> episodeCharacterArrayList;

    private AsyncHttpClient client = new AsyncHttpClient();
    private String url = "https://rickandmortyapi.com/api/episode/";
    private String imageUrl;

    private String CHANNEL_ID = "my_channel_01";
    private int NOTIFICATION_ID = 234;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_episode, container, false);
        super.onCreateView(inflater, container, savedInstanceState);

        textView_episode = view.findViewById(R.id.textView_episode);
        textView_episodeName = view.findViewById(R.id.textView_episodeName);
        textView_airedDate = view.findViewById(R.id.textView_airDate);
        button = view.findViewById(R.id.button);
        recyclerView = view.findViewById(R.id.recyclerView_episode);
        episodeCharacterArrayList = new ArrayList<>();

        count = getArguments().getString("count");
        int size = Integer.parseInt(count);
        Random rand = new Random();
        String randomCount = String.valueOf(rand.nextInt(size) + 1);
        Log.d("count in episode", randomCount);
        //client.setEnableRedirects(true, true, true);;
        url = url + randomCount;

        createNotificationChannel();

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONObject response = new JSONObject(new String(responseBody));
                    String name = response.getString("name");
                    String episode = response.getString("episode");
                    String airedDate = response.getString("air_date");

                    //replace the name's spaces with underscores to put in the notification

                    // set up the onClick method here because it needs to use GET information
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), CHANNEL_ID)
                            .setSmallIcon(R.drawable.ic_launcher_foreground)
                            .setContentTitle(getString(R.string.notification_title, name, episode))
                            .setContentText(getString(R.string.notification, episode.substring(4), name))
                            .setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText(getString(R.string.notification, episode.substring(4), name)))
                            .setPriority(NotificationCompat.PRIORITY_HIGH);

                    // this is for sending the user to the website
                    //      .setContentIntent()
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());

// notificationId is a unique int for each notification that you must define
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            notificationManager.notify(NOTIFICATION_ID, builder.build());
                        }
                    });


                    JSONArray characters = response.getJSONArray("characters");
                    for (int i = 0; i < characters.length(); i++){
                        String url = (String) characters.get(i);
                        //String url = getImageUrl((String) characters.get(i));
                        //Log.d("url", url);

                        client.get(url, new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                try {
                                    JSONObject obj = new JSONObject(new String(responseBody));
                                    String image = obj.getString("image");
                                    Log.d("image", image);
                                    EpisodeCharacter episodeCharacter = new EpisodeCharacter(image);
                                    episodeCharacterArrayList.add(episodeCharacter);
                                    adapter = new EpisodeCharacterAdapter(episodeCharacterArrayList);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                                    recyclerView.setAdapter(adapter);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                            }
                        });

                        //Log.d("testing", episodeCharacter.getImage_url());
                    }


                    //Log.d("adapter", String.valueOf(adapter.getItemCount()));


                    textView_episode.setText(episode);
                    textView_episodeName.setText(name);
                    textView_airedDate.setText(airedDate);



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

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "my_channel";
            String description = "this is my channel";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(description);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            channel.setShowBadge(true);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }

//    public String getImageUrl(String link){
//        String death;
//        client.get(link, new AsyncHttpResponseHandler() {
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                try {
//                    JSONObject response = new JSONObject(new String(responseBody));
//                    String image = response.getString("image");
//                    imageUrl = image;
//                    //Log.d("imageUrl", imageUrl);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//            }
//        });
//
//        Log.d("imageUrl", imageUrl);
//        return death;
//    }

}
