package com.example.memeslord;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.memeslord.NewMemeActivity;
import com.example.memeslord.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;











public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;




    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //   updateUI(currentUser);
    }
    private void singin(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    private static final String TAG = "";

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent goNewMeme = new Intent(getApplicationContext(), NewMemeActivity.class);
                            startActivity(goNewMeme);
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                           Toast.makeText(getApplicationContext(),"LE COMPTE N'EXISTE PAS",Toast.LENGTH_SHORT).show();
                        }


                    }
                });
    }


    private void createAccount (String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent newMeme  = new Intent(getApplicationContext(), NewMemeActivity.class);


                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(getApplicationContext(), "LE COMPTE EXISTE DEJA", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
// Initialize Firebase Auth
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        EditText pseudo   = (EditText)findViewById(R.id.Email);
        EditText password   = (EditText)findViewById(R.id.MotDePasse);
        Button Login = findViewById(R.id.loginButton);
        Button Register = findViewById(R.id.registerButton);

        Login.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         singin(pseudo.getText().toString(), password.getText().toString());                                     }

        });
                Register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                createAccount(pseudo.getText().toString(), password.getText().toString());


            }
                        });




            }
        }
