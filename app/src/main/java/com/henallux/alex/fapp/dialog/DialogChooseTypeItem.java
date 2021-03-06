package com.henallux.alex.fapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.henallux.alex.fapp.ContainerActivity;
import com.henallux.alex.fapp.R;
import com.henallux.alex.fapp.azure.AzureDAO;
import com.henallux.alex.fapp.azure.ContainerAzure;
import com.henallux.alex.fapp.azure.ItemAzure;
import com.henallux.alex.fapp.model.Container;
import com.henallux.alex.fapp.model.Item;
import com.henallux.alex.fapp.model.Type;
import com.henallux.alex.fapp.sql.FappDAO;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Alexandre on 13/01/2015.
 */
public class DialogChooseTypeItem extends Dialog{
    private Item item;
    private Container container;
    private ArrayList<Type> types;
    private int typeSelected;

    private Spinner spinnerType;
    private Button bntAddItem;
    private Button bntBack;

    public DialogChooseTypeItem(Context context, Item item, Container container) {
        super(context);

        this.setContentView(R.layout.dialog_choose_type_item);
        this.item = item;
        this.container = container;
        typeSelected = 0;
        types = getAllType();

        initComponent();
        initListener();
    }

    private void initComponent() {
        spinnerType = (Spinner)findViewById(R.id.spinnerTypeItem);
        ArrayAdapter<Type> spinnerTypeAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, types);
        spinnerType.setAdapter(spinnerTypeAdapter);
        bntAddItem = (Button)findViewById(R.id.bnt_valid_add_item);
        bntBack = (Button)findViewById(R.id.bnt_back);
    }

    private void initListener() {
        bntAddItem.setOnClickListener(new OnClickAddItemListener());
        bntBack.setOnClickListener(new OnClickBack());
    }

    private ArrayList<String> getAllNameType() {
        ArrayList<String> nameTypes = new ArrayList<>();
        for (Type type : types) {
            nameTypes.add(type.getName());
        }
        return nameTypes;
    }

    private ArrayList<Type> getAllType() {
        FappDAO fappDAO = new FappDAO(getContext());
        return fappDAO.getAllType();
    }

    //Listener
    private class OnClickAddItemListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            item.setType(types.get(spinnerType.getSelectedItemPosition()));
            if(container.getType() == Container.TYPE_FREEZER)
                computeExpiredDateForFreezer();
            item.setLastSync(new GregorianCalendar());
            FappDAO fappDAO = new FappDAO(getContext());
            fappDAO.createItem(item, container);

            AzureDAO azureDAO = new AzureDAO();
            ItemAzure itemAzure=new ItemAzure(item.getName(),item.getExpiryDate().getTime(),item.getQuantity(),item.getType().getAzureId(),item.getLastSync().getTime());
            azureDAO.addItem(itemAzure, getContext());

            DialogChooseTypeItem.this.dismiss();
        }

        private void computeExpiredDateForFreezer() {
            item.setExpiryDate(new GregorianCalendar());
            item.getExpiryDate().add(GregorianCalendar.MONTH, types.get(spinnerType.getSelectedItemPosition()).getFreezerDuration());
        }
    }
    private class OnClickBack implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            DialogChooseTypeItem.this.dismiss();
        }
    }
}
