package com.example.searchapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.searchapp.models.University;
import com.example.searchapp.params.Params;
import java.util.ArrayList;
import java.util.List;

public class MyDbHandler extends SQLiteOpenHelper {

    public MyDbHandler(Context context) {
        super(context, Params.DB_NAME, null, Params.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY,%s TEXT, %s TEXT, %s TEXT)", Params.TABLE_NAME, Params.KEY_ID, Params.KEY_NAME, Params.KEY_COUNTRY, "website");
        Log.d("dbharry", "Query being run is : " + create);
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addUniv(List<University> universities) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        for (University university : universities) {
            ContentValues values = new ContentValues();
            values.put(Params.KEY_NAME, university.getName());
            values.put(Params.KEY_COUNTRY, university.getCountry());
            values.put("website", university.getWeb_pages().get(0));
            db.insert(Params.TABLE_NAME, null, values);
        }
        try {
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public List<University> getUniversities() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from univ_table", null);
        List<University> universities = new ArrayList<>();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                List<String> univWebs = new ArrayList<>();
                univWebs.add(cursor.getString(3));
                University university = new University(cursor.getInt(0), cursor.getString(1), cursor.getString(2), univWebs);
                universities.add(university);
            } while (cursor.moveToNext());
        }
        return universities;
    }
    /*Truncate DB
     */
    public void truncateDb() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from univ_table");
    }
}
