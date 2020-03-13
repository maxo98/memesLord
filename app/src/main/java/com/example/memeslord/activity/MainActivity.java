package com.example.memeslord.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.memeslord.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();

        Button login = findViewById(R.id.buttonLogin);
        String statut = "not logged";
        if(intent.hasExtra("logged")) {
            statut = intent.getStringExtra("logged");
        }
        System.out.println(statut);
        if(!statut.equals("logged")) {
            System.out.println("not logged");
            login.setOnClickListener(v -> {
                Intent loginIntent = new Intent(v.getContext(), LoginActivity.class);
                startActivity(loginIntent);
            });
        } else {
            System.out.println("logged");
            login.setVisibility(View.GONE);
        }

        //start new Meme activity
        Button buttonNewMeme = findViewById(R.id.buttonNewMeme);
        buttonNewMeme.setOnClickListener(v -> {
            Intent goNewMeme = new Intent(v.getContext(), NewMemeActivity.class);
            startActivity(goNewMeme);
        });

        //start reddit browse activity
        Button buttonRedditMeme = findViewById(R.id.buttonReddit);
        buttonRedditMeme.setOnClickListener(v -> {
            Intent goRandomMeme = new Intent(v.getContext(), BrowseRedditActivity.class);
            startActivity(goRandomMeme);
        });

        //start login/register activity

    }

}
