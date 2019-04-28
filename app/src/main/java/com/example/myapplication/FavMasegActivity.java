package com.example.myapplication;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

public class FavMasegActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_maseg);

        DBOperation dbOperation = new DBOperation(this);
        dbOperation.openDB();
        ArrayList<MESSAGES> data = dbOperation.selectallFav();
        dbOperation.closeDB();

        Mycustemadabter mycustemadabter = new Mycustemadabter(this, R.layout.msg_item, data);
        GridView listView = findViewById(R.id.favbist);
        listView.setAdapter(mycustemadabter);
    }

    public void homemsg(View view) {
        Intent intent = new Intent(FavMasegActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void finesh(View view) {
        finish();
    }

    public void emails(View view) {
        String txt = "السلام عليكم ورحمه الله \n اقتراحي هو :";
        Intent sendemail = new Intent(Intent.ACTION_SEND);
        sendemail.setData(Uri.parse("mailto:"));
        sendemail.setType("message/rfc822");
        sendemail.putExtra(Intent.EXTRA_EMAIL, "abedulrahman2017@gmail.com");
        sendemail.putExtra(Intent.EXTRA_SUBJECT, "تطبيق مسجاتي");
        sendemail.putExtra(Intent.EXTRA_TEXT, txt);
        startActivity(Intent.createChooser(sendemail, "Send Email"));
    }

    class Mycustemadabter extends ArrayAdapter<MESSAGES> {
        ArrayList<MESSAGES> messages;
        Context context;
        int resource;

        public Mycustemadabter(Context context, int resource, ArrayList<MESSAGES> objects) {
            super(context, resource, objects);
            this.context = context;
            this.resource = resource;
            this.messages = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.msg_item, null);
            TextView textView = view.findViewById(R.id.msg_text);
            CheckBox like = view.findViewById(R.id.like);
            CheckBox copy = view.findViewById(R.id.copy);
            CheckBox share = view.findViewById(R.id.share);

            final MESSAGES msg = messages.get(position);
            textView.setText(msg.getMESSAGE());

            if (msg.getFAV() == 1) {
                like.setClickable(true);
            } else {
                like.setClickable(false);
            }
            copy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager clipboard = (ClipboardManager) context.getSystemService(context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("label", msg.getMESSAGE());
                    clipboard.setPrimaryClip(clip);
                }
            });
            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, msg.getMESSAGE());
                    context.startActivity(Intent.createChooser(sharingIntent, "المشاركة مع"));
                }
            });

            like.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    DBOperation dbOperation = new DBOperation(context);
                    dbOperation.openDB();
                    if (isChecked == true) {
                        dbOperation.upDate(msg.getID(), 1);
                    } else {
                        dbOperation.upDate(msg.getID(), 0);

                    }
                    dbOperation.closeDB();
                }
            });
            return view;
        }
    }

}
