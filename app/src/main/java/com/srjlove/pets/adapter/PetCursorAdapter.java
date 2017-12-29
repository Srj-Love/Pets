package com.srjlove.pets.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.srjlove.pets.R;
import com.srjlove.pets.data.PetContract;

/**
 * Created by Suraj on 12/2/2017.
 */

public class PetCursorAdapter extends CursorAdapter {


    public PetCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView name = view.findViewById(R.id.name);
        TextView summery = view.findViewById(R.id.summary);

        name.setText(cursor.getString(cursor.getColumnIndex(PetContract.PetEntry.COLOUMN_PET_NAME)));
        summery.setText(cursor.getString(cursor.getColumnIndex(PetContract.PetEntry.COLOUMN_PET_BREED)));
    }
}
