package com.henallux.alex.fapp.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.henallux.alex.fapp.model.Container;
import com.henallux.alex.fapp.model.Item;
import com.henallux.alex.fapp.model.Type;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

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
        values.put(SQLiteHelperFapp.COLUMN_CONTAINER_LAST_SYNC, container.getLastSync().getTimeInMillis());
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
        values.put(SQLiteHelperFapp.COLUMN_CONTAINER_LAST_SYNC, container.getLastSync().getTimeInMillis());
        database.update(SQLiteHelperFapp.TABLE_CONTAINER_NAME, values, "ID=" + container.getIdCont(), null);
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
        lastSync.setGregorianChange(new Date(cursor.getInt(
                cursor.getColumnIndex(SQLiteHelperFapp.COLUMN_CONTAINER_LAST_SYNC))));
        container.setLastSync(lastSync);
        return container;
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
        long d = item.getExpiryDate().getTimeInMillis();
        values.put(SQLiteHelperFapp.COLUMN_ITEM_EXPIRY_DATE, d);
        values.put(SQLiteHelperFapp.COLUMN_ITEM_LAST_SYNC, item.getLastSync().getTimeInMillis());
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
        values.put(SQLiteHelperFapp.COLUMN_ITEM_EXPIRY_DATE, item.getExpiryDate().getTimeInMillis());
        values.put(SQLiteHelperFapp.COLUMN_ITEM_LAST_SYNC, item.getLastSync().getTimeInMillis());
        database.update(SQLiteHelperFapp.TABLE_ITEM_NAME, values, "ID=?",
                new String[]{String.valueOf(item.getId())});
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
        item.setName(cursor.getString(cursor.getColumnIndex("i."+SQLiteHelperFapp.COLUMN_ITEM_NAME)));
        item.setQuantity(cursor.getInt(cursor.getColumnIndex(SQLiteHelperFapp.COLUMN_ITEM_QUANTITY)));
//        item.setType(getTypeByID(cursor.getInt(cursor.getColumnIndex(
//                SQLiteHelperFapp.COLUMN_ITEM_ID_TYPE))));

        GregorianCalendar expiryDate = new GregorianCalendar();
        long date = cursor.getInt(cursor.getColumnIndex(SQLiteHelperFapp.COLUMN_ITEM_EXPIRY_DATE));
        expiryDate.setTimeInMillis(date);
        item.setExpiryDate(expiryDate);

        GregorianCalendar lastSync = new GregorianCalendar();
        lastSync.setGregorianChange(new Date(cursor.getInt(cursor.getColumnIndex(
                SQLiteHelperFapp.COLUMN_ITEM_LAST_SYNC))));
        item.setLastSync(lastSync);

        return item;
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

        GregorianCalendar freezerDuration = new GregorianCalendar();
        freezerDuration.setGregorianChange(new Date(cursor.getInt(cursor.getColumnIndex(
                SQLiteHelperFapp.COLUMN_TYPE_FREEZER_DURATION))));
        type.setFreezerDuration(freezerDuration);

        GregorianCalendar expiryDate = new GregorianCalendar();
        expiryDate.setGregorianChange(new Date(cursor.getInt(cursor.getColumnIndex(
                SQLiteHelperFapp.COLUMN_TYPE_DEFAULT_EXPIRY_DATE))));
        type.setDefaultExpiryDate(expiryDate);

        return type;
    }
    //</editor-fold>
}
