package com.example.memeslord.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.memeslord.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ShowImageActivity extends AppCompatActivity {

    private String imgURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_show);
        Intent oldIntent = getIntent();
        imgURL = oldIntent.getStringExtra("@string/img_url");
        ImageView img = (ImageView) findViewById(R.id.imageShow);
        img.setMaxHeight(img.getHeight());
        img.setMaxWidth(img.getWidth());
        ImageLoader.getInstance().displayImage(imgURL, img);

        Button buttonSave = findViewById(R.id.buttonSaveReddit);
        buttonSave.setOnClickListener(v -> {
            img.buildDrawingCache();
            Bitmap bm = img.getDrawingCache();
            saveImage(bm);
        });
    }

    public String getImagePath() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "/JPEG_" + timeStamp + "_";
        String storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
        return storageDir + imageFileName + ".jpg";
    }

    public void saveImage(Bitmap bitmap) {
        try {
            FileOutputStream output = new FileOutputStream(getImagePath());
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
