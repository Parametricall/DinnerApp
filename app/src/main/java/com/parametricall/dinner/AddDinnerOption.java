package com.parametricall.dinner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class AddDinnerOption extends AppCompatActivity {

    private static final String TAG = "qqzzAddDinnerOption";

    private EditText dinnerOption;
    Database myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dinner_option);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dinnerOption = findViewById(R.id.dinnerOptionEditText);
        myDb = new Database(this);
    }

    public void addMenuOption(View view) {
        String newDinnerOption = dinnerOption.getText().toString();
        myDb.insertData(newDinnerOption);
        Message.message(getApplicationContext(), "Insertion Successful");
        dinnerOption.setText("");
    }

    public String viewData(View view) {
        String[] data = myDb.getData();

        StringBuilder buffer = new StringBuilder();
        for (String aData : data) {
            buffer.append(aData).append("\n");
        }

        Message.message(this, buffer.toString());
        return data[0];
    }
}
