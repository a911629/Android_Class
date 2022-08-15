package com.calvin.atm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

public class ContactActivity extends AppCompatActivity {

    private static final int REQUEST_CONTACTS = 80;
    private static final String TAG = ContactActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        if(permission == PackageManager.PERMISSION_GRANTED) {
            readContacts();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    REQUEST_CONTACTS);
        }
    }

    @SuppressLint("Range")
    private void readContacts() {
        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        while(cursor.moveToNext()) {
            String phone = "";
            @SuppressLint("Range") int id = cursor.getInt(
                    cursor.getColumnIndex(ContactsContract.Contacts._ID));
            @SuppressLint("Range") String name = cursor.getString(
                    cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            @SuppressLint("Range") int hasPhone = cursor.getInt(
                    cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
            if (hasPhone == 1) {
                Cursor cursor2 = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
                        new String[]{String.valueOf(id)}, null);
                while (cursor2.moveToNext()) {
                    phone = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA));
                }
            }

            Log.d(TAG, "readContacts: " + name + "phone : " + phone);

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                readContacts();
            }
        }
    }
}