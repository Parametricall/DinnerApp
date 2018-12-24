package com.parametricall.dinner;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BulletSpan;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class ScrollingDinnerOption extends AppCompatActivity{

    Database myDb;
    TextView dinnerOptionName, dinnerOptionIngredients, cookingInstructions;
    ImageView dinnerOptionImage;
    Integer tableID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling_dinner_option);

        myDb = new Database(getApplicationContext());
        dinnerOptionName = findViewById(R.id.dinnerOptionNameTextView);
        dinnerOptionIngredients = findViewById(R.id.dinnerOptionIngredientsTextView);
        dinnerOptionImage = findViewById(R.id.dinnerOptionImageView);
        cookingInstructions = findViewById(R.id.cookingInstructionsTextView);

        ArrayList<String[]> data = myDb.getData();
        if (!data.isEmpty()) {
            Integer initialDbSize = data.size();

//            final int random  = new Random().nextInt(initialDbSize);
//            tableID = random;

            String firstName = data.get(tableID)[0];
            String ingredients = data.get(tableID)[1];
            String instructions = data.get(tableID)[2];

            String[] ingredientArr = ingredients.split(", ");
//            StringBuffer buffer = new StringBuffer();
//
//            String catString = "";
//
            SpannableString stringEnd = new SpannableString("");

            SpannableStringBuilder stringBuild = new SpannableStringBuilder();
            for (String ingredient : ingredientArr) {
//                buffer.append(ingredient + "\n");
//
//                catString = catString.concat(ingredient + "\n");
//
                SpannableString string = new SpannableString(ingredient + "\n");
                string.setSpan(new BulletSpan(40, getResources().getColor(R.color.colorAccent)), 0, ingredient.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


                stringBuild.append(string);

            }

            dinnerOptionIngredients.setText(stringBuild);

            dinnerOptionName.setText(firstName);
//            dinnerOptionIngredients.setText(ingredient);
            cookingInstructions.setText(instructions);
            if (firstName.equals("Steak and Chips")) {
                dinnerOptionImage.setImageResource(R.drawable.steak_and_chips_edit);
            } else if (firstName.equals("Chicken Curry")) {
                dinnerOptionImage.setImageResource(R.drawable.chicken_curry);
            } else if (firstName.equals("Fish n Chips")) {
                dinnerOptionImage.setImageResource(R.drawable.fish_and_chips);
            } else {
                dinnerOptionImage.setVisibility(View.INVISIBLE);
            }
        } else {
            dinnerOptionName.setText(getString(R.string.empty_database));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dinner_option, menu);
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
            Intent intent = new Intent(this, UpdateDinnerOption.class);
            intent.putExtra("currentDinnerName", dinnerOptionName.getText());
            intent.putExtra("currentDinnerIngredients", dinnerOptionIngredients.getText());
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    @RequiresApi(api = Build.VERSION_CODES.P)
    public void onClickNextDinner(View view) {
        dinnerOptionImage.setVisibility(View.VISIBLE);
        tableID++;

        ArrayList<String[]> data = myDb.getData();

        if (tableID >= data.size()) {
            tableID = 0;
        }
        if (!data.isEmpty() && data.size() > 1) {
            String firstName = data.get(tableID)[0];
            String ingredients = data.get(tableID)[1];
            String instructions = data.get(tableID)[2];

            String[] ingredientArr = ingredients.split(", ");
//            StringBuffer buffer = new StringBuffer();
//
//            String catString = "";
//
            SpannableString stringEnd = new SpannableString("");

            SpannableStringBuilder stringBuild = new SpannableStringBuilder();
            for (String ingredient : ingredientArr) {
//                buffer.append(ingredient + "\n");
//
//                catString = catString.concat(ingredient + "\n");
//
                SpannableString string = new SpannableString(ingredient + "\n");
                string.setSpan(new BulletSpan(40, getResources().getColor(R.color.colorAccent)), 0, ingredient.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


                stringBuild.append(string);

            }

            dinnerOptionIngredients.setText(stringBuild);

//            SpannableString string = new SpannableString(catString);
//            string.setSpan(new BulletSpan(), 1, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//            SpannableString string = new SpannableString("hello");
//
//            string.setSpan(new BulletSpan(), 10, 22, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            dinnerOptionIngredients.setText(string);

            dinnerOptionName.setText(firstName);

            cookingInstructions.setText(instructions);

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
        dinnerOptionImage.setVisibility(View.VISIBLE);
        tableID--;
        ArrayList<String[]> data = myDb.getData();
        if (tableID < 0) {
            tableID = data.size() - 1;
        }
        if (!data.isEmpty()) {
            String firstName = data.get(tableID)[0];
            String ingredients = data.get(tableID)[1];
            String instructions = data.get(tableID)[2];

            String[] ingredientArr = ingredients.split(", ");
//            StringBuffer buffer = new StringBuffer();
//
//            String catString = "";
//
            SpannableString stringEnd = new SpannableString("");

            SpannableStringBuilder stringBuild = new SpannableStringBuilder();
            for (String ingredient : ingredientArr) {
//                buffer.append(ingredient + "\n");
//
//                catString = catString.concat(ingredient + "\n");
//
                SpannableString string = new SpannableString(ingredient + "\n");
                string.setSpan(new BulletSpan(40, getResources().getColor(R.color.colorAccent)), 0, ingredient.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


                stringBuild.append(string);

            }

            dinnerOptionIngredients.setText(stringBuild);

            dinnerOptionName.setText(firstName);
//            dinnerOptionIngredients.setText(Ingredient);
            cookingInstructions.setText(instructions);

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

    public void onSwipeLeftNextDinner() {
        dinnerOptionImage.setVisibility(View.VISIBLE);
        tableID--;
        ArrayList<String[]> data = myDb.getData();
        if (tableID < 0) {
            tableID = data.size() - 1;
        }
        if (!data.isEmpty()) {
            String firstName = data.get(tableID)[0];
            String Ingredient = data.get(tableID)[1];
            String instructions = data.get(tableID)[2];

            dinnerOptionName.setText(firstName);
            dinnerOptionIngredients.setText(Ingredient);
            cookingInstructions.setText(instructions);

            if (firstName.equals("Steak and Chips")) {
                dinnerOptionImage.setImageResource(R.drawable.steak_and_chips_edit);
            } else if (firstName.equals("Chicken Curry")) {
                dinnerOptionImage.setImageResource(R.drawable.chicken_curry);
            } else if (firstName.equals("Fish n Chips")) {
                dinnerOptionImage.setImageResource(R.drawable.fish_and_chips);
            } else {
                dinnerOptionImage.setVisibility(View.INVISIBLE);
            }
        } else {
            dinnerOptionName.setText(getString(R.string.empty_database));
            dinnerOptionIngredients.setText("");
        }
    }
}
