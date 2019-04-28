package com.example.myapplication;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Main3Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);


        String intent2 = getIntent().getStringExtra("cat_title2");

        EditText editText = findViewById(R.id.resevesms);
        editText.setText(intent2);
    }

    public void updatesms(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update");
        builder.setMessage("هل متاكد من تعديل الرسالة ؟");
        builder.setIcon(R.drawable.updateblack);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText editText = findViewById(R.id.resevesms);
                int intent = getIntent().getIntExtra("cat_id2", 0);
                DBOperation dbOperation = new DBOperation(getApplicationContext());
                dbOperation.openDB();
                dbOperation.UpdateContact(editText.getText().toString(), intent);
                dbOperation.closeDB();
                Toast.makeText(getApplicationContext(), "تم تعديل الرسالة", Toast.LENGTH_SHORT).show();

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT)
                        .show();
                dialog.cancel();
            }
        });
        builder.show();

    }

    public void delesms(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete");
        builder.setMessage("هل متاكد من حذف الرسالة ؟");
        builder.setIcon(R.drawable.deleteblack);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int intent = getIntent().getIntExtra("cat_id2", 0);
                DBOperation dbOperation = new DBOperation(getApplicationContext());
                dbOperation.openDB();
                dbOperation.DeleteContact(intent);
                dbOperation.closeDB();
                Toast.makeText(getApplicationContext(), "تم حذف الرسالة", Toast.LENGTH_SHORT).show();

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT)
                        .show();
                dialog.cancel();
            }
        });
        builder.show();


    }
}
