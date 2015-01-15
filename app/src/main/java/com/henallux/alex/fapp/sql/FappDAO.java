package com.henallux.alex.fapp.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.henallux.alex.fapp.model.Container;
import com.henallux.alex.fapp.model.Item;
import com.henallux.alex.fapp.model.Type;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Alexandre on 7/01/2015.
 */
public class FappDAO {
    private static final String QUERY_READ_ITEMS_CONTAINER = "SELECT * FROM "
            + SQLiteHelperFapp.TABLE_ITEM_NAME + " i INNER JOIN " + SQLiteHelperFapp.TABLE_TYPE_NAME
            + " t ON i." + SQLiteHelperFapp.COLUMN_ITEM_ID_TYPE + "=t."
            + SQLiteHelperFapp.COLUMN_TYPE_ID + " WHERE i."
            + SQLiteHelperFapp.COLUMN_ITEM_ID_CONTAINER + "=?";

    private SQLiteDatabase database;
    private SQLiteHelperFapp dbHelperFapp;
    private Context context;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd",
            Locale.ENGLISH);

    public FappDAO(Context context) {
        this.context = context;
    }

    //<editor-fold defaultstate="collapsed" desc="container">
    public void createContainer (Container container){
        dbHelperFapp = new SQLiteHelperFapp(context);
        database = dbHelperFapp.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SQLiteHelperFapp.COLUMN_CONTAINER_NAME, container.getName());
        values.put(SQLiteHelperFapp.COLUMN_CONTAINER_TYPE, container.getType());
        values.put(SQLiteHelperFapp.COLUMN_CONTAINER_LAST_SYNC,
                formatter.format(container.getLastSync().getTime()));
        database.insert(SQLiteHelperFapp.TABLE_CONTAINER_NAME, null, values);
        database.close();
        dbHelperFapp.close();
    }

    public void deleteContainer (Container container){
        deleteItemsOfContainer(container);
        dbHelperFapp = new SQLiteHelperFapp(context);
        database = dbHelperFapp.getWritableDatabase();
        database.delete(SQLiteHelperFapp.TABLE_CONTAINER_NAME,
                SQLiteHelperFapp.COLUMN_CONTAINER_ID + " = " + container.getIdCont(), null);
        database.close();
        dbHelperFapp.close();
    }

    public void updateContainer (Container container){
        dbHelperFapp = new SQLiteHelperFapp(context);
        database = dbHelperFapp.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SQLiteHelperFapp.COLUMN_CONTAINER_NAME, container.getName());
        values.put(SQLiteHelperFapp.COLUMN_CONTAINER_LAST_SYNC,
                formatter.format(container.getLastSync().getTime()));
        database.update(SQLiteHelperFapp.TABLE_CONTAINER_NAME, values,
                SQLiteHelperFapp.COLUMN_CONTAINER_ID + "=" + container.getIdCont(), null);
        database.close();
        dbHelperFapp.close();
    }

    public Container getContainerByID(int id) {
        dbHelperFapp = new SQLiteHelperFapp(context);
        database = dbHelperFapp.getReadableDatabase();
        Container container;
        Cursor cursor = database.query(SQLiteHelperFapp.TABLE_CONTAINER_NAME, null,
                SQLiteHelperFapp.COLUMN_CONTAINER_ID+ "=" + id,
                null, null, null, null);

        cursor.moveToFirst();
        if(!cursor.isAfterLast())
            container =  converterContainer(cursor);
        else
            container = null;//TODO gestion erreur pas de container

        cursor.close();
        database.close();
        dbHelperFapp.close();
        return container;
    }

    public ArrayList<Container> getAllContainers(){
        dbHelperFapp = new SQLiteHelperFapp(context);
        database = dbHelperFapp.getReadableDatabase();
        ArrayList<Container> containers = new ArrayList<>();

        Cursor cursor = database.query(SQLiteHelperFapp.TABLE_CONTAINER_NAME, null, null, null,
                null, null, SQLiteHelperFapp.COLUMN_CONTAINER_NAME);

        if(cursor.getCount() != 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                containers.add(converterContainer(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();
        database.close();
        dbHelperFapp.close();
        return containers;
    }

    private Container converterContainer(Cursor cursor) {
        Container container = new Container();
        container.setIdCont(cursor.getInt(cursor.getColumnIndex(SQLiteHelperFapp.COLUMN_CONTAINER_ID)));
        container.setName(cursor.getString(cursor.getColumnIndex(SQLiteHelperFapp.COLUMN_CONTAINER_NAME)));
        container.setType(cursor.getInt(cursor.getColumnIndex(SQLiteHelperFapp.COLUMN_CONTAINER_TYPE)));
        GregorianCalendar lastSync = new GregorianCalendar();
        try {
            lastSync.setGregorianChange(formatter.parse(cursor.getString(cursor.getColumnIndex(
                    SQLiteHelperFapp.COLUMN_CONTAINER_LAST_SYNC))));
        } catch (ParseException e) {
            //TODO gérer une erreur
        }
        container.setLastSync(lastSync);
        return container;
    }

    public Integer getLastIdContainer(){
        dbHelperFapp = new SQLiteHelperFapp(context);
        database = dbHelperFapp.getReadableDatabase();
        Integer lastid = null;

        Cursor cursor = database.query(SQLiteHelperFapp.TABLE_CONTAINER_NAME, null, null, null, null, null, SQLiteHelperFapp.COLUMN_CONTAINER_ID + " DESC");

        cursor.moveToFirst();
        if(!cursor.isAfterLast()){
            lastid = cursor.getInt(cursor.getColumnIndex(SQLiteHelperFapp.COLUMN_CONTAINER_ID));
        }

        cursor.close();
        database.close();
        dbHelperFapp.close();

        return lastid;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="item">
    public void createItem(Item item, Container container) {
        dbHelperFapp = new SQLiteHelperFapp(context);
        database = dbHelperFapp.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SQLiteHelperFapp.COLUMN_ITEM_NAME, item.getName());
        values.put(SQLiteHelperFapp.COLUMN_ITEM_QUANTITY, item.getQuantity());
        values.put(SQLiteHelperFapp.COLUMN_ITEM_ID_TYPE, item.getType().getId());
        values.put(SQLiteHelperFapp.COLUMN_ITEM_ID_CONTAINER, container.getIdCont());
        values.put(SQLiteHelperFapp.COLUMN_ITEM_EXPIRY_DATE,
                formatter.format(item.getExpiryDate().getTime()));
        values.put(SQLiteHelperFapp.COLUMN_ITEM_LAST_SYNC,
                formatter.format(item.getLastSync().getTime()));
        database.insert(SQLiteHelperFapp.TABLE_ITEM_NAME, null, values);
        database.close();
        dbHelperFapp.close();
    }

    public void deleteItem(Item item) {
        dbHelperFapp = new SQLiteHelperFapp(context);
        database = dbHelperFapp.getWritableDatabase();
        database.delete(SQLiteHelperFapp.TABLE_ITEM_NAME,
                SQLiteHelperFapp.COLUMN_ITEM_ID + " = " + item.getId(), null);
        database.close();
        dbHelperFapp.close();
    }

    public void deleteItemsOfContainer(Container container) {
        dbHelperFapp = new SQLiteHelperFapp(context);
        database = dbHelperFapp.getWritableDatabase();
        database.delete(SQLiteHelperFapp.TABLE_ITEM_NAME, SQLiteHelperFapp.COLUMN_ITEM_ID_CONTAINER
                + "= " + container.getIdCont(),null);
        database.close();
        dbHelperFapp.close();
    }

    public void updateItem(Item item) {
        dbHelperFapp = new SQLiteHelperFapp(context);
        database = dbHelperFapp.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SQLiteHelperFapp.COLUMN_ITEM_NAME, item.getName());
        values.put(SQLiteHelperFapp.COLUMN_ITEM_QUANTITY, item.getQuantity());
        values.put(SQLiteHelperFapp.COLUMN_ITEM_ID_TYPE, item.getType().getId());
        values.put(SQLiteHelperFapp.COLUMN_ITEM_EXPIRY_DATE,
                formatter.format(item.getExpiryDate().getTime()));
        values.put(SQLiteHelperFapp.COLUMN_ITEM_LAST_SYNC,
                formatter.format(item.getLastSync().getTime()));
        database.update(SQLiteHelperFapp.TABLE_ITEM_NAME, values,
                SQLiteHelperFapp.COLUMN_ITEM_ID + "=?", new String[]{String.valueOf(item.getId())});
    }

    public ArrayList<Item> getContainerItems(int containerId) {
        dbHelperFapp = new SQLiteHelperFapp(context);
        database = dbHelperFapp.getReadableDatabase();
        ArrayList<Item> items = new ArrayList<>();

        Cursor cursor = database.rawQuery(QUERY_READ_ITEMS_CONTAINER,
                new String[]{String.valueOf(containerId)});

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            items.add(converterItem(cursor));
            cursor.moveToNext();
        }
        return items;
    }

    private Item converterItem(Cursor cursor) {
        Item item = new Item();
        item.setId(cursor.getInt(cursor.getColumnIndex(SQLiteHelperFapp.COLUMN_ITEM_ID)));
        item.setName(cursor.getString(cursor.getColumnIndex("i." + SQLiteHelperFapp.COLUMN_ITEM_NAME)));
        item.setQuantity(cursor.getInt(cursor.getColumnIndex(SQLiteHelperFapp.COLUMN_ITEM_QUANTITY)));
        item.setType(new Type(cursor.getInt(cursor.getColumnIndex(SQLiteHelperFapp.COLUMN_TYPE_ID)),
                cursor.getString(cursor.getColumnIndex(SQLiteHelperFapp.COLUMN_TYPE_NAME)),
                cursor.getInt(cursor.getColumnIndex(SQLiteHelperFapp.COLUMN_TYPE_FREEZER_DURATION)),
                cursor.getInt(cursor.getColumnIndex(SQLiteHelperFapp.COLUMN_TYPE_DEFAULT_EXPIRY_DATE))));

        GregorianCalendar expiryDate = new GregorianCalendar();
        try {
            expiryDate.setTime(formatter.parse(cursor.getString(cursor.getColumnIndex(
                    SQLiteHelperFapp.COLUMN_ITEM_EXPIRY_DATE))));
        } catch (ParseException e) {
            //TODO gérer une erreur
        }
        item.setExpiryDate(expiryDate);

        GregorianCalendar lastSync = new GregorianCalendar();
        try {
            lastSync.setTime(formatter.parse(cursor.getString(cursor.getColumnIndex(
                    SQLiteHelperFapp.COLUMN_ITEM_LAST_SYNC))));
        } catch (ParseException e) {
            //TODO gérer une erreur
        }
        item.setLastSync(lastSync);

        return item;
    }

    public Integer getLastIdItem(){
        dbHelperFapp = new SQLiteHelperFapp(context);
        database = dbHelperFapp.getReadableDatabase();
        Integer lastid = null;

        Cursor cursor = database.query(SQLiteHelperFapp.TABLE_ITEM_NAME, null, null, null, null, null, SQLiteHelperFapp.COLUMN_ITEM_ID + " DESC");

        cursor.moveToFirst();
        if(!cursor.isAfterLast()){
            lastid = cursor.getInt(cursor.getColumnIndex(SQLiteHelperFapp.COLUMN_ITEM_ID));
        }

        cursor.close();
        database.close();
        dbHelperFapp.close();

        return lastid;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Type"
    private Type getTypeByID(int id){
        dbHelperFapp = new SQLiteHelperFapp(context);
        database = dbHelperFapp.getReadableDatabase();
        Type type = new Type();

        Cursor cursor = database.query(SQLiteHelperFapp.TABLE_TYPE_NAME, null,
                SQLiteHelperFapp.COLUMN_TYPE_ID + "=?", new String[id], null, null, null);

        cursor.moveToFirst();
        if(!cursor.isAfterLast()){
            type = converterType(cursor);
        }

        cursor.close();
        database.close();
        dbHelperFapp.close();

        return type;
    }

    public ArrayList<Type> getAllType() {
        dbHelperFapp = new SQLiteHelperFapp(context);
        database = dbHelperFapp.getReadableDatabase();
        ArrayList<Type> types = new ArrayList<>();

        Cursor cursor = database.query(SQLiteHelperFapp.TABLE_TYPE_NAME, null, null, null, null,
                null, SQLiteHelperFapp.COLUMN_TYPE_NAME);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            types.add(converterType(cursor));
            cursor.moveToNext();
        }

        cursor.close();
        database.close();
        dbHelperFapp.close();

        return types;
    }

    private Type converterType (Cursor cursor) {
        Type type = new Type();
        type.setId(cursor.getInt(cursor.getColumnIndex(SQLiteHelperFapp.COLUMN_TYPE_ID)));
        type.setName(cursor.getString(cursor.getColumnIndex(SQLiteHelperFapp.COLUMN_TYPE_NAME)));
        type.setFreezerDuration(cursor.getInt(cursor.getColumnIndex(
                SQLiteHelperFapp.COLUMN_TYPE_FREEZER_DURATION)));
        type.setDefaultExpiryDate(cursor.getInt(cursor.getColumnIndex(
                SQLiteHelperFapp.COLUMN_TYPE_DEFAULT_EXPIRY_DATE)));

        return type;
    }
    //</editor-fold>
}
