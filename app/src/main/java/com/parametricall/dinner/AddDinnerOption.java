package com.parametricall.dinner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddDinnerOption extends AppCompatActivity {

    private static final String TAG = "qqzzAddDinnerOption";

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private EditText dinnerOption, dinnerIngredient, dinnerInstructions;
    Database myDb;
    ImageView newDinnerImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dinner_option);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dinnerOption = findViewById(R.id.addNewDinnerOptionEditText);
        dinnerIngredient = findViewById(R.id.addNewIngredientEditText);
        dinnerInstructions = findViewById(R.id.newDinnerInstructionsEditText);
        myDb = new Database(this);
        newDinnerImage = findViewById(R.id.newDinnerImageView);
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

    public void takePicture(View view) {
        dispatchTakePictureIntent();
    }

    static final int REQUEST_TAKE_PHOTO = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
//                Uri photoURI = FileProvider.getUriForFile(this,
//                        "com.parametricall.dinner.fileprovider",
//                        photoFile);
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            newDinnerImage.setImageBitmap(imageBitmap);

        }
    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
}
