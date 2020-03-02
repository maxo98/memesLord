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
        String imagePath = intent.getStringExtra("SelectedImage");
        imagePath = imagePath.split("raw/")[1];
        PermissionChecker.verifyStoragePermissions(this);
        File file = new  File(imagePath);
        System.out.println("file : " + file);
        if(file.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            System.out.println(bitmap);
            imageView.setImageBitmap(bitmap);
        }
        else {
            System.out.println("mange tes morts");
        }
    }

}
