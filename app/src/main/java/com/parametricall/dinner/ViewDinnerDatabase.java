package com.parametricall.dinner;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ViewDinnerDatabase extends AppCompatActivity {
    TextView viewDbTextView;
    Database myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String TAG = this.getClass().getName();
        Log.d(TAG, "Creating ViewDinnerDatabase");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_dinner_database);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myDb = new Database(this);
        viewDbTextView = findViewById(R.id.viewDbTextView);
    }


    public void getAndSetData(View view) {

        String name = "Empty Database";
        StringBuilder buffer = new StringBuilder();

        Cursor cursor = myDb.getData();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                if (cursor.getType(1) == cursor.FIELD_TYPE_STRING) {
                    name = cursor.getString(1);
                    buffer.append(name);
                    buffer.append("\n");
                }
                cursor.moveToNext();
            }
            viewDbTextView.setText(buffer);
        } else {
            viewDbTextView.setText(name);
        }
    }

    public void clearDatabase(View view) {
        myDb.clearDatabase();
    }
}
