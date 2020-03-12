package com.example.memeslord;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

public class ShowImageActivity extends AppCompatActivity {

    private String imgURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_show);
        Intent oldIntent = getIntent();
        imgURL = oldIntent.getStringExtra("@string/img_url");
        ImageView img = (ImageView) findViewById(R.id.imageShow);
        ImageLoader.getInstance().displayImage(imgURL, img);
    }
}
