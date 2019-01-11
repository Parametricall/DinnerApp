package com.parametricall.dinner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


class Database {

    private static final String TAG = Database.class.getName();
    private myDatabase myHelper;

    Database(Context context) {
        Log.d(TAG, "Creating Database constructor");
        myHelper = new myDatabase(context);
    }

    void insertData(String name, String ingredients, String instructions) {
        SQLiteDatabase db = myHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(myDatabase.KEY_NAME, name);
        contentValues.put(myDatabase.KEY_INGREDIENTS, ingredients);
        contentValues.put(myDatabase.KEY_INSTRUCTIONS, instructions);

        db.insert(myDatabase.TABLE_NAME, null, contentValues);
    }

    Cursor getData() {
        SQLiteDatabase db = myHelper.getWritableDatabase();

        String tableName = myDatabase.TABLE_NAME;
        String[] columns = {
                myDatabase.KEY_ID,
                myDatabase.KEY_NAME,
                myDatabase.KEY_INGREDIENTS,
                myDatabase.KEY_INSTRUCTIONS
        };

        return db.query(
                tableName,
                columns,
                null,
                null,
                null,
                null,
                null
        );
    }

    void delete(String name) {
        SQLiteDatabase db = myHelper.getWritableDatabase();
        String[] whereArgs = {name};
        db.delete(myDatabase.TABLE_NAME, myDatabase.KEY_NAME + " = ?", whereArgs);
    }

    void clearDatabase() {
        SQLiteDatabase db = myHelper.getWritableDatabase();
        db.delete(myDatabase.TABLE_NAME, null, null);
    }

    Cursor getDinnerFromName(String name) {
        SQLiteDatabase db = myHelper.getReadableDatabase();

        String table_name = myDatabase.TABLE_NAME;
//        String[] column = {myDatabase.KEY_NAME};
        String condition_column_name = myDatabase.KEY_NAME;
        String[] whereArgs = {name};

        String query = "SELECT * FROM " + table_name + " WHERE " + condition_column_name + " = ?";
        return db.rawQuery(query, whereArgs, null);
    }

    static class myDatabase extends SQLiteOpenHelper {

        private static final int DATABASE_VERSION = 5;
        private static final String DATABASE_NAME = "dinnerOptions";

        private static final String TABLE_NAME = "meals";

        private static final String KEY_ID = "_id";
        private static final String KEY_NAME = "title";
        private static final String KEY_INGREDIENTS = "ingredients";
        private static final String KEY_INSTRUCTIONS = "instructions";

        private myDatabase(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String CREATE_FEEDS_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_NAME + " TEXT, " +
                    KEY_INGREDIENTS + " TEXT, " +
                    KEY_INSTRUCTIONS + " TEXT" +
                    ");";
            db.execSQL(CREATE_FEEDS_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

            //create table again
            onCreate(db);
        }
    }
}


//    public void insertImage(int id, Bitmap img) {
//        byte[] data = getBitmapAsByteArray(img);
//    }
//
//    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, outputStream);
//        return outputStream.toByteArray();
//    }

//    public ArrayList<String[]> getData() {
//        SQLiteDatabase db = myHelper.getWritableDatabase();
//
//        String tableName = myDatabase.TABLE_NAME;
//        String[] columns = {myDatabase.KEY_ID, myDatabase.KEY_NAME, myDatabase.KEY_INGREDIENTS, myDatabase.KEY_INSTRUCTIONS};
//
//        Cursor cursor = db.query(tableName, columns, null, null, null, null, null);
//
//        ArrayList<String[]> myArr = new ArrayList<>();
//        while (cursor.moveToNext()) {
//            String name = cursor.getString(cursor.getColumnIndex(myDatabase.KEY_NAME));
//            String ingredients = cursor.getString(cursor.getColumnIndex(myDatabase.KEY_INGREDIENTS));
//            String instructions = cursor.getString(cursor.getColumnIndex(myDatabase.KEY_INSTRUCTIONS));
//
//            String[] info = {name, ingredients, instructions};
//            myArr.add(info);
//        }
//        cursor.close();
//        return myArr;
//    }
