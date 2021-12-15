package com.example.foodsave.Other;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.foodsave.R;

public class about_us extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolBar);
        toolbar.setTitle(R.string.about_us);
        setSupportActionBar(toolbar);

        Fragment fragment = new Fragment(R.layout.about_us);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content,fragment).commit();
    }
}
