package com.parametricall.dinner;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BulletSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DinnerOption extends AppCompatActivity {
    Database myDb;
    TextView dinnerOptionName, dinnerOptionIngredients, cookingInstructions;
    ImageView dinnerOptionImage;
    Cursor dbCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String TAG = this.getClass().getName();
        Log.d(TAG, "Creating DinnerOption");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinner_option);
        Bundle bundle = getIntent().getExtras();

        String dinnerName = "";
        if (bundle != null && bundle.get("name") != null) {
            dinnerName = bundle.getString("name");
        }

        myDb = new Database(this);
        dbCursor = myDb.getData();

        dinnerOptionName = findViewById(R.id.dinnerOptionNameTextView);
        dinnerOptionIngredients = findViewById(R.id.dinnerOptionIngredientsTextView);
        dinnerOptionImage = findViewById(R.id.dinnerOptionImageView);
        cookingInstructions = findViewById(R.id.cookingInstructionsTextView);

        Cursor cursor = myDb.getDinnerFromName(dinnerName);
        cursor.moveToFirst();
        showDinner(cursor);

        dbCursor.moveToFirst();
        while (!dbCursor.getString(1).equals(dinnerName)) {
            dbCursor.moveToNext();
            if (dbCursor.isAfterLast()) {
                break;
            }
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

    public void onClickNextDinner(View view) {
        if (dbCursor.isLast()) {
            dbCursor.moveToFirst();
        } else {
            dbCursor.moveToNext();
        }
        showDinner(dbCursor);
    }

    public void onClickPrevDinner(View view) {
        if (dbCursor.isFirst()) {
            dbCursor.moveToLast();
        } else {
            dbCursor.moveToPrevious();
        }
        showDinner(dbCursor);
    }

    public void showDinner(Cursor cursor) {
        String name = "";
        String ingredients = "";
        String instructions = "";

        if (cursor.getType(1) == Cursor.FIELD_TYPE_STRING) {
            name = cursor.getString(1);
            ingredients = cursor.getString(2);
            instructions = cursor.getString(3);
        }

        String[] ingredientArr = ingredients.split(", ");

        SpannableStringBuilder stringBuild = new SpannableStringBuilder();
        for (String ingredient : ingredientArr) {
            SpannableString string = new SpannableString(ingredient + "\n");
            string.setSpan(
                    new BulletSpan(40, getResources().getColor(R.color.colorAccent)),
                    0,
                    ingredient.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
            stringBuild.append(string);
        }

        dinnerOptionName.setText(name);
        dinnerOptionIngredients.setText(stringBuild);
        cookingInstructions.setText(instructions);
        dinnerOptionImage.setVisibility(View.VISIBLE);

        switch (name) {
            case "Steak and Chips":
                dinnerOptionImage.setImageResource(R.drawable.steak_and_chips_edit);
                break;
            case "Chicken Curry":
                dinnerOptionImage.setImageResource(R.drawable.chicken_curry);
                break;
            case "Fish n Chips":
                dinnerOptionImage.setImageResource(R.drawable.fish_and_chips);
                break;
            default:
                dinnerOptionImage.setVisibility(View.INVISIBLE);
                break;
        }

    }
}


//
//
//public class DinnerOption extends AppCompatActivity{
//
//    private static final String TAG = "DinnerOption";
//
//
//    Database myDb;
//    TextView dinnerOptionName, dinnerOptionIngredients, cookingInstructions;
//    ImageView dinnerOptionImage;
//    Integer tableID = 0;
//
//    Cursor dbCursor;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        Log.d(TAG, "DinnerOption");
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_dinner_option);
//        Bundle bundle = getIntent().getExtras();
//
//        String dinnerName = "";
////        if (bundle.get("row_id") != null) {
////           tableID = bundle.getInt("row_id");
////        }
//        if (bundle.get("name") != null) {
//            dinnerName = bundle.getString("name");
//        }
//
//        Log.d(TAG, "DinnerOption Table_ID: " + tableID);
//
//        myDb = new Database(this);
//        dinnerOptionName = findViewById(R.id.dinnerOptionNameTextView);
//        dinnerOptionIngredients = findViewById(R.id.dinnerOptionIngredientsTextView);
//        dinnerOptionImage = findViewById(R.id.dinnerOptionImageView);
//        cookingInstructions = findViewById(R.id.cookingInstructionsTextView);
//
////        dbCursor = myDb.getData();
////        dbCursor.moveToPosition(tableID);
////        showDinner(dbCursor);
//        dbCursor = myDb.getData();
//        Cursor cursor = myDb.getDinnerFromName(dinnerName);
//        tableID = myDb.getIdFromName(dinnerName);
//        cursor.moveToFirst();
////        tableID = cursor.getInt(0); // Returns dinner _ID. Does not mean row in cursor (If fields have been deleted then _ID will be greater than row).
//        showDinner(cursor);
//
////        String name = dbCursor.getString(1);
////        dinnerOptionName.setText(name);
//
////        ArrayList<String[]> data = myDb.getData();
////        if (!data.isEmpty()) {
////            Integer initialDbSize = data.size();
////
//////            final int random  = new Random().nextInt(initialDbSize);
//////            tableID = random;
////
////            String firstName = data.get(tableID)[0];
////            String ingredients = data.get(tableID)[1];
////            String instructions = data.get(tableID)[2];
////
////            String[] ingredientArr = ingredients.split(", ");
//////            StringBuffer buffer = new StringBuffer();
//////
//////            String catString = "";
//////
////            SpannableString stringEnd = new SpannableString("");
////
////            SpannableStringBuilder stringBuild = new SpannableStringBuilder();
////            for (String ingredient : ingredientArr) {
//////                buffer.append(ingredient + "\n");
//////
//////                catString = catString.concat(ingredient + "\n");
//////
////                SpannableString string = new SpannableString(ingredient + "\n");
////                string.setSpan(new BulletSpan(40, getResources().getColor(R.color.colorAccent)), 0, ingredient.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
////
////
////                stringBuild.append(string);
////
////            }
////
////            dinnerOptionIngredients.setText(stringBuild);
//
////            dinnerOptionName.setText(firstName);
//////            dinnerOptionIngredients.setText(ingredient);
////            cookingInstructions.setText(instructions);
////            if (firstName.equals("Steak and Chips")) {
////                dinnerOptionImage.setImageResource(R.drawable.steak_and_chips_edit);
////            } else if (firstName.equals("Chicken Curry")) {
////                dinnerOptionImage.setImageResource(R.drawable.chicken_curry);
////            } else if (firstName.equals("Fish n Chips")) {
////                dinnerOptionImage.setImageResource(R.drawable.fish_and_chips);
////            } else {
////                dinnerOptionImage.setVisibility(View.INVISIBLE);
////            }
////        } else {
////            dinnerOptionName.setText(getString(R.string.empty_database));
////        }
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_dinner_option, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            Intent intent = new Intent(this, UpdateDinnerOption.class);
//            intent.putExtra("currentDinnerName", dinnerOptionName.getText());
//            intent.putExtra("currentDinnerIngredients", dinnerOptionIngredients.getText());
//            startActivity(intent);
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    public void showDinner(Cursor cursor) {
//        String name = "";
//        Log.d(TAG, "DinnerOption showDinner");
//        if (cursor.getType(1) == Cursor.FIELD_TYPE_STRING)
//            name = cursor.getString(1);
//        Log.d(TAG, "DinnerOption got Name");
////        String ingredients = cursor.getString(2);
////        String instructions = cursor.getString(3);
//
//        dinnerOptionName.setText(name);
//
//        if (name.equals("Steak and Chips")) {
//            dinnerOptionImage.setImageResource(R.drawable.steak_and_chips_edit);
//        } else if (name.equals("Chicken Curry")) {
//            dinnerOptionImage.setImageResource(R.drawable.chicken_curry);
//        } else if (name.equals("Fish n Chips")) {
//            dinnerOptionImage.setImageResource(R.drawable.fish_and_chips);
//        } else {
//            dinnerOptionImage.setVisibility(View.INVISIBLE);
//        }
//    }
//
////    @RequiresApi(api = Build.VERSION_CODES.P)
//    public void onClickNextDinner(View view) {
//        dbCursor.moveToPosition(tableID);
//        if (dbCursor.isLast()) {
//            dbCursor.moveToFirst();
//        } else {
//            dbCursor.moveToNext();
//        }
//        showDinner(dbCursor);
//
//
////        dinnerOptionImage.setVisibility(View.VISIBLE);
////        tableID++;
////
////        ArrayList<String[]> data = myDb.getData();
////
////        if (tableID >= data.size()) {
////            tableID = 0;
////        }
////        if (!data.isEmpty() && data.size() > 1) {
////            String firstName = data.get(tableID)[0];
////            String ingredients = data.get(tableID)[1];
////            String instructions = data.get(tableID)[2];
////
////            String[] ingredientArr = ingredients.split(", ");
//////            StringBuffer buffer = new StringBuffer();
//////
//////            String catString = "";
//////
////            SpannableString stringEnd = new SpannableString("");
////
////            SpannableStringBuilder stringBuild = new SpannableStringBuilder();
////            for (String ingredient : ingredientArr) {
//////                buffer.append(ingredient + "\n");
//////
//////                catString = catString.concat(ingredient + "\n");
//////
////                SpannableString string = new SpannableString(ingredient + "\n");
////                string.setSpan(new BulletSpan(40, getResources().getColor(R.color.colorAccent)), 0, ingredient.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
////
////
////                stringBuild.append(string);
////
////            }
////
////            dinnerOptionIngredients.setText(stringBuild);
////
//////            SpannableString string = new SpannableString(catString);
//////            string.setSpan(new BulletSpan(), 1, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//////
//////            SpannableString string = new SpannableString("hello");
//////
//////            string.setSpan(new BulletSpan(), 10, 22, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//////            dinnerOptionIngredients.setText(string);
////
////            dinnerOptionName.setText(firstName);
////
////            cookingInstructions.setText(instructions);
////
////            if (firstName.equals("Steak and Chips")) {
////                dinnerOptionImage.setImageResource(R.drawable.steak_and_chips_edit);
////            } else if (firstName.equals("Chicken Curry")) {
////                dinnerOptionImage.setImageResource(R.drawable.chicken_curry);
////            } else if (firstName.equals("Fish n Chips")) {
////                dinnerOptionImage.setImageResource(R.drawable.fish_and_chips);
////            } else {
////                dinnerOptionImage.setVisibility(view.INVISIBLE);
////            }
////        } else {
////            dinnerOptionName.setText(getString(R.string.empty_database));
////            dinnerOptionIngredients.setText("");
////        }
//    }
//
//    public void onClickPrevDinner(View view) {
//
//        if (dbCursor.isFirst()) {
//            dbCursor.moveToLast();
//        } else {
//            dbCursor.moveToPrevious();
//        }
//        showDinner(dbCursor);
//
//
////        dinnerOptionImage.setVisibility(View.VISIBLE);
////        tableID--;
////        ArrayList<String[]> data = myDb.getData();
////        if (tableID < 0) {
////            tableID = data.size() - 1;
////        }
////        if (!data.isEmpty()) {
////            String firstName = data.get(tableID)[0];
////            String ingredients = data.get(tableID)[1];
////            String instructions = data.get(tableID)[2];
////
////            String[] ingredientArr = ingredients.split(", ");
//////            StringBuffer buffer = new StringBuffer();
//////
//////            String catString = "";
//////
////            SpannableString stringEnd = new SpannableString("");
////
////            SpannableStringBuilder stringBuild = new SpannableStringBuilder();
////            for (String ingredient : ingredientArr) {
//////                buffer.append(ingredient + "\n");
//////
//////                catString = catString.concat(ingredient + "\n");
//////
////                SpannableString string = new SpannableString(ingredient + "\n");
////                string.setSpan(new BulletSpan(40, getResources().getColor(R.color.colorAccent)), 0, ingredient.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
////
////
////                stringBuild.append(string);
////
////            }
////
////            dinnerOptionIngredients.setText(stringBuild);
////
////            dinnerOptionName.setText(firstName);
//////            dinnerOptionIngredients.setText(Ingredient);
////            cookingInstructions.setText(instructions);
////
////            if (firstName.equals("Steak and Chips")) {
////                dinnerOptionImage.setImageResource(R.drawable.steak_and_chips_edit);
////            } else if (firstName.equals("Chicken Curry")) {
////                dinnerOptionImage.setImageResource(R.drawable.chicken_curry);
////            } else if (firstName.equals("Fish n Chips")) {
////                dinnerOptionImage.setImageResource(R.drawable.fish_and_chips);
////            } else {
////                dinnerOptionImage.setVisibility(view.INVISIBLE);
////            }
////        } else {
////            dinnerOptionName.setText(getString(R.string.empty_database));
////            dinnerOptionIngredients.setText("");
////        }
//    }
//
//    public void onSwipeLeftNextDinner() {
//        dinnerOptionImage.setVisibility(View.VISIBLE);
//        tableID--;
//        ArrayList<String[]> data = myDb.getData();
//        if (tableID < 0) {
//            tableID = data.size() - 1;
//        }
//        if (!data.isEmpty()) {
//            String firstName = data.get(tableID)[0];
//            String Ingredient = data.get(tableID)[1];
//            String instructions = data.get(tableID)[2];
//
//            dinnerOptionName.setText(firstName);
//            dinnerOptionIngredients.setText(Ingredient);
//            cookingInstructions.setText(instructions);
//
//            if (firstName.equals("Steak and Chips")) {
//                dinnerOptionImage.setImageResource(R.drawable.steak_and_chips_edit);
//            } else if (firstName.equals("Chicken Curry")) {
//                dinnerOptionImage.setImageResource(R.drawable.chicken_curry);
//            } else if (firstName.equals("Fish n Chips")) {
//                dinnerOptionImage.setImageResource(R.drawable.fish_and_chips);
//            } else {
//                dinnerOptionImage.setVisibility(View.INVISIBLE);
//            }
//        } else {
//            dinnerOptionName.setText(getString(R.string.empty_database));
//            dinnerOptionIngredients.setText("");
//        }
//    }
//}
