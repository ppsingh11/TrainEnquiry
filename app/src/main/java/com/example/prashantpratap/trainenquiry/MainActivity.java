package com.example.prashantpratap.trainenquiry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Pnr(View view) {
        Intent i = new Intent(this,pnr.class);
        startActivity(i);
    }

    public void fare(View view) {

        Intent i = new Intent(this,fare.class);
        startActivity(i);
    }

    public void Running_st(View view) {
    }

    public void Seat_avail(View view) {
    }

    public void Train_bn_st(View view) {
    }
}
