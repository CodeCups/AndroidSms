package com.aven.sms;

import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import java.util.HashMap;
import java.util.Map;

import static android.Manifest.permission.READ_SMS;
import static android.Manifest.permission.SEND_SMS;

public class welcomeActivity extends AppCompatActivity {
    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcome);
        mayRequestContacts();
        getSmsFromPhone();
    }
    private Uri SMS_INBOX = Uri.parse("content://sms/");
    public void getSmsFromPhone() {
        ContentResolver cr = getContentResolver();
        String[] projection = new String[] {"_id", "address", "person",
                "body", "date", "type" };//"_id", "address", "person",, "date", "type
        Cursor cur = cr.query(SMS_INBOX, projection, null, null, "date desc");
    }
    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_SMS) == PackageManager.PERMISSION_GRANTED||
                checkSelfPermission(SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_SMS)||
                shouldShowRequestPermissionRationale(SEND_SMS)) {
            requestPermissions(new String[]{READ_SMS,SEND_SMS}, REQUEST_EXTERNAL_STORAGE);
        } else {
            requestPermissions(new String[]{READ_SMS,SEND_SMS}, REQUEST_EXTERNAL_STORAGE);
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mayRequestContacts();
            }
        }
    }
}
