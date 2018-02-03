package com.ooczc.spammess;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity implements
        AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    Button bt;
    EditText et;
    List<Map<String,Object>> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去除标题栏
        setContentView(R.layout.listview);
        //通过int型反射，获取控件对象
        //这里的tv_a1必须在setContentView(R.layout.activity_main)的activity_main中.

        ListView listView = (ListView) findViewById(R.id.lv_main);



        list = new ArrayList<>();

        getSmsFromPhone();


        Log.i("aven","---------MainActivity  111");
        MyAdapter adapter = new MyAdapter(this);
        adapter.setList(list);

        listView.setAdapter(adapter);

//        listView.setOnItemClickListener(this);


    }
    private Uri SMS_INBOX = Uri.parse("content://sms/");
    public void getSmsFromPhone() {
        ContentResolver cr = getContentResolver();
        String[] projection = new String[] {"_id", "address", "person",
                "body", "date", "type" };//"_id", "address", "person",, "date", "type
        Cursor cur = cr.query(SMS_INBOX, projection, null, null, "date desc");
        Log.i("aven","---------getSmsFromPhone  111");
        if (null == cur) {
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("num","110");
            map.put("mess","读取短信出错！");
            list.add(map);
            return;
        }
        while(cur.moveToNext()) {
            String number = cur.getString(cur.getColumnIndex("address"));//手机号
            String name = cur.getString(cur.getColumnIndex("person"));//联系人姓名列表
            String body = cur.getString(cur.getColumnIndex("body"));
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("num",number);
            map.put("mess",body);
            list.add(map);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent,
                            View view, int position, long id) {

        Intent intent = new Intent();
        intent.setClass(this,MessActivity.class);

        Map<String,Object> map =
                (Map<String, Object>) parent.getItemAtPosition(position);
        intent.putExtra("index",""+map.get("num"));
        intent.putExtra("index2",""+map.get("mess"));
        startActivity(intent);


    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent,
                                   View view, int position, long id) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.people);
        builder.setTitle("个性化过滤");
        builder.setMessage("是否把当前短信发送至服务器，实现个性化过滤？");
        builder.setNegativeButton("不发送", null);
        builder.setPositiveButton("发送", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this,
                        "已发送", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();

        return true; //false表示不消化事件，事件继续传递下去,传给点击事件
    }
}