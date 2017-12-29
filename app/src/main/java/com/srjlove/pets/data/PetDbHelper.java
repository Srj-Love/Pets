package com.srjlove.pets.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.srjlove.pets.data.PetContract.PetEntry;

/**
 * Created by Suraj on 12/2/2017.
 */

public class PetDbHelper extends SQLiteOpenHelper {


    private static final String DB_NAME = "pets.db";
    private static final int DB_VERSION = 1;
    private final String CREATE_PET_TABLE = "CREATE TABLE " + PetEntry.TABLE_NAME + " ( " +
            PetEntry.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            PetEntry.COLOUMN_PET_NAME + " TEXT NOT NULL, " +
            PetEntry.COLOUMN_PET_BREED + " TEXT NOT NULL, " +
            PetEntry.COLOUMN_PET_GENDER + " INTEGER NOT NULL, " +
            PetEntry.COLOUMN_PET_WEIGHT + " INTEGER NOT NULL DEFAULT 0 );";

    public PetDbHelper(Context contex) {
        super(contex, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PET_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PetEntry.TABLE_NAME);
    }
}

