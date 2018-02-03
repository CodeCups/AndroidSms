package com.ooczc.spammess;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Map;

public class Dialog_Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        Log.i("aven","---------Dialog_Activity 111");
        TextView tv_num = (TextView) findViewById(R.id.tv_num);
        int flag = getIntent().getIntExtra("flag",3);
        String num = getIntent().getStringExtra("number");
        String mess = getIntent().getStringExtra("body");
        if(flag == 3){
            Log.i("aven","---------Dialog ******** wrong 3");

        }
        if(flag == 1){
            tv_num.setText("发现垃圾短信");
            Log.i("aven","---------Dialog ******** flag == 1 "+flag);
            tv_num.setTextColor(this.getResources().getColor(R.color.Red));
            tv_num.setTextSize(22);
        }
        else{
            tv_num.setText("来自"+num);
            Log.i("aven","---------Dialog ******** flag == 0 "+flag);
        }
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+"18380484212"));
        intent.putExtra("sms_body", num+": "+mess);
        startActivity(intent);

        TextView tv = (TextView) findViewById(R.id.tv_dialog);
        tv.setText(mess);
        intent = new Intent();
        intent.setClass(Dialog_Activity.this,MainActivity.class);
        startActivity(intent);


    }
}
