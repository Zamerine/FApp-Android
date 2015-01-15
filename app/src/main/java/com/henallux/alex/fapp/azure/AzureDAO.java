package com.henallux.alex.fapp.azure;

import android.content.Context;
import android.util.Log;

import com.henallux.alex.fapp.model.Container;
import com.henallux.alex.fapp.model.Item;
import com.henallux.alex.fapp.sql.SQLiteHelperFapp;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;
import com.microsoft.windowsazure.mobileservices.table.TableQueryCallback;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 15/01/2015.
 */
public class AzureDAO {
    private MobileServiceClient mClient;
    private MobileServiceTable<ContainerAzure> mContainerTable;
    private MobileServiceTable<ItemAzure> mItemTable;
    private MobileServiceTable<TypeAzure> mTypeTable;
    private ArrayList<TypeAzure> typesAzure;
    private ArrayList<ContainerAzure> containersAzure;
    private ArrayList<ItemAzure> itemsAzure;


    public AzureDAO() {}

    public void addContainer(ContainerAzure container, Context context){
        try {
//			// Create the Mobile Service Client instance, using the provided
//			// Mobile Service URL and key
            mClient = new MobileServiceClient(
                    "https://fapp.azure-mobile.net/",
                    "rIGuwkdaibzulcZWJPJsvmSzXINxEJ89",
                    context);

            // Get the Mobile Service Table instance to use
            mContainerTable = mClient.getTable(ContainerAzure.class);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        mContainerTable.insert(container, new TableOperationCallback<ContainerAzure>() {
            public void onCompleted(ContainerAzure entity,
                                    Exception exception,
                                    ServiceFilterResponse response) {
                if (exception == null) {
                    Log.i("findId", "Read object with ID " + entity.getAzureId()); // TODO pas sur que ca soit utile, ds le tuto ils le donnent pour pouvoir retrouver l'ID assignée automatiquement
                }
            }
        });

    }

    public void addItem(ItemAzure item, Context context){
        try {
//			// Create the Mobile Service Client instance, using the provided
//			// Mobile Service URL and key
            mClient = new MobileServiceClient(
                    "https://fapp.azure-mobile.net/",
                    "rIGuwkdaibzulcZWJPJsvmSzXINxEJ89",
                    context);

            // Get the Mobile Service Table instance to use
            mItemTable = mClient.getTable(ItemAzure.class);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        mItemTable.insert(item, new TableOperationCallback<ItemAzure>() {
            public void onCompleted(ItemAzure entity,
                                    Exception exception,
                                    ServiceFilterResponse response) {
                if (exception == null) {
                    Log.i("findId", "Read object with ID " + entity.getAzureId()); // TODO pas sur que ca soit utile, ds le tuto ils le donnent pour pouvoir retrouver l'ID assignée automatiquement
                }
            }
        });

    }

    public ArrayList<TypeAzure> getTypes(Context context){
        try {
//			// Create the Mobile Service Client instance, using the provided
//			// Mobile Service URL and key
            mClient = new MobileServiceClient(
                    "https://fapp.azure-mobile.net/",
                    "rIGuwkdaibzulcZWJPJsvmSzXINxEJ89",
                    context);

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
                        typesAzure=new ArrayList<TypeAzure>(result);
                        for (TypeAzure typeAzure : result) {
                            Log.i("tag", "Read object with ID " + typeAzure.getAzureId());
                        }
                        //notify();
                    }
                }
            });}
        catch(Exception e){
            e.printStackTrace();
        }

        return typesAzure;
    }

    public ArrayList<ContainerAzure> getContainers(Context context)
    {
        try {
//			// Create the Mobile Service Client instance, using the provided
//			// Mobile Service URL and key
            mClient = new MobileServiceClient(
                    "https://fapp.azure-mobile.net/",
                    "rIGuwkdaibzulcZWJPJsvmSzXINxEJ89",
                    context);

            // Get the Mobile Service Table instance to use
            mContainerTable = mClient.getTable(ContainerAzure.class);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try{
            mContainerTable.execute(new TableQueryCallback<ContainerAzure>() {
                public void onCompleted(List<ContainerAzure> result,
                                        int count, Exception exception,
                                        ServiceFilterResponse response) {
                    if (exception == null) {
                        containersAzure=new ArrayList<ContainerAzure>(result);
                        for (ContainerAzure containerAzure : result) {
                            Log.i("tag", "Read object with ID " + containerAzure.getAzureId());
                        }
                    }
                }
            });}
        catch(Exception e){
            e.printStackTrace();
        }

        return containersAzure;

    }

    public ArrayList<ItemAzure> getItems(Context context)
    {
        try {
//			// Create the Mobile Service Client instance, using the provided
//			// Mobile Service URL and key
            mClient = new MobileServiceClient(
                    "https://fapp.azure-mobile.net/",
                    "rIGuwkdaibzulcZWJPJsvmSzXINxEJ89",
                    context);

            // Get the Mobile Service Table instance to use
            mItemTable = mClient.getTable(ItemAzure.class);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try{
            mItemTable.execute(new TableQueryCallback<ItemAzure>() {
                public void onCompleted(List<ItemAzure> result,
                                        int count, Exception exception,
                                        ServiceFilterResponse response) {
                    if (exception == null) {
                        itemsAzure=new ArrayList<ItemAzure>(result);
                        for (ItemAzure itemAzure : result) {
                            Log.i("tag", "Read object with ID " + itemAzure.getAzureId());
                        }
                    }
                }
            });}
        catch(Exception e){
            e.printStackTrace();
        }

        return itemsAzure;

    }
}
