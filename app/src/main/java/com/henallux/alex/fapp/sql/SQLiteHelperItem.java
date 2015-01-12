package com.henallux.alex.fapp.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Alexandre on 7/01/2015.
 */
public class SQLiteHelperItem extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "item";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ID_TYPE = "idType";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_ID_CONTAINER = "idContainer";
    public static final String COLUMN_EXPIRY_DATE = "expiredDate";
    public static final String COLUMN_LAST_SYNC = "lastSync";

    private static final String DATABASE_NAME = "item.db";
    private static final int DATABASE_VERSION = 4;

    private static final String DATABASE_CREATE = "create table " + TABLE_NAME + " (" + COLUMN_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + COLUMN_NAME + " text, " + COLUMN_ID_TYPE
            + " integer, " + COLUMN_QUANTITY + " integer, " + COLUMN_ID_CONTAINER + " integer, "
            +  COLUMN_EXPIRY_DATE + " integer, " + COLUMN_LAST_SYNC + " integer)";
    private static final String DATABASE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public SQLiteHelperItem(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DATABASE_DROP);
        onCreate(db);
    }
}
