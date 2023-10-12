package com.example.medicinereminder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(@Nullable Context context) {
        super(context, "medicine_database", null , 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE TABLE_MED (Medicine_name TEXT, Date TEXT, Time TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertMedicine(String MedName, String Date, String Time){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("Medicine_name",MedName);
        cv.put("Date",Date);
        cv.put("Time",Time);

        long res = database.insert("TABLE_MED",null,cv);

        if (res != -1){
            return true;
        }
        else{
            return false;
        }

    }

    public Cursor FetchMedicine(String Date, String Time){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM TABLE_MED WHERE Date = '"+Date+"' AND Time = '"+Time+"'",null);
        return c;
    }
}
