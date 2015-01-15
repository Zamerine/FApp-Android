package com.henallux.alex.fapp.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.henallux.alex.fapp.R;
import com.henallux.alex.fapp.model.Container;
import com.henallux.alex.fapp.model.Item;
import com.henallux.alex.fapp.model.Type;
import com.henallux.alex.fapp.sql.FappDAO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Alexandre on 14/01/2015.
 */
public class DialogEditItem extends Dialog {
    private Item item;
    private Container container;
    private ArrayList<Type> types;

    private EditText editNameItem;
    private EditText editQtyItem;
    private EditText editExpiredDateItem;
    private Spinner editTypeItem;
    private Button bntValidEdit;
    private Button bntBack;
    private Button bntDelete;

    private DatePickerDialog datePickerExpired;

    private SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

    public DialogEditItem(Context context, Item item, Container container) {
        super(context);
        this.setContentView(R.layout.dialog_edit_item);
        this.item = item;
        this.container = container;
        this.types = getAllType();

        CreateDatePickerExpired();
        initComponent();
        initListener();
    }

    private ArrayList<Type> getAllType() {
        FappDAO fappDAO = new FappDAO(getContext());
        return fappDAO.getAllType();
    }

    private void initComponent() {
        editNameItem = (EditText)findViewById(R.id.editNameItem);
        editNameItem.setText(item.getName());
        editQtyItem = (EditText)findViewById(R.id.editQtyItem);
        editQtyItem.setText(String.valueOf(item.getQuantity()));
        editExpiredDateItem = (EditText)findViewById(R.id.editExpiredDateItem);
        editExpiredDateItem.setText(formatter.format(item.getExpiryDate().getTime()));

        editTypeItem = (Spinner)findViewById(R.id.spinnerEditTypeItem);
        ArrayAdapter<Type> spinnerTypeAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, types);
        editTypeItem.setAdapter(spinnerTypeAdapter);
        editTypeItem.setSelection(findPosType());

        bntValidEdit = (Button)findViewById(R.id.bntEditItem);
        bntBack = (Button)findViewById(R.id.bntBack);
        bntDelete = (Button)findViewById(R.id.bntDeleteItem);
    }

    private int findPosType() {
        int pos = 0;
        for(; pos < types.size() && types.get(pos).getId() != item.getType().getId();pos++);
        return pos;
    }

    private void initListener() {
        bntValidEdit.setOnClickListener(new OnClickEditItemListener());
        bntBack.setOnClickListener(new OnClickBack());
        bntDelete.setOnClickListener(new OnClickDeleteItemListener());
        editExpiredDateItem.setOnClickListener(new OnClickListenerShowDatePickerDialog());
    }

    private void CreateDatePickerExpired(){
        datePickerExpired = new DatePickerDialog(getContext(),new OnExpiredDateSetListener(),
                item.getExpiryDate().get(Calendar.YEAR), item.getExpiryDate().get(Calendar.MONTH),
                item.getExpiryDate().get(Calendar.DAY_OF_MONTH));
        datePickerExpired.setTitle(getContext().getString(R.string.columnExpiration));
    }

    //Listener
    class OnExpiredDateSetListener implements DatePickerDialog.OnDateSetListener{

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            GregorianCalendar expiredDate = new GregorianCalendar(year, monthOfYear, dayOfMonth);
            item.setExpiryDate(expiredDate);
            editExpiredDateItem.setText(formatter.format(expiredDate.getTime()));
        }
    }

    class OnClickListenerShowDatePickerDialog implements  View.OnClickListener {
        @Override
        public void onClick(View v) {
            datePickerExpired.show();
        }
    }

    private class OnClickEditItemListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (allFieldsIsFilled()) {
                item.setName(editNameItem.getText().toString());
                item.setQuantity(Integer.valueOf(editQtyItem.getText().toString()));
                item.setType(types.get(editTypeItem.getSelectedItemPosition()));
                item.setLastSync(new GregorianCalendar());
                FappDAO fappDAO = new FappDAO(getContext());
                fappDAO.updateItem(item);
                DialogEditItem.this.dismiss();
            }
        }

        private boolean allFieldsIsFilled() {
            if(editNameItem.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), getContext().getString(R.string.error_item_name),
                        Toast.LENGTH_SHORT).show();
                return false;
            }

            if(addItemQtyIsEmpty()) {
                Toast.makeText(getContext(), getContext().getString(R.string.error_item_quantity),
                        Toast.LENGTH_SHORT).show();
                return false;
            }

            if(!expiredDateIsCorrect()){
                Toast.makeText(getContext(), getContext().getString(R.string.error_item_expired_date),
                        Toast.LENGTH_SHORT).show();
                return false;
            }

            return true;
        }

        private boolean addItemQtyIsEmpty(){
            return editQtyItem.getText().toString().isEmpty() ||
                    Integer.valueOf(editQtyItem.getText().toString()) == 0;
        }

        private boolean expiredDateIsCorrect() {
            if(container.getType() == Container.TYPE_FREEZER)
                return true;
            if(editExpiredDateItem.getText().toString().isEmpty()) {
                return false;
            }
            return item.getExpiryDate().compareTo(new GregorianCalendar()) > 0;
        }
    }

    private class OnClickDeleteItemListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            FappDAO fappDAO = new FappDAO(getContext());
            fappDAO.deleteItem(item);
            DialogEditItem.this.dismiss();
        }
    }

    private class OnClickBack implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            DialogEditItem.this.dismiss();
        }
    }
}
