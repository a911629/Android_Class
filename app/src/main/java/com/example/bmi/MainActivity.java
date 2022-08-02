package com.example.bmi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    private EditText edWeight;
    private EditText edHeight;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        findViews ();
        String hello = getString(R.string.hello);
    }

    public MainActivity() {
        super();
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart: ");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop: ");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: ");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume();
    }

    @Override
    protected void onRestart() {
        Log.d(TAG, "onRestart: ");
        super.onRestart();
    }

    private void findViews() {
        edWeight = findViewById (R.id.ed_weight);
        edHeight = findViewById (R.id.ed_height);
        result = findViewById (R.id.result);
        Button help = findViewById (R.id.help);
        help.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder (MainActivity.this)
                        .setTitle (getString(R.string.help))
                        .setMessage (getString(R.string.bmi_info))
                        .setPositiveButton (getString(R.string.ok), null)
                        .show ();
            }
        });
    }

    public void bmi(View view) {
        String w = edWeight.getText ().toString ();
        String h = edHeight.getText ().toString ();
        float weight = Float.parseFloat (w);
        float height = Float.parseFloat (h);
        float bmi = weight / (height * height);
        Log.d ("MainActivity", "BMI : " + bmi);
        Toast.makeText (this, "Your BMI is " + bmi, Toast.LENGTH_LONG).show ();
        result.setText (getString(R.string.your_bmi_is) + bmi);
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("BMI", bmi);
        startActivity(intent);
//        AlertDialog alertDialog = new AlertDialog.Builder (this)
//                .setTitle ("BMI")
//                .setMessage (getString(R.string.your_bmi_is) + bmi)
//                .setPositiveButton (getString(R.string.ok), new DialogInterface.OnClickListener () {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        edWeight.setText ("");
//                        edHeight.setText ("");
//                    }
//                })
//                .show ();
    }
}