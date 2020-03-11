package com.example.memeslord;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.memeslord.model.Feed;
import com.example.memeslord.model.entry.Entry;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class RandomMemeActivity extends AppCompatActivity {

    private static final String TAG = "RandomMainActivity";
    private static final String BASE_URL = "https://www.reddit.com/r/";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_meme);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();


        FeedAPI service = retrofit.create(FeedAPI.class);

        Call<Feed> call = service.getFeed();

        call.enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(Call<Feed> call, Response<Feed> response) {
                Log.d(TAG, "onResponse: feed:" + response.body().toString());
                Log.d(TAG, "onResponse: Server Response:" + response.toString());

                List<Entry> entries = response.body().getEntries();

                Log.d(TAG, "onResponse: entries:  " + response.body().getEntries());

//                Log.d(TAG, "onResponse: author:  " + entries.get(0).getAuthor());
//
//                Log.d(TAG, "onResponse: updated:  " + entries.get(0).getUpdated());
//
//                Log.d(TAG, "onResponse: title:  " + entries.get(0).getTitle());

                List<Post> posts = new ArrayList<>();
                for (int i = 0; i < entries.size(); ++i) {
                    ExtractXML extractXML1 = new ExtractXML("<a href=", entries.get(i).getContent());
                    List<String> postContent = extractXML1.start();

                    ExtractXML extractXML2 = new ExtractXML("<img src=", entries.get(i).getContent());
                    try {
                        postContent.add(extractXML2.start().get(0));
                    } catch (NullPointerException e) {
                        postContent.add(null);
                        Log.e(TAG, "onResponse: IndexOutOfBoundsException(thumbnail): " + e.getMessage());
                    } catch (IndexOutOfBoundsException e) {
                        postContent.add(null);
                        Log.e(TAG, "onResponse: IndexOutOfBoundsException(thumbnail): " + e.getMessage());
                    }

                    posts.add(new Post(entries.get(i).getTitle(), entries.get(i).getAuthor().getName(), postContent.get(0), postContent.get(postContent.size() - 1), postContent.get(2) ));
                }

                for(int i =0; i<posts.size();++i){
                    Log.d(TAG, "onResponse: \n"+
                            "PostURL: " + posts.get(i).getPostURL() + "\n" +
                            "ThumbnailURL: " + posts.get(i).getThumbnailURL() + "\n" +
                            "Title: " + posts.get(i).getTitle() + "\n" +
                            "Author: " + posts.get(i).getAuthor() + "\n" +
                            "ImageURL: " + posts.get(i).getImageURL() + "\n"
                    );
                }
            }


            @Override
            public void onFailure(Call<Feed> call, Throwable t) {
                Log.e(TAG, "onFailure: Unable to retrieve RSS: " + t.getMessage());
                Toast.makeText(RandomMemeActivity.this, "An Error Occured", Toast.LENGTH_SHORT).show();


            }
        });
    }
}
