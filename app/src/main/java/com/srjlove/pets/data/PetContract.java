package com.srjlove.pets.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Suraj on 12/2/2017.
 */

public final class PetContract {


    public static final String CONTENT_AUTHORITY = "com.srjlove.pets.provider.PetProvider";
    public static final Uri BASE_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_NAME = PetContract.PetEntry.TABLE_NAME;

    public static abstract class PetEntry implements BaseColumns {

        public final static String TABLE_NAME = "pets";
        public static final Uri CONTENT_URI = BASE_URI.buildUpon().appendPath(PATH_NAME).build();

        public final static String ID = _ID;
        public final static String COLOUMN_PET_NAME = "name";
        public final static String COLOUMN_PET_BREED = "breed";
        public final static String COLOUMN_PET_GENDER = "gender";
        public final static String COLOUMN_PET_WEIGHT = "weight";

        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;
        public static final int GENDER_UNKNOW = 0;

    }
}
