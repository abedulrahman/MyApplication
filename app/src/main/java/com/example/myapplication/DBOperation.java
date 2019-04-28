package com.example.myapplication;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;


public class DBOperation extends SQLiteOpenHelper {

    private static String DB_PATH = "/data/data/com.example.myapplication/databases/";

    private static String DB_NAME = "msgDb.db";

    private SQLiteDatabase myDataBase;

    private final Context myContext;


    public DBOperation(Context context) {

        super(context, DB_NAME, null, 3);
        this.myContext = context;
        try {
            createDataBase();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if (dbExist) {
        } else {


            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {
                e.printStackTrace();
                throw new Error("Error copying database");

            }
        }

    }


    private boolean checkDataBase() {

        SQLiteDatabase checkDB = null;

        try {
            String myPath = DB_PATH + DB_NAME;
            File file = new File(myPath);
            if (file.exists() && !file.isDirectory())
                checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        } catch (SQLiteException e) {
            e.printStackTrace();

        }

        if (checkDB != null) {

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }


    private void copyDataBase() throws IOException {

        InputStream myInput = myContext.getAssets().open(DB_NAME);

        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = null;
        File file = new File(outFileName);
        if (file.exists() && !file.isDirectory()) {
            myOutput = new FileOutputStream(outFileName);
        }
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();

    }


    public void openDataBase() throws SQLException {

        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {

        if (myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void openDB() {
        myDataBase = getWritableDatabase();
    }

    public void closeDB() {
        myDataBase.close();
    }


    public ArrayList<MSG_CAT> selectallcat() {
        ArrayList<MSG_CAT> allContacts = new ArrayList<>();

        Cursor C = myDataBase.rawQuery("select * from MSG_CAT", null);

        while (C.moveToNext()) {
            int id = C.getInt(0);
            String name = C.getString(1);
            allContacts.add(new MSG_CAT(id, name));
        }

        return allContacts;
    }

    public void upDate(int idmsg, int fav) {

        myDataBase.execSQL("update MESSAGES set FAV =" + fav + " where ID=" + idmsg);
    }

    public boolean InsertContact(String messages, int s) {
        myDataBase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("MESSAGE", messages);
        contentValues.put("MSG_CAT", s);
        long result = myDataBase.insert("MESSAGES", null, contentValues);
        if (result == 1) {
            return false;
        } else {
            return true;
        }
    }

    public void UpdateContact(String message, int s2) {
        myDataBase.execSQL("update MESSAGES set MESSAGE='" + message + "' where id = " + s2);

    }

    public void DeleteContact(int id) {
        myDataBase.execSQL("delete from MESSAGES where ID =" + id);
    }

    public ArrayList<MESSAGES> selectallmasg(int i, String s) {
        ArrayList<MESSAGES> allContacts = new ArrayList<>();

        Cursor C = myDataBase.rawQuery("select * from MESSAGES where MSG_CAT =" + i, null);

        while (C.moveToNext()) {
            int id = C.getInt(0);
            String name = C.getString(1);
            int color = C.getInt(3);
            allContacts.add(new MESSAGES(id, name, s, color));
        }

        return allContacts;
    }

    public ArrayList<MESSAGES> selectallFav() {
        ArrayList<MESSAGES> allContacts = new ArrayList<>();

        Cursor C = myDataBase.rawQuery("select * from MESSAGES where FAV =1", null);

        while (C.moveToNext()) {
            int id = C.getInt(0);
            String name = C.getString(1);
            allContacts.add(new MESSAGES(id, name, "", 1));
        }

        return allContacts;
    }


}


