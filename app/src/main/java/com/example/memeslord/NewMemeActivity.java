package com.example.memeslord;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class NewMemeActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 100;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_meme);

        Button buttonGalleryImage = findViewById(R.id.buttonPickGalleryImage);
        Button buttonCameraImage = findViewById(R.id.buttonTakeAPict);

        buttonGalleryImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openGallery();

            }
        });
    }

    private void openGallery() {
        Intent gallery =
                new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            if(data != null) {
                Uri imageUri = data.getData();
                String imagePath = imageUri.getPath();
                Intent goCreateMeme = new Intent(this, CreateMeme.class);
                goCreateMeme.putExtra("SelectedImage", imagePath);
                startActivity(goCreateMeme);
            }
        }
    }
}
