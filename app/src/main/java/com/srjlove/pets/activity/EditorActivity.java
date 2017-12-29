package com.srjlove.pets.activity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.srjlove.pets.R;
import com.srjlove.pets.data.PetContract;
import com.srjlove.pets.data.PetDbHelper;

import static com.srjlove.pets.data.PetContract.PetEntry.COLOUMN_PET_BREED;
import static com.srjlove.pets.data.PetContract.PetEntry.COLOUMN_PET_GENDER;
import static com.srjlove.pets.data.PetContract.PetEntry.COLOUMN_PET_NAME;
import static com.srjlove.pets.data.PetContract.PetEntry.COLOUMN_PET_WEIGHT;
import static com.srjlove.pets.data.PetContract.PetEntry.GENDER_FEMALE;
import static com.srjlove.pets.data.PetContract.PetEntry.GENDER_MALE;
import static com.srjlove.pets.data.PetContract.PetEntry.GENDER_UNKNOW;

/**
 * Created by Suraj on 12/1/2017.
 */

public class EditorActivity extends AppCompatActivity {

    private static final String TAG = EditorActivity.class.getSimpleName();
    /**
     * EditText field to enter the pet's name
     */
    private EditText mNameEditText;

    /**
     * EditText field to enter the pet's breed
     */
    private EditText mBreedEditText;

    /**
     * EditText field to enter the pet's weight
     */
    private EditText mWeightEditText;

    /**
     * EditText field to enter the pet's gender
     */
    private Spinner mGenderSpinner;

    /**
     * Gender of the pet. The possible values are:
     * 0 for unknown gender, 1 for male, 2 for female.
     */
    private int mGender = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Find all relevant views that we will need to read user input from
        mNameEditText = (EditText) findViewById(R.id.edit_pet_name);
        mBreedEditText = (EditText) findViewById(R.id.edit_pet_breed);
        mWeightEditText = (EditText) findViewById(R.id.edit_pet_weight);
        mGenderSpinner = (Spinner) findViewById(R.id.spinner_gender);

        setupSpinner();
    }

    /**
     * Setup the dropdown spinner that allows the user to select the gender of the pet.
     */
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gender_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mGenderSpinner.setAdapter(genderSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.gender_male))) {
                        mGender = GENDER_MALE; // Male
                    } else if (selection.equals(getString(R.string.gender_female))) {
                        mGender = GENDER_FEMALE; // Female
                    } else {
                        mGender = GENDER_UNKNOW; // Unknown
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mGender = 0; // Unknown
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                insertPetData();
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                deleteRow();
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteRow() {
        String selection = PetContract.PetEntry.COLOUMN_PET_NAME + "=?";
        String selectionArgs[] = {mNameEditText.getText().toString()};
        int deletedrows = getContentResolver().delete(PetContract.PetEntry.CONTENT_URI, selection, selectionArgs);
        Toast.makeText(this, deletedrows != -1 ? "Data Deleted successfully id: " + deletedrows : "Data deltetion successfully ", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "deleteRows: data deleted");

    }

    private void insertPetData() {
        SQLiteDatabase mDatabase = new PetDbHelper(this).getWritableDatabase();

        String name = mNameEditText.getText().toString().trim(); // will remove extra spaces if there any
        String breed = mBreedEditText.getText().toString().trim(); // will remove extra spaces if there any
        int gender = mGender;
        int weight = Integer.parseInt(mWeightEditText.getText().toString());

        ContentValues mValues = new ContentValues();
        mValues.put(COLOUMN_PET_NAME, name);
        mValues.put(COLOUMN_PET_BREED, breed);
        mValues.put(COLOUMN_PET_GENDER, gender);
        mValues.put(COLOUMN_PET_WEIGHT, weight);

        Uri mUri = getContentResolver().insert(PetContract.PetEntry.CONTENT_URI, mValues);
        // Log.d(TAG, "insertDummyData: row inserted at : " + rowId);
        //String s = rowId!= -1 ? "Data inserted successfully id" : "Data inserted successfully id";

//        Toast.makeText(this, rowId != -1 ? "Data inserted successfully id: " + rowId : "Data inserted successfully id", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "insertPetData: UrI: " + mUri);
    }
}
