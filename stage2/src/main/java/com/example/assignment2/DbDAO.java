package com.example.assignment2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class DbDAO implements IDAO {

    private Context context;

    public DbDAO(Context ctx){
        context = ctx;
    }

    @Override
    public void save(Hashtable<String, String> attributes) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues content = new ContentValues();
        Enumeration<String> keys = attributes.keys();
        while (keys.hasMoreElements()){
            String key = keys.nextElement();
            content.put(key,attributes.get(key));
        }

        db.insert("SelectedCites",null,content);
    }

    @Override
    public void save(ArrayList<Hashtable<String, String>> objects) {
        for(Hashtable<String,String> obj : objects){
            save(obj);
        }
    }

    @Override
    public ArrayList<Hashtable<String, String>> load() {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM SelectedCites";
        Cursor cursor = db.rawQuery(query,null);

        ArrayList<Hashtable<String,String>> objects = new ArrayList<Hashtable<String, String>>();
        while(cursor.moveToNext()){
            Hashtable<String,String> obj = new Hashtable<String, String>();
            String [] columns = cursor.getColumnNames();
            for (int i = 1; i<4;i++){
                String temp = cursor.getString(i);
                obj.put(columns[i], temp);
            }
            objects.add(obj);
        }

        return objects;
    }

    @Override
    public Hashtable<String, String> load(String id) {
        return null;
    }

    @Override
    public void delete(Hashtable<String, String> attributes) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Enumeration<String> keys = attributes.keys();
        String name = attributes.get(keys.nextElement());

        db.delete("SelectedCites", "Name" + " LIKE '" + name + "'", null);
    }
}