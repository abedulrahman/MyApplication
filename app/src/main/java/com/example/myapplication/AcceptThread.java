package com.example.myapplication;



import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class AcceptThread extends Thread {

    private final Handler mHandler;

    public AcceptThread( Handler mHandler) {
        this.mHandler = mHandler;
    }

    public void run(){
        int i = 0 ;
        while (true){
            Message msg=mHandler.obtainMessage(Constants.MESSAGE_READ);
            Bundle bundle=new Bundle();
            bundle.putString("data","i :"+i);
            msg.setData(bundle);
            mHandler.sendMessage(msg);
            i++;
            try{Thread.sleep(1000);}catch (Exception e){}
        }
    }
}
