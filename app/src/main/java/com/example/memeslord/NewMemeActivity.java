package com.example.memeslord;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;


public class NewMemeActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 100;
    private static final int CAMERA_IMAGE = 200;
    private String currentPhotoPath;
    private Uri photoURI;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_meme);

        Button buttonGalleryImage = findViewById(R.id.buttonPickGalleryImage);
        Button buttonCameraImage = findViewById(R.id.buttonTakeAPict);

        buttonGalleryImage.setOnClickListener((v) -> {openGallery();});

        buttonCameraImage.setOnClickListener((v) -> {openCamera();});
    }

    private void openGallery() {
        Intent gallery =
                new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void openCamera() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                System.err.println("Erreur a la création des fichiers");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_IMAGE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri imageUri = null;
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            if(data != null) {
                imageUri = data.getData();
            }
        }
        if(resultCode == RESULT_OK && requestCode == CAMERA_IMAGE) {
            //Uri of camera image
            imageUri = photoURI;
        }

        String uriString = imageUri.toString();
        if(uriString != "") {
            Intent goCreateMeme = new Intent(this, CreateMeme.class);
            goCreateMeme.putExtra("SelectedImage", uriString);
            startActivity(goCreateMeme);
        }
    }
}
