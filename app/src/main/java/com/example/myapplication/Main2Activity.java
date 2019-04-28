package com.example.myapplication;


import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

    }

    public void btninserts(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Insert");
        builder.setMessage("هل متاكد من اضافه الرسالة الجديدة ؟");
        builder.setIcon(R.drawable.insertblack);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                EditText editText = findViewById(R.id.txtinsert);
                DBOperation dbOperation = new DBOperation(getApplicationContext());
                dbOperation.openDB();
                boolean insert = dbOperation.InsertContact(editText.getText().toString(), 48);
                if (insert == true) {
                    Toast.makeText(getApplicationContext(), "تمت الاضافة", Toast.LENGTH_SHORT).show();
                }
                dbOperation.close();
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
