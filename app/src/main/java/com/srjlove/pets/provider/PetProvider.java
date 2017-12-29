package com.srjlove.pets.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.srjlove.pets.data.PetContract;
import com.srjlove.pets.data.PetDbHelper;

import static com.srjlove.pets.data.PetContract.CONTENT_AUTHORITY;
import static com.srjlove.pets.data.PetContract.PATH_NAME;

/**
 * Created by Suraj on 12/2/2017.
 */

public class PetProvider extends ContentProvider {

    private static final String TAG = PetProvider.class.getSimpleName();
    private static UriMatcher mMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    ;

    private static final int MATCH_PETS = 12;

    private static final int MATCH_PETS_ID = 13;
    PetDbHelper mHelper;

    private static final int MATCH_PETS_STRING = 14;

    static {
        mMatcher.addURI(CONTENT_AUTHORITY, PetContract.PetEntry.TABLE_NAME, MATCH_PETS);
        mMatcher.addURI(CONTENT_AUTHORITY, PATH_NAME + "/#", MATCH_PETS_ID);
        mMatcher.addURI(CONTENT_AUTHORITY, PATH_NAME + "/*", MATCH_PETS_STRING);
    }

    @Override
    public boolean onCreate() {
        mHelper = new PetDbHelper(getContext());
        return true; // initialization compete
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        Cursor mCursor = null;
        switch (mMatcher.match(uri)) {
            case MATCH_PETS:
                Log.d(TAG, "query: URI matches with MATCH_PETS TABLE");
                return queryPetsData(uri);
            case MATCH_PETS_ID:
                Log.d(TAG, "query: URI matches with MATCH_PETS_ID TABLE");
                // do operations
                break;
            default:
                throw new IllegalArgumentException(" unknown uri" + uri.toString());
        }

        return null;
    }

    /**
     * Quailing the pets data
     */
    private Cursor queryPetsData(Uri mUri) {
        SQLiteDatabase mDatabase = mHelper.getReadableDatabase();
        
        return mDatabase.query(PetContract.PetEntry.TABLE_NAME, null, null, null, null, null, null);
//        while (mCursor.moveToNext()) {
//            String data = "\n"+
//                    mCursor.getInt(mCursor.getColumnIndex(PetContract.PetEntry.ID)) + " - " +
//                    mCursor.getString(mCursor.getColumnIndex(PetContract.PetEntry.COLOUMN_PET_NAME)) + " - " +
//                    mCursor.getInt(mCursor.getColumnIndex(PetContract.PetEntry.COLOUMN_PET_GENDER)) + " - " +
//                    mCursor.getInt(mCursor.getColumnIndex(PetContract.PetEntry.COLOUMN_PET_WEIGHT));
//            Log.d(TAG, "queryPetsData: Data : " + data );
//        }

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return insertData(uri, values);

    }

    private Uri insertData(Uri uri, ContentValues values) {
        SQLiteDatabase mDatabase = mHelper.getWritableDatabase();
        long rowId = mDatabase.insert(PetContract.PetEntry.TABLE_NAME, null, values);
        Toast.makeText(getContext(), rowId != -1 ? "Data inserted successfully id: " + rowId : "Data insertion failed", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "insertData: rowid : " + rowId);

        return ContentUris.withAppendedId(uri, rowId);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase mDatabase = mHelper.getWritableDatabase();
        switch (mMatcher.match(uri)) {
            case MATCH_PETS:
                return mDatabase.delete(PetContract.PetEntry.TABLE_NAME, null, null);
            case MATCH_PETS_ID:
                selection = PetContract.PetEntry.COLOUMN_PET_WEIGHT + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                int rowDelted = mDatabase.delete(PetContract.PetEntry.TABLE_NAME, selection, selectionArgs);
                Log.d(TAG, "delete: case MATCH_PETS_ID: rows deleted " + rowDelted);
                return rowDelted;
            case MATCH_PETS_STRING:
                int deleteName = mDatabase.delete(PetContract.PetEntry.TABLE_NAME, selection, selectionArgs);
                Log.d(TAG, "delete: case MATCH_PETS String: rows deleted " + deleteName);


        }
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
