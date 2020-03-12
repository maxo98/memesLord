package com.example.memeslord;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

public class BrowseRedditActivity extends AppCompatActivity {

    private static final String TAG = "BrowseRedditActivity";
    private static final String BASE_URL = "https://www.reddit.com/r/";

    private WebView webView;

    private Button btnRefreshFeed;
    private EditText mFeedName;
    private String currentFeed;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentFeed = "memes";
        setContentView(R.layout.activity_browse_reddit);
        btnRefreshFeed = (Button) findViewById(R.id.btnRefreshFeed);
        mFeedName = (EditText) findViewById(R.id.etFeedName);

        init();

        btnRefreshFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String feedName = mFeedName.getText().toString();
                if(!feedName.equals("")){
                    currentFeed = feedName;
                    init();
                }
                else{
                    currentFeed = "memes";
                    init();
                }
            }
        });

    }

    public void init(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();


        FeedAPI service = retrofit.create(FeedAPI.class);

        Call<Feed> call = service.getFeed(currentFeed);

        call.enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(Call<Feed> call, Response<Feed> response) {
                if(response.body()!= null) {
                    List<Entry> entries = response.body().getEntries();

                    ArrayList<Post> posts = new ArrayList<>();
                    for (int i = 0; i < entries.size(); ++i) {
                        ExtractXML extractXML1 = new ExtractXML("<a href=", entries.get(i).getContent());
                        List<String> postContent = extractXML1.start();

                        ExtractXML extractXML2 = new ExtractXML("<img src=", entries.get(i).getContent());
                        try {
                            postContent.add(extractXML2.start().get(0));
                        } catch (NullPointerException e) {
                            postContent.add(null);
                        } catch (IndexOutOfBoundsException e) {
                            postContent.add(null);
                        }

                        posts.add(new Post(entries.get(i).getTitle(), entries.get(i).getAuthor().getName(), postContent.get(0), postContent.get(postContent.size() - 1), postContent.get(2)));
                    }

                    ListView listView = (ListView) findViewById(R.id.postList);
                    CustomListAdapter customListAdapter = new CustomListAdapter(BrowseRedditActivity.this, R.layout.card_layout_main, posts);
                    listView.setAdapter(customListAdapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Log.d(TAG, "onItemClick: Clicked: " + posts.get(position).toString());

                            Intent intent = new Intent(BrowseRedditActivity.this, ShowImageActivity.class);
                            intent.putExtra("@string/img_url", posts.get(position).getImageURL());
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Feed> call, Throwable t) {
                Log.e(TAG, "onFailure: Unable to retrieve RSS: " + t.getMessage());
                Toast.makeText(BrowseRedditActivity.this, "An Error Occured", Toast.LENGTH_SHORT).show();


            }
        });
    }

}
