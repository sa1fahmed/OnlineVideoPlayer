package com.example.onlinevideoplayer.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Saif on 5/12/2019.
 */

public class DBHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "videos_db";

    public static final String TABLE_NAME = "videos";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TIME = "time";


    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                    + COLUMN_ID + " TEXT PRIMARY KEY,"
                    + COLUMN_TIME + " INTEGER"
                    + ")";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public long getLastTime(String id) {

        long time = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " + COLUMN_TIME + " FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = '" + id + "'", null);
        if (c.moveToFirst()) {
            do {
                time = c.getLong(0);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return time;
    }

    public void setLastTime(String id, long time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        contentValue.put(COLUMN_ID, id);
        contentValue.put(COLUMN_TIME, time);
        long insertId = db.insert(TABLE_NAME, null, contentValue);
        if (insertId == -1) {
            db.update(TABLE_NAME, contentValue, COLUMN_ID + "= ?", new String[]{id});
        }
    }
}
