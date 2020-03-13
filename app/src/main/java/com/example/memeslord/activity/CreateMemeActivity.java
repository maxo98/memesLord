package com.example.memeslord.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.memeslord.R;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateMemeActivity extends AppCompatActivity implements View.OnTouchListener, View.OnFocusChangeListener{

    private String currentImagePath;
    private float dX;
    private float dY;
    private int lastAction;
    private FrameLayout frameLayout;
    private int lineNumber = 1;

    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meme);

        //set the selected image to the imageView
        ImageView imageView = findViewById(R.id.imageSelected);
        Intent intent = getIntent();
        String uriString = intent.getStringExtra("SelectedImage");
        Uri imageUri = Uri.parse(uriString);
        imageView.setImageURI(imageUri);
        //get original text
        EditText et = findViewById(R.id.textMeme);
        et.setOnTouchListener(this);
        et.setOnFocusChangeListener(this);


        //add New texts to the frame
        frameLayout = findViewById(R.id.frameImage);
        frameLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addText();
            }
        });
        //button to save the image
        Button saveButton = findViewById(R.id.buttonSave);
        saveButton.setOnClickListener(v -> {
            Bitmap bitmap = viewToBitmap(frameLayout);
            if(bitmap != null) {
                saveImage(bitmap);
            }
        });
    }

    public void addText() {
        EditText et = new EditText(this);
        EditText t = (EditText) findViewById(R.id.textMeme);
        FrameLayout.LayoutParams p = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        p.setMargins(350, 800, 0, 0);
        et.setLayoutParams(p);
        et.setTextSize(30);
        et.setBackground(t.getBackground());
        et.setText("write something here");
        et.setClickable(true);
        et.setId(lineNumber + 1);
        lineNumber++;
        frameLayout.addView(et);
        et.setOnTouchListener(this);
        et.setOnFocusChangeListener(this);

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        EditText t = (EditText) v;
        if(hasFocus) {
            t.setText("");
            Toast.makeText(this, "msdlkfjds", Toast.LENGTH_LONG);
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(t, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public boolean onTouch(View v, MotionEvent event) {
        EditText t = (EditText) v;

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_UP:
                if (lastAction == MotionEvent.ACTION_DOWN) {
                    t.requestFocus();
                }
                lastAction = MotionEvent.ACTION_UP;
                break;
            case MotionEvent.ACTION_DOWN:
                dX = v.getX() - event.getRawX();
                dY = v.getY() - event.getRawY();
                lastAction = MotionEvent.ACTION_DOWN;
                break;

            case MotionEvent.ACTION_MOVE:
                v.setY(event.getRawY() + dY);
                v.setX(event.getRawX() + dX);
                lastAction = MotionEvent.ACTION_MOVE;
                break;

            default:
                return false;
        }
        return true;
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
