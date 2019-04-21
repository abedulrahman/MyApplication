package com.example.myapplication;


import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;


public class MainActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

TabHost tabHost=getTabHost();
tabHost.addTab(tabHost.newTabSpec("main").setIndicator("main").setContent(new Intent(this,Main2Activity.class)));
        tabHost.addTab(tabHost.newTabSpec("main").setIndicator("do sub").setContent(new Intent(this,Main2Activity.class)));

tabHost.setCurrentTab(0);

    }
}
