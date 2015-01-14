package com.henallux.alex.fapp.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Alexandre on 14/01/2015.
 */
public class SQLiteHelperFapp extends SQLiteOpenHelper {
    public static final String TABLE_CONTAINER_NAME = "container";
    public static final String COLUMN_CONTAINER_ID = "contID";
    public static final String COLUMN_CONTAINER_NAME = "contName";
    public static final String COLUMN_CONTAINER_TYPE = "contType";
    public static final String COLUMN_CONTAINER_LAST_SYNC = "contLastSync";
    public static final String TABLE_TYPE_NAME = "type";
    public static final String COLUMN_TYPE_ID = "typeID";
    public static final String COLUMN_TYPE_NAME = "typeName";
    public static final String COLUMN_TYPE_FREEZER_DURATION = "typeFreezerDuration";
    public static final String COLUMN_TYPE_DEFAULT_EXPIRY_DATE = "typeDefaultExpiryDate";
    public static final String TABLE_ITEM_NAME = "item";
    public static final String COLUMN_ITEM_ID = "itemID";
    public static final String COLUMN_ITEM_NAME = "itemName";
    public static final String COLUMN_ITEM_ID_TYPE = "itemIdType";
    public static final String COLUMN_ITEM_QUANTITY = "itemQuantity";
    public static final String COLUMN_ITEM_ID_CONTAINER = "itemIdContainer";
    public static final String COLUMN_ITEM_EXPIRY_DATE = "itemExpiredDate";
    public static final String COLUMN_ITEM_LAST_SYNC = "itemLastSync";

    private static final String DATABASE_NAME = "fapp.db";
    private static final int DATABASE_VERSION = 3;

    private static final String TABLE_TYPE_CREATE = "create table " + TABLE_TYPE_NAME + " ("
            + COLUMN_TYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + COLUMN_TYPE_NAME
            + " text, " + COLUMN_TYPE_FREEZER_DURATION + " date, "
            + COLUMN_TYPE_DEFAULT_EXPIRY_DATE + " date)";
    private static final String TABLE_CONTAINER_CREATE = "create table " + TABLE_CONTAINER_NAME
            + " (" + COLUMN_CONTAINER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + COLUMN_CONTAINER_NAME + " text, " + COLUMN_CONTAINER_TYPE + " integer, "
            + COLUMN_CONTAINER_LAST_SYNC + " date)";
    private static final String TABLE_ITEM_CREATE = "create table " + TABLE_ITEM_NAME + " ("
            + COLUMN_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + COLUMN_ITEM_NAME
            + " text, " + COLUMN_ITEM_ID_TYPE + " integer, " + COLUMN_ITEM_QUANTITY + " integer, "
            + COLUMN_ITEM_ID_CONTAINER + " integer, " + COLUMN_ITEM_EXPIRY_DATE + " date, "
            + COLUMN_ITEM_LAST_SYNC + " date, FOREIGN KEY(" + COLUMN_ITEM_ID_CONTAINER
            + ") REFERENCES " + TABLE_CONTAINER_NAME + "(" + COLUMN_CONTAINER_ID +  "), FOREIGN KEY("
            + COLUMN_ITEM_ID_TYPE + ") REFERENCES " + TABLE_TYPE_NAME + "(" + COLUMN_TYPE_ID + "))";

    private static final String TABLE_ITEM_DROP = "DROP TABLE IF EXISTS " + TABLE_ITEM_NAME;
    private static final String TABLE_TYPE_DROP = "DROP TABLE IF EXISTS " + TABLE_TYPE_NAME;
    private static final String TABLE_CONTAINER_DROP = "DROP TABLE IF EXISTS " + TABLE_CONTAINER_NAME;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd",
            Locale.ENGLISH);

    public SQLiteHelperFapp(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if(!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CONTAINER_CREATE);
        db.execSQL(TABLE_TYPE_CREATE);
        db.execSQL(TABLE_ITEM_CREATE);
        initTypes(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TABLE_ITEM_DROP);
        db.execSQL(TABLE_CONTAINER_DROP);
        db.execSQL(TABLE_TYPE_DROP);
        onCreate(db);
    }

    private void initTypes(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(SQLiteHelperFapp.COLUMN_TYPE_NAME, "produit laitié");
        values.put(SQLiteHelperFapp.COLUMN_TYPE_FREEZER_DURATION,
                formatter.format(new GregorianCalendar(0,3,0).getTime()));
        values.put(SQLiteHelperFapp.COLUMN_TYPE_DEFAULT_EXPIRY_DATE,
                formatter.format(new GregorianCalendar(0,0,15).getTime()));
        db.insert(SQLiteHelperFapp.TABLE_TYPE_NAME, null, values);
        values.put(SQLiteHelperFapp.COLUMN_TYPE_NAME, "Viande");
        values.put(SQLiteHelperFapp.COLUMN_TYPE_FREEZER_DURATION,
                formatter.format(new GregorianCalendar(0,7,0).getTime()));
        values.put(SQLiteHelperFapp.COLUMN_TYPE_DEFAULT_EXPIRY_DATE,
                formatter.format(new GregorianCalendar(0,0,15).getTime()));
        db.insert(SQLiteHelperFapp.TABLE_TYPE_NAME, null, values);
        values.put(SQLiteHelperFapp.COLUMN_TYPE_NAME, "Fruit");
        values.put(SQLiteHelperFapp.COLUMN_TYPE_FREEZER_DURATION,
                formatter.format(new GregorianCalendar(0,10,0).getTime()));
        values.put(SQLiteHelperFapp.COLUMN_TYPE_DEFAULT_EXPIRY_DATE,
                formatter.format(new GregorianCalendar(0,0,15).getTime()));
        db.insert(SQLiteHelperFapp.TABLE_TYPE_NAME, null, values);
        values.put(SQLiteHelperFapp.COLUMN_TYPE_NAME, "Poisson");
        values.put(SQLiteHelperFapp.COLUMN_TYPE_FREEZER_DURATION,
                formatter.format(new GregorianCalendar(0,7,0).getTime()));
        values.put(SQLiteHelperFapp.COLUMN_TYPE_DEFAULT_EXPIRY_DATE,
                formatter.format(new GregorianCalendar(0,0,15).getTime()));
        db.insert(SQLiteHelperFapp.TABLE_TYPE_NAME, null, values);
    }
}
