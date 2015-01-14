package com.henallux.alex.fapp.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.GregorianCalendar;

/**
 * Created by Alexandre on 7/01/2015.
 */
public class SQLiteHelperType extends SQLiteOpenHelper{

    public static final String TABLE_NAME = "type";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_FREEZER_DURATION = "freezerDuration";
    public static final String COLUMN_DEFAULT_EXPIRY_DATE = "defaultExpiryDate";

    private static final String DATABASE_NAME = "type.db";
    private static final int DATABASE_VERSION = 3;

    private static final String DATABASE_CREATE = "create table " + TABLE_NAME + " (" + COLUMN_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + COLUMN_NAME + " text, "
            + COLUMN_FREEZER_DURATION + " integer, " + COLUMN_DEFAULT_EXPIRY_DATE + " integer)";
    private static final String DATABASE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public SQLiteHelperType(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
        ContentValues values = new ContentValues();
        values.put(SQLiteHelperType.COLUMN_NAME, "produit laitié");
        values.put(SQLiteHelperType.COLUMN_FREEZER_DURATION, new GregorianCalendar(0,4,0).getTimeInMillis());
        db.insert(SQLiteHelperType.TABLE_NAME, null, values);
        values.put(SQLiteHelperType.COLUMN_NAME, "Viande");
        values.put(SQLiteHelperType.COLUMN_FREEZER_DURATION, new GregorianCalendar(0,6,0).getTimeInMillis());
        db.insert(SQLiteHelperType.TABLE_NAME, null, values);
        values.put(SQLiteHelperType.COLUMN_NAME, "Fruit");
        values.put(SQLiteHelperType.COLUMN_FREEZER_DURATION, new GregorianCalendar(0,5,0).getTimeInMillis());
        db.insert(SQLiteHelperType.TABLE_NAME, null, values);
        values.put(SQLiteHelperType.COLUMN_NAME, "Poisson");
        values.put(SQLiteHelperType.COLUMN_FREEZER_DURATION, new GregorianCalendar(0,6,0).getTimeInMillis());
        db.insert(SQLiteHelperType.TABLE_NAME, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DATABASE_DROP);
        onCreate(db);
    }
}
