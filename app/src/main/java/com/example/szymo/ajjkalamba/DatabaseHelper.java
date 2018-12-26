package com.example.szymo.ajjkalamba;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Records";
    private static final String DB_TABLE = "Words";

    //columns in this table

    private static final String ID = "ID";
    private static final String KATEGORIA = "KATEGORIA";
    private static final String HASLO = "HASLO";

    private static final String CREATE_TABLE =
            "CREATE TABLE "+ DB_TABLE+" ("+
                    ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    KATEGORIA+ " TEXT, "+
                    HASLO+ " TEXT " + ")";

    public DatabaseHelper(Context context){
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+DB_TABLE);
        db.execSQL(CREATE_TABLE);
    }

    //method for data deletion
    public void deleteData(String kategoria, String haslo){

        kategoria =kategoria.toUpperCase();
        haslo = haslo.toUpperCase();

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + DB_TABLE + " WHERE UPPER(" +
            KATEGORIA + ") = '" + kategoria + "' AND UPPER(" +
                HASLO + ") = '" + haslo +"'";
        db.execSQL(query);
    }

    //Method for data insertion
    public boolean insertData(String kategoria, String haslo){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KATEGORIA,kategoria);
        contentValues.put(HASLO,haslo);

        long result = db.insert(DB_TABLE,null,contentValues);

        return result != -1; //Data not inserted
    }

    //Method for viewing data (whole data)
    public Cursor vievData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * from "+DB_TABLE +" ORDER BY "+KATEGORIA;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;

    }

    public void clear() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+DB_TABLE);
    }
}