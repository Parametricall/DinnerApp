package com.parametricall.dinner;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;


public class MainActivity extends AppCompatActivity {
    Database myDb;
    Cursor dbCursor;
    Button randomDinnerButton, whatsForDinnerButton;
    Random rand;
    Integer[] randomArr;
    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String TAG = this.getClass().getName();
        Log.d(TAG, "Creating Main Activity");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rand = new Random();

        myDb = new Database(this);
        whatsForDinnerButton = findViewById(R.id.whatsForDinnerButton);
        randomDinnerButton = findViewById(R.id.randomDinnerButton);

        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(fab.getContext(), AddDinnerOption.class);
                startActivity(intent);
            }
        });

        dbCursor = myDb.getData();

        if (dbCursor.getCount() == 0) {
            randomDinnerButton.setEnabled(false);
            randomDinnerButton.setVisibility(View.INVISIBLE);
            whatsForDinnerButton.setText(R.string.add_first_recipe);
            whatsForDinnerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(
                            whatsForDinnerButton.getContext(),
                            AddDinnerOption.class
                    );
                    startActivity(intent);
                }
            });
        } else {
            randomArr = new Integer[dbCursor.getCount()];
            for (int i = 0; i < dbCursor.getCount(); i++) {
                randomArr[i] = i;
            }

            Collections.shuffle(Arrays.asList(randomArr));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, ViewDinnerDatabase.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getDinnerOption(View view) {
        String dinner;
        randomDinnerButton.setEnabled(true);
        int number = 0;

        if (randomArr.length > 0) {
            if (index == randomArr.length) {
                index = 0;
            }
            number = randomArr[index];
            index++;
        }

        if (number <= dbCursor.getCount()) {
            dbCursor.moveToPosition(number);
            dinner = dbCursor.getString(1);
        } else {
            dinner = "number larger than table size";
        }
        randomDinnerButton.setText(dinner);
    }

    public void goToDinnerOption(View view) {
        String dinnerName = randomDinnerButton.getText().toString();

        Intent intent = new Intent(this, DinnerOption.class);
        intent.putExtra("name", dinnerName);
        startActivity(intent);
    }
}
