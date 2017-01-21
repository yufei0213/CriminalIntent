package com.bignerdanch.android.criminalintent.database;

import android.database.Cursor;

/**
 * Created by yufei0213 on 16/12/22.
 */

public class CrimeDbSchema {

    public static final class CrimeTable {

        public static final String NAME = "crimes";

        public static final class Cols {

            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
        }
    }
}
