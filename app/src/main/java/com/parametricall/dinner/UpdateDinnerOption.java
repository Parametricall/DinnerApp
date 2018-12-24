package com.parametricall.dinner;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class UpdateDinnerOption extends AppCompatActivity {

    ImageView dinnerImage;
    Button updateDinnerOption;
    EditText dinnerName, dinnerIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_dinner_option);

        dinnerImage = findViewById(R.id.newDinnerImageView);
        updateDinnerOption = findViewById(R.id.updateDinnerButton);
        dinnerName = findViewById(R.id.updatedDinnerOptionEditText);
        dinnerIngredients = findViewById(R.id.updatedIngredientEditText);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String name = extras.getString("currentDinnerName");
            String ingredients = extras.getString("currentDinnerIngredients");

            dinnerName.setText(name);
            dinnerIngredients.setText(ingredients);
        }


    }
}
