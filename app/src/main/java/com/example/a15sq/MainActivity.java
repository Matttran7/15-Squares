package com.example.a15sq;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.a15sq.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SeekBar;
/**
 * Name: Matthew Tran
 * Date: Oct 2 2022
 *
 * Enhancements: Checked if the puzzle was solvable before displaying to user, can be seen in SurfaceViewMain(class)->checker(function)
 *
 * CS301 Assignment 3
 * */

public class MainActivity extends AppCompatActivity{

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // created objects
        SurfaceViewMain surfaceView = findViewById(R.id.surfaceView);
        Button resetbutton = findViewById(R.id.button);

        // gave objects listeners
        surfaceView.setOnTouchListener(surfaceView);
        resetbutton.setOnClickListener(surfaceView);

    } // onCreate

}