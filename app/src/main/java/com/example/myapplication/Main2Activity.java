package com.example.myapplication;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        AcceptThread t=new AcceptThread(mHandler, mHandler);
        t.start();


    }
    private final Handler mHandler =new Handler(){

        public void handMessage(Message msg){
            switch (msg.what){
                case Constants.MESSAGE_READ:
                    String x =msg.getData().getString("data");

                    textView.setText(x);
                    break;
            }
        }

    };
}
