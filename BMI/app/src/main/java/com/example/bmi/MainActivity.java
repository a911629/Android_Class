package com.example.bmi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText edWeight;
    private EditText edHeight;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        findViews ();
        String hello = getString(R.string.hello);
    }

    public MainActivity() {
        super();
    }

    /**
     * 取得元件
     */
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

    /**
     * 計算BMI數值
     * @param view
     */
    public void bmi(View view) {
        String w = edWeight.getText ().toString ();
        String h = edHeight.getText ().toString ();
        float weight = Float.parseFloat (w);
        float height = Float.parseFloat (h);
        float bmi = weight / (height * height);
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("BMI", bmi);
        startActivity(intent);
    }
}