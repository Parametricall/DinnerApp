package com.parametricall.dinner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class UpdateDinnerOption extends AppCompatActivity {

    Database myDb;

    ImageView dinnerImage;
    Button updateDinnerOption;
    EditText dinnerName, dinnerIngredients;

    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_dinner_option);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myDb = new Database(this);

        dinnerImage = findViewById(R.id.newDinnerImageView);
        updateDinnerOption = findViewById(R.id.updateDinnerButton);
        dinnerName = findViewById(R.id.updatedDinnerOptionEditText);
        dinnerIngredients = findViewById(R.id.updatedIngredientEditText);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            name = extras.getString("currentDinnerName");
            String ingredients = extras.getString("currentDinnerIngredients");

            dinnerName.setText(name);
            dinnerIngredients.setText(ingredients);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void delete(View view) {
        String name = dinnerName.getText().toString();
        myDb.delete(name);
        Message.message(getApplicationContext(), "Deletion Successful");

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
