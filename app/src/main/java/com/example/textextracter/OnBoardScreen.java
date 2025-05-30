package com.example.textextracter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.hololo.tutorial.library.Step;
import com.hololo.tutorial.library.TutorialActivity;

public class OnBoardScreen extends TutorialActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // as we want three screens write this code three time
        addFragment(new Step.Builder().setTitle("extraction of text from images using ML ")
                .setContent("using ML Kit to recognize text in images or video, such as the text of a street sign")
                .setBackgroundColor(Color.parseColor("#FF0957")) // int background color
                .setDrawable(R.drawable.onboard1) // int top drawable
                .setSummary("basically extracting text from image rather writing it")
                .build());
        addFragment(new Step.Builder().setTitle("copy and use the text")
                .setContent("copying the text from image")
                .setBackgroundColor(Color.parseColor("#FF0957")) // int background color
                .setDrawable(R.drawable.onboard2) // int top drawable
                .setSummary("required text is copied")
                .build());
        addFragment(new Step.Builder().setTitle("copied to clipboard")
                .setContent("text which is extracted is present in your clipboard")
                .setBackgroundColor(Color.parseColor("#FF0957")) // int background color
                .setDrawable(R.drawable.onboard3) // int top drawable
                .setSummary("use the extracted text")
                .build());


    }
    //opening another activity
    @Override
    public void finishTutorial() {
        // Your implementation
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void currentFragmentPosition(int position) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}