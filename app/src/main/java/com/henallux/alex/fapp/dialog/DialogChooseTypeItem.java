package com.henallux.alex.fapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.henallux.alex.fapp.R;
import com.henallux.alex.fapp.model.Container;
import com.henallux.alex.fapp.model.Item;
import com.henallux.alex.fapp.model.Type;
import com.henallux.alex.fapp.sql.FappDAO;

import java.lang.reflect.Array;
import java.util.ArrayList;
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
            DialogChooseTypeItem.this.dismiss();
        }

        private void computeExpiredDateForFreezer() {
            item.getExpiryDate().add(GregorianCalendar.YEAR, types.get(spinnerType.
                    getSelectedItemPosition()).getFreezerDuration().get(GregorianCalendar.YEAR));
            item.getExpiryDate().add(GregorianCalendar.MONTH, types.get(spinnerType.
                    getSelectedItemPosition()).getFreezerDuration().get(GregorianCalendar.MONTH));
            item.getExpiryDate().add(GregorianCalendar.DAY_OF_MONTH, types.get(spinnerType.
                    getSelectedItemPosition()).getFreezerDuration().get(GregorianCalendar.DAY_OF_MONTH));
        }
    }
    private class OnClickBack implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            DialogChooseTypeItem.this.dismiss();
        }
    }
}
