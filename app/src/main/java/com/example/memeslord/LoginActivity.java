package com.example.memeslord;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.memeslord.NewMemeActivity;
import com.example.memeslord.R;

public class LoginActivity extends AppCompatActivity { {

    Button Login = findViewById(R.id.loginButton);

    Login.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent goNewMeme = new Intent(v.getContext(), NewMemeActivity.class);
            startActivity(goNewMeme);
        }
    });
    }

}
