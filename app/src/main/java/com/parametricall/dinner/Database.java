package com.parametricall.dinner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class Database {

    private static final String TAG = "qqzzDatabase";

    private myDatabase myhelper;

    Database(Context context) {
        Log.d(TAG, "Creating Database constructor");
        myhelper = new myDatabase(context);
    }

    long insertData(String name) {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDatabase.KEY_TITLE, name);
        long id = dbb.insert(myDatabase.TABLE_MEALS, null, contentValues);
        return id;
    }

    public String[] getData() {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDatabase.KEY_ID, myDatabase.KEY_TITLE};
        Cursor cursor = db.query(myDatabase.TABLE_MEALS, columns, null, null, null, null, null);
        String[] data = new String[cursor.getCount()];
        while (cursor.moveToNext()) {
            int cid = cursor.getInt(cursor.getColumnIndex(myDatabase.KEY_ID));
            String name = cursor.getString(cursor.getColumnIndex(myDatabase.KEY_TITLE));
            data[cid - 1] = name;
        }
        cursor.close();
        return data;
    }


    static class myDatabase extends SQLiteOpenHelper {

        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "dinnerOptions";

        private static final String TABLE_MEALS = "meals";

        private static final String KEY_ID = "id";
        private static final String KEY_TITLE = "title";
        private static final String KEY_INGREDIENTS = "ingredients";

        private myDatabase(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String CREATE_FEEDS_TABLE = "CREATE TABLE " + TABLE_MEALS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT," + KEY_INGREDIENTS + " TEXT" + ")";
            db.execSQL(CREATE_FEEDS_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEALS);

            //create table again
            onCreate(db);
        }
    }
}
