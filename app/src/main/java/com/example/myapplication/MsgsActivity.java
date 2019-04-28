package com.example.myapplication;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MsgsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msgs);

        int intent = getIntent().getIntExtra("cat_id", 0);
        String intent2 = getIntent().getStringExtra("cat_title");


        DBOperation dbOperation = new DBOperation(this);
        dbOperation.openDB();
        ArrayList<MESSAGES> data = dbOperation.selectallmasg(intent, intent2);
        dbOperation.closeDB();

        Mycustemadabter mycustemadabter = new Mycustemadabter(this, R.layout.msg_item, data);
        GridView listView = findViewById(R.id.msg_list);
        listView.setAdapter(mycustemadabter);

    }

    public void inserts(View view) {
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
        Toast toast = new Toast(this);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LayoutInflater layoutInflater = getLayoutInflater();
        View views = layoutInflater.inflate(R.layout.toastview, (ViewGroup) findViewById(R.id.toastview));
        TextView textView = views.findViewById(R.id.textView);
        TextView textView2 = views.findViewById(R.id.textView2);
        textView.setText("سوف يتم وضع الاضافات الجديدة في بند");
        textView2.setText("مسجات خاصة");
        textView.setTextColor(getResources().getColor(R.color.sharet));
        textView2.setTextColor(getResources().getColor(R.color.tiltecat));
        toast.setView(views);
        toast.show();
    }

    public void homemsg(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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
            final LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.msg_item, null);
            final TextView textView = view.findViewById(R.id.msg_text);
            final CheckBox like = view.findViewById(R.id.like);
            CheckBox copy = view.findViewById(R.id.copy);
            CheckBox share = view.findViewById(R.id.share);

            final MESSAGES msg = messages.get(position);
            textView.setText(msg.getMESSAGE());

            if (msg.getFAV() == 1) {
                like.setChecked(true);
            } else {
                like.setChecked(false);
            }


            copy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager clipboard = (ClipboardManager) context.getSystemService(context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("label", msg.getMESSAGE());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(getApplicationContext(), "تم النسخ", Toast.LENGTH_SHORT).show();

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
                        Toast.makeText(getApplicationContext(), "تم الوضع في المفضلة", Toast.LENGTH_SHORT).show();

                    } else {
                        dbOperation.upDate(msg.getID(), 0);
                        Toast.makeText(getApplicationContext(), "تم الازالة من المفضلة", Toast.LENGTH_SHORT).show();


                    }
                    dbOperation.closeDB();
                }
            });

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MsgsActivity.this, Main3Activity.class);
                    intent.putExtra("cat_id2", msg.getID());
                    intent.putExtra("cat_title2", msg.getMESSAGE());
                    startActivity(intent);
                }
            });

            return view;
        }
    }

}
