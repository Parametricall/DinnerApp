package com.parametricall.dinner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class DinnerOption extends AppCompatActivity {

    Database myDb;
    TextView dinnerOptionName, dinnerOptionIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinner_option);

        myDb = new Database(getApplicationContext());
        dinnerOptionName = findViewById(R.id.dinnerOptionNameTextView);
        dinnerOptionIngredients = findViewById(R.id.dinnerOptionIngredientsTextView);

        ArrayList<String[]> data = myDb.getData();
        String firstName = data.get(0)[0];
        String Ingredient = data.get(0)[1];

        dinnerOptionName.setText(firstName);
        dinnerOptionIngredients.setText(Ingredient);

    }
}
