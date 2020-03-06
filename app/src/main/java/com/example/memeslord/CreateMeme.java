package com.example.memeslord;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class CreateMeme extends AppCompatActivity {

    private ImageView imageView;

    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meme);

        imageView = (ImageView) findViewById(R.id.imageSelected);

        Intent intent = getIntent();
        String uriString = intent.getStringExtra("SelectedImage");
        Uri imageUri = Uri.parse(uriString);
        imageView.setImageURI(imageUri);

    }

}
