package com.calvin.atm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;
import static android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE;

public class LoginActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_CAMERA = 5;
    private EditText edUserid;
    private EditText edPasswd;
    private CheckBox cbRemember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "calvin onCreate: In LoginActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
//        if(permission == PackageManager.PERMISSION_GRANTED){
////            takePhoto();
//        } else {
//            Log.d(TAG, "onCreate: calvin not allow");
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
//                    REQUEST_CODE_CAMERA);
//        }

        edUserid = findViewById(R.id.edUserID);
        edPasswd = findViewById(R.id.edUserPasswd);
        cbRemember = findViewById(R.id.cb_rem_userid);
        cbRemember.setChecked(getSharedPreferences("atm", MODE_PRIVATE).getBoolean("REMEMBER USERID", false));
        cbRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getSharedPreferences("atm", MODE_PRIVATE)
                        .edit()
                        .putBoolean("REMEMBER USERID", isChecked)
                        .apply();
            }
        });
        String userid = getSharedPreferences("atm", MODE_PRIVATE)
                .getString("USERID","");
        edUserid.setText(userid);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePhoto();
            }
        }
    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(intent);
    }

    public void login(View view) {
        String userid = edUserid.getText().toString();
        final String passwd = edPasswd.getText().toString();

        FirebaseDatabase.getInstance().getReference("users").child(userid).child("password")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String pw = snapshot.getValue().toString();
                        if(pw.equals(passwd)) {
                            boolean remember = getSharedPreferences("atm", MODE_PRIVATE)
                                    .getBoolean("REMEMBER USERID", false);
                            if (remember) {
                                getSharedPreferences("atm", MODE_PRIVATE)
                                        .edit()
                                        .putString("USERID", userid)
                                        .apply();
                            }
                            setResult(RESULT_OK);
                            finish();
                        } else {
                            new AlertDialog.Builder(LoginActivity.this)
                                    .setTitle("登入結果")
                                    .setMessage("登入失敗")
                                    .setPositiveButton("OK", null)
                                    .show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

//        if ("jack".equals(userid) && "1234".equals(passwd)) {
//            setResult(RESULT_OK);
//            new AlertDialog.Builder(LoginActivity.this)
//                    .setTitle("登入結果")
//                    .setMessage("登入成功")
//                    .setPositiveButton("OK", null)
//                    .show();
////            finish();
//        } else {
//            new AlertDialog.Builder(LoginActivity.this)
//                    .setTitle("登入結果")
//                    .setMessage("登入失敗")
//                    .setPositiveButton("OK", null)
//                    .show();
//        }
    }


    public void quit(View view) {

    }
}