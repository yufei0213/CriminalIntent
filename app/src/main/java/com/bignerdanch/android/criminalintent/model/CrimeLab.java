package com.bignerdanch.android.criminalintent.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bignerdanch.android.criminalintent.database.CrimeBaseHelper;
import com.bignerdanch.android.criminalintent.database.CrimeCursorWrapper;
import com.bignerdanch.android.criminalintent.database.CrimeDbSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by wangyufei on 16/10/16.
 */

public class CrimeLab {

    private static CrimeLab crimeLab;

    private Context context;
    private SQLiteDatabase database;

    public static CrimeLab get(Context context) {

        if (crimeLab == null)
            crimeLab = new CrimeLab(context);

        return crimeLab;
    }

    private CrimeLab(Context context) {

        database = new CrimeBaseHelper(context.getApplicationContext())
                .getWritableDatabase();
    }

    public void addCrime(Crime c) {

        ContentValues values = getContentValues(c);

        database.insert(CrimeDbSchema.CrimeTable.NAME, null, values);
    }

    public void updateCrime(Crime crime) {

        String uuidString = crime.getId().toString();
        ContentValues values = getContentValues(crime);

        database.update(CrimeDbSchema.CrimeTable.NAME, values,
                CrimeDbSchema.CrimeTable.Cols.UUID + " = ?", new String[]{uuidString});
    }

    public List<Crime> getCrimes() {

        List<Crime> crimes = new ArrayList<>();
        CrimeCursorWrapper cursor = queryCrimes(null, null);

        try {

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }

        return null;
    }

    public Crime getCrime(UUID id) {

        return null;
    }

    private static ContentValues getContentValues(Crime crime) {

        ContentValues values = new ContentValues();
        values.put(CrimeDbSchema.CrimeTable.Cols.UUID, crime.getId().toString());
        values.put(CrimeDbSchema.CrimeTable.Cols.TITLE, crime.getTitle().toString());
        values.put(CrimeDbSchema.CrimeTable.Cols.DATE, crime.getDate().getTime());
        values.put(CrimeDbSchema.CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);

        return values;
    }

    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {

        Cursor cursor = database.query(CrimeDbSchema.CrimeTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null);

        return new CrimeCursorWrapper(cursor);
    }
}
