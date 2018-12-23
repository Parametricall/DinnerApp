package com.parametricall.dinner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DinnerOption extends AppCompatActivity {

    Database myDb;
    TextView dinnerOptionName, dinnerOptionIngredients;
    ImageView dinnerOptionImage;
    Integer tableID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinner_option);

        myDb = new Database(getApplicationContext());
        dinnerOptionName = findViewById(R.id.dinnerOptionNameTextView);
        dinnerOptionIngredients = findViewById(R.id.dinnerOptionIngredientsTextView);
        dinnerOptionImage = findViewById(R.id.dinnerOptionImageView);

        Integer initialDbSize = myDb.getData().size();

        ArrayList<String[]> data = myDb.getData();
        if (!data.isEmpty()) {
            String firstName = data.get(tableID)[0];
            String Ingredient = data.get(tableID)[1];

            dinnerOptionName.setText(firstName);
            dinnerOptionIngredients.setText(Ingredient);
            dinnerOptionImage.setImageResource(R.drawable.steak_and_chips_edit);
        } else {
            dinnerOptionName.setText(getString(R.string.empty_database));
        }
    }

    public void onClickNextDinner(View view) {
        tableID++;

        ArrayList<String[]> data = myDb.getData();

        if (tableID >= data.size()) {
            tableID = 0;
        }
        if (!data.isEmpty() && data.size() > 1) {
            String firstName = data.get(tableID)[0];
            String Ingredient = data.get(tableID)[1];

            dinnerOptionName.setText(firstName);
            dinnerOptionIngredients.setText(Ingredient);

            if (firstName.equals("Steak and Chips")) {
                dinnerOptionImage.setImageResource(R.drawable.steak_and_chips_edit);
            } else if (firstName.equals("Chicken Curry")) {
                dinnerOptionImage.setImageResource(R.drawable.chicken_curry);
            } else if (firstName.equals("Fish n Chips")) {
                dinnerOptionImage.setImageResource(R.drawable.fish_and_chips);
            } else {
                dinnerOptionImage.setVisibility(view.INVISIBLE);
            }
        } else {
            dinnerOptionName.setText(getString(R.string.empty_database));
            dinnerOptionIngredients.setText("");
        }
    }

    public void onClickPrevDinner(View view) {
        tableID--;
        ArrayList<String[]> data = myDb.getData();
        if (tableID < 0) {
            tableID = data.size() - 1;
        }
        if (!data.isEmpty()) {
            String firstName = data.get(tableID)[0];
            String Ingredient = data.get(tableID)[1];

            dinnerOptionName.setText(firstName);
            dinnerOptionIngredients.setText(Ingredient);
            if (firstName.equals("Steak and Chips")) {
                dinnerOptionImage.setImageResource(R.drawable.steak_and_chips_edit);
            } else if (firstName.equals("Chicken Curry")) {
                dinnerOptionImage.setImageResource(R.drawable.chicken_curry);
            } else if (firstName.equals("Fish n Chips")) {
                dinnerOptionImage.setImageResource(R.drawable.fish_and_chips);
            } else {
                dinnerOptionImage.setVisibility(view.INVISIBLE);
            }
        } else {
            dinnerOptionName.setText(getString(R.string.empty_database));
            dinnerOptionIngredients.setText("");
        }
    }
}
