package com.example.assignment3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Cites2.db";
    public DbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db){
        String sql = "CREATE TABLE SelectedCites (Id TEXT PRIMARY KEY, " +
                "Name TEXT," +
                "Time TEXT," +
                "BehindOrAhead TEXT)";
        db.execSQL(sql);
        sql = "CREATE TABLE allCites (Id TEXT PRIMARY KEY, " +
                "Name TEXT," +
                "Time TEXT," +
                "BehindOrAhead TEXT)";
        db.execSQL(sql);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS SelectedCites");
        db.execSQL("DROP TABLE IF EXISTS allCites");
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db,oldVersion,newVersion);
    }
}