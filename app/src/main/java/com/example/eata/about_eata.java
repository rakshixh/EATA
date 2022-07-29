package com.example.eata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class about_eata extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_eata);
    }

    public void back(View v) {
        Intent intent = new Intent(about_eata.this,MainActivity.class);
        startActivity(intent);
    }
}