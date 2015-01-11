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

    private static final String[] ALL_COLUMNS_CONTAINER = {SQLiteHelperContainer.COLUMN_ID,
            SQLiteHelperContainer.COLUMN_NAME, SQLiteHelperContainer.COLUMN_TYPE,
            SQLiteHelperContainer.COLUMN_LAST_SYNC};
    private static final  String[] ALL_COLUMNS_ITEM = {SQLiteHelperItem.COLUMN_ID,
            SQLiteHelperItem.COLUMN_NAME, SQLiteHelperItem.COLUMN_QUANTITY,
            SQLiteHelperItem.COLUMN_ID_TYPE, SQLiteHelperItem.COLUMN_EXPIRY_DATE,
            SQLiteHelperItem.COLUMN_LAST_SYNC} ;
    private static final String[] ALL_COLUMNS_TYPE = {SQLiteHelperType.COLUMN_ID,
            SQLiteHelperType.COLUMN_NAME, SQLiteHelperType.COLUMN_FREEZER_DURATION,
            SQLiteHelperType.COLUMN_DEFAULT_EXPIRY_DATE};

    private SQLiteDatabase database;
    private SQLiteHelperContainer dbHelperContainer;
    private SQLiteHelperItem dbHelperItem;
    private SQLiteHelperType dbHelperType;
    private Context context;

    public FappDAO(Context context) {
        this.context = context;
    }

    //<editor-fold defaultstate="collapsed" desc="container">
    public void createContainer (Container container){
        dbHelperContainer = new SQLiteHelperContainer(context);
        database = dbHelperContainer.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SQLiteHelperContainer.COLUMN_ID, container.getIdCont());
        values.put(SQLiteHelperContainer.COLUMN_NAME, container.getName());
        values.put(SQLiteHelperContainer.COLUMN_TYPE, container.getType());
        values.put(SQLiteHelperContainer.COLUMN_LAST_SYNC, container.getLastSync().getTimeInMillis());
        database.insert(SQLiteHelperContainer.TABLE_NAME, null, values);
        database.close();
        dbHelperContainer.close();
    }

    public void deleteContainer (Container container){
        dbHelperContainer = new SQLiteHelperContainer(context);
        database = dbHelperContainer.getWritableDatabase();
        database.delete(SQLiteHelperContainer.TABLE_NAME,
                SQLiteHelperContainer.COLUMN_ID + " = " + container.getIdCont(), null);
        database.close();
        dbHelperContainer.close();
    }

    public ArrayList<Container> getAllContainers(){
        dbHelperContainer = new SQLiteHelperContainer(context);
        database = dbHelperContainer.getReadableDatabase();
        ArrayList<Container> containers = new ArrayList<>();

        Cursor cursor = database.query(SQLiteHelperContainer.TABLE_NAME, ALL_COLUMNS_CONTAINER, null,
                null, null, null, SQLiteHelperContainer.COLUMN_NAME);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            containers.add(converterContainer(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        database.close();
        dbHelperContainer.close();
        return containers;
    }

    private Container converterContainer(Cursor cursor) {
        Container container = new Container();
        container.setIdCont(cursor.getInt(0));
        container.setName(cursor.getString(1));
        container.setType(cursor.getInt(2));
        GregorianCalendar lastSync = new GregorianCalendar();
        lastSync.setGregorianChange(new Date(cursor.getInt(3)));
        container.setLastSync(lastSync);
        return container;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="item"
    public void createItem(Item item) {
        dbHelperItem = new SQLiteHelperItem(context);
        database = dbHelperItem.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SQLiteHelperItem.COLUMN_ID, item.getId());
        values.put(SQLiteHelperItem.COLUMN_NAME, item.getName());
        values.put(SQLiteHelperItem.COLUMN_QUANTITY, item.getQuantity());
        values.put(SQLiteHelperItem.COLUMN_ID_TYPE, item.getType().getId());
        values.put(SQLiteHelperItem.COLUMN_EXPIRY_DATE, item.getExpiryDate().getTimeInMillis());
        values.put(SQLiteHelperItem.COLUMN_LAST_SYNC, item.getLastSync().getTimeInMillis());
        database.insert(SQLiteHelperItem.TABLE_NAME, null, values);
        database.close();
        dbHelperItem.close();
    }

    public void deleteItem(Item item) {
        dbHelperItem = new SQLiteHelperItem(context);
        database = dbHelperItem.getWritableDatabase();
        database.delete(SQLiteHelperItem.TABLE_NAME,
                SQLiteHelperItem.COLUMN_ID + " = " + item.getId(), null);
        database.close();
        dbHelperContainer.close();
    }

    public ArrayList<Item> getContainerItem(int containerId) {
        dbHelperItem = new SQLiteHelperItem(context);
        database = dbHelperItem.getReadableDatabase();
        ArrayList<Item> items = new ArrayList<>();

        Cursor cursor = database.query(SQLiteHelperItem.TABLE_NAME, ALL_COLUMNS_ITEM, null, null,
                null, null, SQLiteHelperItem.COLUMN_NAME);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            items.add(converterItem(cursor));
            cursor.moveToNext();
        }
        return items;
    }

    private Item converterItem(Cursor cursor) {
        Item item = new Item();
        item.setId(cursor.getInt(0));
        item.setName(cursor.getString(1));
        item.setQuantity(cursor.getInt(2));
        item.setType(getTypeByID(cursor.getInt(3)));

        GregorianCalendar expiryDate = new GregorianCalendar();
        expiryDate.setGregorianChange(new Date(cursor.getInt(4)));
        item.setExpiryDate(expiryDate);

        GregorianCalendar lastSync = new GregorianCalendar();
        lastSync.setGregorianChange(new Date(cursor.getInt(5)));
        item.setLastSync(lastSync);

        return item;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Type"
    private Type getTypeByID(int id){
        dbHelperType = new SQLiteHelperType(context);
        database = dbHelperType.getReadableDatabase();
        Type type = new Type();

        Cursor cursor = database.query(SQLiteHelperType.TABLE_NAME, ALL_COLUMNS_TYPE,
                SQLiteHelperType.COLUMN_ID + "=?", new String[id], null, null, null);

        cursor.moveToFirst();
        if(!cursor.isAfterLast()){
            type = converterType(cursor);
        }

        cursor.close();
        database.close();
        dbHelperType.close();

        return type;
    }

    public ArrayList<Type> getAllType() {
        dbHelperType = new SQLiteHelperType(context);
        database = dbHelperType.getReadableDatabase();
        ArrayList<Type> types = new ArrayList<>();

        Cursor cursor = database.query(SQLiteHelperType.TABLE_NAME, ALL_COLUMNS_TYPE, null, null,
                null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            types.add(converterType(cursor));
            cursor.moveToNext();
        }

        cursor.close();
        database.close();
        dbHelperType.close();

        return types;
    }

    private Type converterType (Cursor cursor) {
        Type type = new Type();
        type.setId(cursor.getInt(0));
        type.setName(cursor.getString(1));

        GregorianCalendar freezerDuration = new GregorianCalendar();
        freezerDuration.setGregorianChange(new Date(cursor.getInt(2)));
        type.setFreezerDuration(freezerDuration);

        GregorianCalendar expiryDate = new GregorianCalendar();
        expiryDate.setGregorianChange(new Date(cursor.getInt(3)));
        type.setDefaultExpiryDate(expiryDate);

        return type;
    }
    //</editor-fold>
}
