package com.calvin.atm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import static android.content.ContentValues.TAG;

public class LoginActivity extends AppCompatActivity {

    private EditText edUserid;
    private EditText edPasswd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "calvin onCreate: In LoginActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edUserid = findViewById(R.id.edUserID);
        edPasswd = findViewById(R.id.edUserPasswd);
    }

    public void login(View view) {
        String userid = edUserid.getText().toString();
        String passwd = edPasswd.getText().toString();
        if("jack".equals(userid) && "1234".equals(passwd)) {
            setResult(RESULT_OK);
            finish();
        }
    }

    public void quit(View view) {

    }
}