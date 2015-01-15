package com.henallux.alex.fapp.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;

import com.henallux.alex.fapp.azure.AzureDAO;
import com.henallux.alex.fapp.azure.ContainerAzure;
import com.henallux.alex.fapp.azure.ItemAzure;
import com.henallux.alex.fapp.azure.TypeAzure;
import com.henallux.alex.fapp.model.Container;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.TableQueryCallback;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
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
    public static final String COLUMN_TYPE_AZUREID = "typeazureId";
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
            + COLUMN_TYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+ COLUMN_TYPE_NAME
            + " text, " + COLUMN_TYPE_FREEZER_DURATION + " integer, "
            + COLUMN_TYPE_DEFAULT_EXPIRY_DATE + " integer,"+COLUMN_TYPE_AZUREID+" TEXT)";
    private static final String TABLE_CONTAINER_CREATE = "create table " + TABLE_CONTAINER_NAME
            + " (" + COLUMN_CONTAINER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + COLUMN_CONTAINER_NAME + " text, " + COLUMN_CONTAINER_TYPE + " integer, "
            + COLUMN_CONTAINER_LAST_SYNC + " date)";
    private static final String TABLE_ITEM_CREATE = "create table " + TABLE_ITEM_NAME + " ("
            + COLUMN_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +COLUMN_ITEM_NAME
            + " text, " + COLUMN_ITEM_ID_TYPE + " integer, " + COLUMN_ITEM_QUANTITY + " integer, "
            + COLUMN_ITEM_ID_CONTAINER + " integer, " + COLUMN_ITEM_EXPIRY_DATE + " date, "
            + COLUMN_ITEM_LAST_SYNC + " date, FOREIGN KEY(" + COLUMN_ITEM_ID_CONTAINER
            + ") REFERENCES " + TABLE_CONTAINER_NAME + "(" + COLUMN_CONTAINER_ID +  "), FOREIGN KEY("
            + COLUMN_ITEM_ID_TYPE + ") REFERENCES " + TABLE_TYPE_NAME + "(" + COLUMN_TYPE_ID + "))";

    private static final String TABLE_ITEM_DROP = "DROP TABLE IF EXISTS " + TABLE_ITEM_NAME;
    private static final String TABLE_TYPE_DROP = "DROP TABLE IF EXISTS " + TABLE_TYPE_NAME;
    private static final String TABLE_CONTAINER_DROP = "DROP TABLE IF EXISTS " + TABLE_CONTAINER_NAME;

    private Context contexT;
    private MobileServiceClient mClient;
    private MobileServiceTable<TypeAzure> mTypeTable;


    public SQLiteHelperFapp(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        contexT = context;

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

        initTypes();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TABLE_ITEM_DROP);
        db.execSQL(TABLE_CONTAINER_DROP);
        db.execSQL(TABLE_TYPE_DROP);
        onCreate(db);
    }

    public void initTypes () {
        final ContentValues values = new ContentValues();
        final ArrayList<TypeAzure> azureTypes;
        try {
//			// Create the Mobile Service Client instance, using the provided
//			// Mobile Service URL and key
            mClient = new MobileServiceClient(
                    "https://fapp.azure-mobile.net/",
                    "rIGuwkdaibzulcZWJPJsvmSzXINxEJ89",
                    contexT);

            // Get the Mobile Service Table instance to use
            mTypeTable = mClient.getTable(TypeAzure.class);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try{
            mTypeTable.execute(new TableQueryCallback<TypeAzure>() {
                public void onCompleted(List<TypeAzure> result,
                                        int count, Exception exception,
                                        ServiceFilterResponse response) {
                    if (exception == null) {
                        SQLiteHelperFapp sqLiteHelperFapp=new SQLiteHelperFapp(contexT);
                        SQLiteDatabase db=sqLiteHelperFapp.getWritableDatabase();
                        ArrayList <TypeAzure> azureTypes=new ArrayList<TypeAzure>(result);
                        for ( TypeAzure typeAzure : azureTypes) {

                            values.put(SQLiteHelperFapp.COLUMN_TYPE_NAME, typeAzure.getName());
                            values.put(SQLiteHelperFapp.COLUMN_TYPE_FREEZER_DURATION,typeAzure.getDefaultExpiryDate());
                            values.put(SQLiteHelperFapp.COLUMN_TYPE_DEFAULT_EXPIRY_DATE,typeAzure.getFreezerDuration());
                            values.put(SQLiteHelperFapp.COLUMN_TYPE_AZUREID,typeAzure.getAzureId());
                            db.insert(SQLiteHelperFapp.TABLE_TYPE_NAME, null, values);


                    }
                        db.close();
                        sqLiteHelperFapp.close();
                    }
            }
            });
        }
        catch(Exception e){
            e.printStackTrace();
        }
        }
    }


