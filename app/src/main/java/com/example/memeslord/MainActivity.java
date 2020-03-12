package com.example.memeslord;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonNewMeme = findViewById(R.id.buttonNewMeme);
        buttonNewMeme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goNewMeme = new Intent(v.getContext(), NewMemeActivity.class);
                startActivity(goNewMeme);
            }
        });

        Button buttonRandomMeme = findViewById(R.id.buttonRandomMeme);
        buttonRandomMeme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goRandomMeme = new Intent(v.getContext(), BrowseRedditActivity.class);
                startActivity(goRandomMeme);
            }
        });
    }
}
