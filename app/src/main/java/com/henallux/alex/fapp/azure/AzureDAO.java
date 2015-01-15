package com.henallux.alex.fapp.azure;

import android.content.Context;
import android.util.Log;

import com.henallux.alex.fapp.model.Container;
import com.henallux.alex.fapp.model.Item;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;

import java.net.MalformedURLException;

/**
 * Created by Alex on 15/01/2015.
 */
public class AzureDAO {
    private MobileServiceClient mClient;
    private MobileServiceTable<ContainerAzure> mContainerTable;
    private MobileServiceTable<ItemAzure> mItemTable;

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
}

