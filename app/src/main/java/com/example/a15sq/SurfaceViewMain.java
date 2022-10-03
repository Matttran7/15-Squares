package com.example.a15sq;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Name: Matthew Tran
 * Date: Oct 2 2022
 * CS301 Assignment 3
 * */

public class SurfaceViewMain extends SurfaceView implements View.OnTouchListener, View.OnClickListener{
    Paint paint;
    Paint txtpaint;
    int size;
    int padding;
    int lever;
    int track;
    float[] storeval;
    int saveindex;
    int countCheck;
    public ArrayList<Float> values = new ArrayList<Float>();
    public ArrayList<ArrayList<Float>> storage = new ArrayList<ArrayList<Float>>();
    ArrayList<Integer> Add;

    public SurfaceViewMain(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);

        saveindex = -1;
        track = 0;
        lever = 0;
        paint = new Paint();
        paint.setARGB(255,255,0,0);
        txtpaint = new Paint();
        txtpaint.setARGB(255,255,255,255);
        txtpaint.setTextSize(150);
        size = 275;
        padding = 25;
        storeval = new float[3];
        storeval[0] =0;
        storeval[1] = -1;
        storeval[2] = -1;
        countCheck = -1;
        while(countCheck%2 != 0) {
            reset();
            checker();
        }
    } // constructor
    // draws onto board
    @Override
    public void onDraw(Canvas canvas) {
        track = 0;
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                if(storage.get(track).get(0)-1 == track){
                    paint.setARGB(255,255,140,0);
                }
                else{
                    paint.setARGB(255,255,0,0);
                }
                if(lever == 1 && storeval[0] == storage.get(track).get(0)){
                    paint.setARGB(255,255,0,255);
                }
                canvas.drawRect((j*size)+j*padding,(i*size)+i*padding,size+j*size+(j*padding),size+i*size+(i*padding),paint);
                if(storage.get(track).get(0) == 0){
                    canvas.drawText(" ",size*j+(j*padding)+size/4,size*i+(i*padding)+(size/4)*3,txtpaint);
                }
                else {
                    canvas.drawText(String.format("%.0f", storage.get(track).get(0)), size * j + (j * padding) + size / 4, size * i + (i * padding) + (size / 4) * 3, txtpaint);
                }
                track++;
            } // for j
        }// for i
    }
    // Lever is 0 if its the first time clicking, and 1 if its the second time. The second time MUST be the "blank" spot
    @Override
    public boolean onTouch(View view, MotionEvent e) {
        if (e.getActionMasked() == MotionEvent.ACTION_DOWN) {
            float x = e.getX();
            float y = e.getY();
            if(lever == 0) {
                // Checks through the 16 squares to check for matching locations
                for (int i = 0; i < storage.size(); i++) {
                    if (storage.get(i).get(1) * size + (storage.get(i).get(1) * padding) <= y && y <= storage.get(i).get(1) * size + size + (storage.get(i).get(1) * padding)) {
                        if (storage.get(i).get(2) * size + (storage.get(i).get(2) * padding) <= x && x <= storage.get(i).get(2) * size + size + (storage.get(i).get(1) * padding)) {
                            if (storage.get(i).get(0) != 0) {
                                // saves the selected value to be swapped after the second click
                                storeval[0] = storage.get(i).get(0);
                                storeval[1] = storage.get(i).get(1);
                                storeval[2] = storage.get(i).get(2);
                                saveindex = i;
                                lever = 1;
                            }
                            break;
                        } //if y
                    }// if x
                } // for
            } // if lever 0
            else if (lever == 1){
                lever = 0;
                // Checks through the 16 squares to check for matching locations
                for (int i = 0; i < storage.size(); i++) {
                    if (storage.get(i).get(1) * size + (storage.get(i).get(1) * padding) <= y && y <= storage.get(i).get(1) * size + size + (storage.get(i).get(1) * padding)) {
                        if (storage.get(i).get(2) * size + (storage.get(i).get(2) * padding) <= x && x <= storage.get(i).get(2) * size + size + (storage.get(i).get(1) * padding)) {
                            /* Checks if on edges of the board, both x and y
                            *  Checks if the second selected square is a neighbor of the first selected square
                            *  Checks if the second selected square is the "blank" square
                            *  Other wise, reset the selecting processing and force user to select first again */
                            if(storeval[1] != 0 && storeval[1] == storage.get(i).get(1)+1 && storeval[2] == storage.get(i).get(2)){
                                if(storage.get(i).get(0) == 0){
                                    storage.get(saveindex).set(0,(float)0);
                                    storage.get(i).set(0,storeval[0]);
                                    break;
                                }
                            }
                            if(storeval[2] != 0 && storeval[2] == storage.get(i).get(2)+1 && storeval[1] == storage.get(i).get(1)){
                                if(storage.get(i).get(0) == 0){
                                    storage.get(saveindex).set(0,(float)0);
                                    storage.get(i).set(0,storeval[0]);
                                    break;
                                }
                            }
                            if(storeval[1] != 3 && storeval[1] == storage.get(i).get(1)-1 && storeval[2] == storage.get(i).get(2)){
                                if(storage.get(i).get(0) == 0){
                                    storage.get(saveindex).set(0,(float)0);
                                    storage.get(i).set(0,storeval[0]);
                                    break;
                                }
                            }
                            if(storeval[2] != 3 && storeval[2] == storage.get(i).get(2)-1 && storeval[1] == storage.get(i).get(1)){
                                if(storage.get(i).get(0) == 0){
                                    storage.get(saveindex).set(0,(float)0);
                                    storage.get(i).set(0,storeval[0]);
                                    break;
                                }
                            }
                        } // if 2
                    } // if 1
                } // for i
            } // else if lever 1
            invalidate();
            return true;
        } // if MotionEvent
        return false;
    }

    // shuffles board
    public void reset(){
        values.clear();
        storage.clear();
        // make values 0-15
        for(int i = 0; i < 16; i++){
            values.add((float)i);
        }
        Collections.shuffle(values);
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                ArrayList<Float> Add = new ArrayList<Float>();
                Add.add(values.remove(0));
                Add.add((float)i);
                Add.add((float)j);
                storage.add(Add);
            }
        }
    }
    // check if valid
    public int checker(){
        // Copy from ArrayList --> List [* turn 0 to 16 to make calculations easier]
        float[] check = new float[storage.size()]; // declare
        for(int i = 0; i < storage.size(); i++){
            if(storage.get(i).get(0) == 0){
                check[i] = 16;
            }
            else {
                check[i] = storage.get(i).get(0);
            }
        }
        // go through array and switch each element to result in the correct [1,2,3,4...16] order
        float holder = -1; // counts how many transitions were made
        countCheck = 0;
        for(int i = 0; i < storage.size(); i++){
            if(check[i] != i+1){
                for(int j = i+1; j < storage.size(); j++){
                    if(check[j] == i+1){
                        holder = check[j];
                        check[j] = check[i];
                        check[i] = holder;
                        countCheck++;
                        break;
                    }
                } // for j
            }
        } // for i
        return countCheck;
    }
    // reset button
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button){
            countCheck = -1;
            while(countCheck%2 != 0) {
                reset();
                checker();
            }
        } // if id = R.id
        invalidate();
    } // onClick
}
