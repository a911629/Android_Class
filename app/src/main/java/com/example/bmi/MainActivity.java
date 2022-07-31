package com.example.bmi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
    }

    public void bmi(View view) {
        EditText edWeight = findViewById (R.id.ed_weight);
        EditText edHeight = findViewById (R.id.ed_height);
        String w = edWeight.getText ().toString ();
        String h = edHeight.getText ().toString ();
        float weight = Float.parseFloat (w);
        float height = Float.parseFloat (h);
        float bmi = weight / (height * height);
        Log.d ("MainActivity", "BMI : " + bmi);
    }
}