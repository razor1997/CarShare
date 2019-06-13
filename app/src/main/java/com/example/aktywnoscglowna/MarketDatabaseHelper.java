package com.example.aktywnoscglowna;

import android.content.ContentValues;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

public class MarketDatabaseHelper extends SQLiteOpenHelper {
    private static final String DBNAME="MarketStand"; //Nazwa bazy
    private static int DBVER=2; //Wersja bazy

    MarketDatabaseHelper(Context context){
        super(context,DBNAME,null,DBVER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sqlString=
                "CREATE TABLE IF NOT EXISTS STAND (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "NAME TEXT)";
        sqLiteDatabase.execSQL(sqlString);

        ContentValues itemValues = new ContentValues();
        for(int i=0; i<MojaAplikacja.SIZE; i++){
            itemValues.clear();
            itemValues.put("NAME",MojaAplikacja.NAME[i]);
            sqLiteDatabase.insert("STAND",null,itemValues);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
/*
        String sqlString= "DROP TABLE STAND";
        sqLiteDatabase.execSQL(sqlString);
*/
        String sqlString="ALTER TABLE STAND ADD COLUMN DESCRIPTION VARCHAR(255)";
        sqLiteDatabase.execSQL(sqlString);

        ContentValues itemValues = new ContentValues();
        for(int i=0; i<MojaAplikacja.SIZE; i++){
            itemValues.clear();
            itemValues.put("NAME",MojaAplikacja.NAME[i]);
            itemValues.put("DESCRIPTION",MojaAplikacja.DESCRIPTION[i]);
            //sqLiteDatabase.insert("STAND",null,itemValues);
            sqLiteDatabase.update(
                    "STAND",
                    itemValues,
                    "_id = ?",
                    new String[]{Integer.toString(i+1)});
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    public void addFullEntry(String title, String description){

        ContentValues itemValues = new ContentValues();
        itemValues.clear();
        itemValues.put("NAME",title);
        itemValues.put("DESCRIPTION",description);
        getWritableDatabase().insert("STAND",null,itemValues);
    }
    /*
    public void fullDelete(int id){
        try{
        getWritableDatabase().delete(
                "STAND",
                "_id = ?",
                new String[]{Integer.toString(id+1)});
        }
        catch (Exception e){
            Log.d("pfffffff", e.getMessage());
        }
    }
*/
    public void addDescription(String description, int id){
        ContentValues itemValues = new ContentValues();
        itemValues.clear();
        itemValues.put("DESCRIPTION",description);
        getWritableDatabase().update(
                "STAND",
                itemValues,
                "_id = ? ",
                new String[]{Integer.toString(id+1)});
    }

    public void addBasicEntry(String title){

        ContentValues itemValues = new ContentValues();
        itemValues.clear();
        itemValues.put("NAME",title);
        getWritableDatabase().insert("STAND",null,itemValues);
    }


    public static int getDBVER() {
        return DBVER;
    }
}