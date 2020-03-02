package com.example.memeslord;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class NewMemeActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_meme);

        Button buttonNewMeme = findViewById(R.id.buttonPickGalleryImage);
        Button buttonRandomMeme = findViewById(R.id.buttonRandomMeme);

        buttonRandomMeme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goRandomMeme = new Intent(v.getContext(), RandomMemeActivity.class);
                startActivity(goRandomMeme);
            }
        });
    }
}
