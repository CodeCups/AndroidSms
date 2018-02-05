package com.aven.sms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

public class welcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcome);

    }
}
