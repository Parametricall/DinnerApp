package com.parametricall.dinner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class AddDinnerOption extends AppCompatActivity {

    private static final String TAG = "qqzzAddDinnerOption";

    private EditText dinnerOption, dinnerIngredient;
    Database myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dinner_option);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dinnerOption = findViewById(R.id.addNewDinnerOptionEditText);
        dinnerIngredient = findViewById(R.id.addNewIngredientEditText);
        myDb = new Database(this);
    }

    public void addMenuOption(View view) {
        String newDinnerOption = dinnerOption.getText().toString();
        String newDinnerIngredient = dinnerIngredient.getText().toString();
        myDb.insertData(newDinnerOption, newDinnerIngredient);
        Message.message(getApplicationContext(), "Insertion Successful");
        dinnerOption.setText("");
        dinnerIngredient.setText("");
    }
}
