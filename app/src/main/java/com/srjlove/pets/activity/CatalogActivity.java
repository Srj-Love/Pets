package com.srjlove.pets.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.srjlove.pets.R;
import com.srjlove.pets.adapter.PetCursorAdapter;
import com.srjlove.pets.data.PetContract.PetEntry;
import com.srjlove.pets.data.PetDbHelper;

public class CatalogActivity extends AppCompatActivity {

    private static final String TAG = CatalogActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

    }

    private void displayDatabaseInfo() {
        Log.d(TAG, "displayDatabaseInfo: quarrying raw database info");

        ListView displayView = findViewById(R.id.lv);

        PetDbHelper mHolder = new PetDbHelper(this);
        SQLiteDatabase mDatabase = mHolder.getReadableDatabase();

        Cursor mCursor = getContentResolver().query(PetEntry.CONTENT_URI, null, null, null, null);
        displayView.setAdapter(new PetCursorAdapter(this,mCursor));
        assert mCursor != null;
        mCursor.close(

        );

    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo(); // when user come from Editor Activity
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertDummyData();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                deleteAllPets();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    private void deleteAllPets() {
        int deletedrows = getContentResolver().delete(PetEntry.CONTENT_URI,null,null);
        Toast.makeText(this, deletedrows != -1 ? "Data Deleted successfully id: " + deletedrows : "Data deltetion successfully ", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "deleteAllPets: data deleted");
    }

    /**
     * Inserting Dummy data
     */
    private void insertDummyData() {
        SQLiteDatabase mDatabase = new PetDbHelper(this).getWritableDatabase();

        ContentValues mValues = new ContentValues();
        mValues.put(PetEntry.COLOUMN_PET_NAME, "Doggay");
        mValues.put(PetEntry.COLOUMN_PET_BREED, "breed1");
        mValues.put(PetEntry.COLOUMN_PET_GENDER, 1);
        mValues.put(PetEntry.COLOUMN_PET_WEIGHT, 23);

        long rowId = mDatabase.insert(PetEntry.TABLE_NAME, null, mValues);
        Log.d(TAG, "insertDummyData: row inserted at : " + rowId);
        displayDatabaseInfo();
    }
}
