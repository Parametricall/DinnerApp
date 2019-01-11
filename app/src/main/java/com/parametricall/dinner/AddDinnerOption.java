package com.parametricall.dinner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class AddDinnerOption extends AppCompatActivity {

    private EditText dinnerOption, dinnerIngredient, dinnerInstructions;
    Database myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String TAG = this.getClass().getName();
        Log.d(TAG, "Creating AddDinnerOption");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dinner_option);

        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dinnerOption = findViewById(R.id.addNewDinnerOptionEditText);
        dinnerIngredient = findViewById(R.id.addNewIngredientEditText);
        dinnerInstructions = findViewById(R.id.newDinnerInstructionsEditText);
        myDb = new Database(this);
    }

    public void addMenuOption(View view) {
        String newDinnerOption = dinnerOption.getText().toString();
        String newDinnerIngredient = dinnerIngredient.getText().toString();
        String newDinnerInstructions = dinnerInstructions.getText().toString();

        myDb.insertData(newDinnerOption, newDinnerIngredient, newDinnerInstructions);
        Message.message(getApplicationContext(), "Insertion Successful");

        dinnerOption.setText("");
        dinnerIngredient.setText("");
        dinnerInstructions.setText("");
    }
}
