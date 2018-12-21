package com.parametricall.dinner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ViewDinnerDatabase extends AppCompatActivity {

    private static final String TAG = "qqzzView Dinner Databa";

    TextView viewDbTextView;
    Database myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_dinner_database);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewDbTextView = (TextView) findViewById(R.id.viewDbTextView);
        myDb = new Database(this);
    }


    public void getAndSetData(View view) {
        String[] data = myDb.getData();

        StringBuffer buffer = new StringBuffer();
        for (String aData : data) {
            buffer.append(aData).append("\n");
        }
        viewDbTextView.setText(buffer);
    }
}
