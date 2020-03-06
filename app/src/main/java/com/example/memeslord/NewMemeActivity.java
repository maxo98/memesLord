package com.example.memeslord;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;

public class NewMemeActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 100;
    private static final int CAMERA_IMAGE = 200;
    private String fileName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null)
            fileName = savedInstanceState.getString("fileName");

        setContentView(R.layout.activity_new_meme);

        Button buttonGalleryImage = findViewById(R.id.buttonPickGalleryImage);
        Button buttonCameraImage = findViewById(R.id.buttonTakeAPict);

        buttonGalleryImage.setOnClickListener((v) -> {openGallery();});

        buttonCameraImage.setOnClickListener((v) -> {openCamera();});
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString("fileName", fileName);
    }

    private void openGallery() {
        Intent gallery =
                new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    private void openCamera() {
        try {
            File tempFile = File.createTempFile("memesLord", ".jpg");
            fileName = tempFile.getAbsolutePath();
            Uri uri = Uri.fromFile(tempFile);
            Intent camera =
                    new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            camera.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            camera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(camera, CAMERA_IMAGE);
        }
        catch (IOException e) {
            System.err.println("File couldn't be created");
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && (requestCode == PICK_IMAGE || requestCode == CAMERA_IMAGE)) {
            if(data != null) {
                Uri imageUri = data.getData();
                String uriString = imageUri.toString();
                Intent goCreateMeme = new Intent(this, CreateMeme.class);
                goCreateMeme.putExtra("SelectedImage", uriString);
                startActivity(goCreateMeme);
            }
        }
    }
}
