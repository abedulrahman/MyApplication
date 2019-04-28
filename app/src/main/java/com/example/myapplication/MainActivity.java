package com.example.myapplication;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBOperation dbOperation = new DBOperation(this);
        dbOperation.openDB();
        final ArrayList<MSG_CAT> data = dbOperation.selectallcat();
        dbOperation.closeDB();

        MyCustumAdapter myCustumAdapter = new MyCustumAdapter(this, R.layout.cat_item, data);
        ListView listView = findViewById(R.id.cat_list);
        listView.setAdapter(myCustumAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, MsgsActivity.class);
                intent.putExtra("cat_id", data.get(position).getID());
                intent.putExtra("cat_title", data.get(position).getNAME());
                startActivity(intent);
            }
        });
    }

    public void bestmsg(View view) {
        Intent intent = new Intent(MainActivity.this, FavMasegActivity.class);
        startActivity(intent);
    }

    public void email(View view) {
        String txt = "السلام عليكم ورحمه الله \n اقتراحي هو :";
        Intent sendemail = new Intent(Intent.ACTION_SEND);
        sendemail.setData(Uri.parse("mailto:"));
        sendemail.setType("message/rfc822");
        sendemail.putExtra(Intent.EXTRA_EMAIL, "abedulrahman2017@gmail.com");
        sendemail.putExtra(Intent.EXTRA_SUBJECT, "تطبيق مسجاتي");
        sendemail.putExtra(Intent.EXTRA_TEXT, txt);
        startActivity(Intent.createChooser(sendemail, "Send Email"));

    }

    public void fineshs(View view) {
        finish();
    }


    class MyCustumAdapter extends ArrayAdapter<MSG_CAT> {
        ArrayList<MSG_CAT> cats;
        Context context;
        int resource;

        public MyCustumAdapter(Context context, int resource, ArrayList<MSG_CAT> objects) {
            super(context, resource, objects);
            this.context = context;
            this.resource = resource;
            this.cats = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.cat_item, null);
            TextView textView = view.findViewById(R.id.cat_text);
            MSG_CAT cat = cats.get(position);
            textView.setText(cat.getNAME());
            return view;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.Specifications:
                Intent intent = new Intent(this, SpecificationsApp.class);
                startActivity(intent);
                break;
            case R.id.ShareApp:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey check out my app at: \n https://github.com/abedulrahman/MyApplication");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                break;
        }

        return super.onOptionsItemSelected(item);

    }

}


