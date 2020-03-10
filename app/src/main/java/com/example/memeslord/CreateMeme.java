package com.example.memeslord;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateMeme extends AppCompatActivity {

    private String currentImagePath;

    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meme);

        //set the selected image to the imageView
        ImageView imageView = findViewById(R.id.imageSelected);
        Intent intent = getIntent();
        String uriString = intent.getStringExtra("SelectedImage");
        Uri imageUri = Uri.parse(uriString);
        imageView.setImageURI(imageUri);

        FrameLayout frameLayout = findViewById(R.id.frameImage);
        Button saveButton = findViewById(R.id.buttonSave);

        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bitmap bitmap = viewToBitmap(frameLayout);
                if(bitmap != null) {
                    saveImage(bitmap);
                }
            }
        });
    }

    public Bitmap viewToBitmap(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
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
