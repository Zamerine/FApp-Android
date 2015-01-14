package com.henallux.alex.fapp;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.henallux.alex.fapp.adapter.ListItemAdapter;
import com.henallux.alex.fapp.dialog.DialogChooseTypeItem;
import com.henallux.alex.fapp.model.Container;
import com.henallux.alex.fapp.model.Item;
import com.henallux.alex.fapp.model.Type;
import com.henallux.alex.fapp.sql.FappDAO;


public class ContainerActivity extends ActionBarActivity {
    private Container container;
    private Item item;

    private TextView titleContainer;
    private ListView listViewItems;
    private ListItemAdapter listItemAdapter;

    private SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

    private EditText addItemNameText;
    private EditText addItemQtyText;
    private EditText addItemDateText;

    private Button addFoodBtn;
    private DatePickerDialog datePickerExpired;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        Bundle bundle=this.getIntent().getExtras();
        initContainer(bundle.getInt("idContainer"));

        initComponent();
        CreateDatePickerExpired();
        initListener();

        item = new Item();
        new AsyncGetItemForContainer().execute(bundle.getInt("idContainer"));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_container, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()){
            case R.id.action_settings:
                return true;
            case R.id.searchBtn:
                Intent intent = new Intent(ContainerActivity.this, SearchActivity.class);
                startActivity(intent);
                return true;
            case R.id.homeBtn:
                intent = new Intent(ContainerActivity.this, HomeActivity.class);
                startActivity(intent);
                return true;
            default :
                return super.onOptionsItemSelected(item);
        }
    }

    private void initComponent(){

        titleContainer=(TextView) findViewById(R.id.textTitleContainer);
        titleContainer.setText(container.getName());

        listViewItems = (ListView) findViewById(R.id.listViewItems);
        listItemAdapter = new ListItemAdapter(this, new ArrayList<Item>());
        listViewItems.setAdapter(listItemAdapter);

        addItemNameText =  (EditText) findViewById(R.id.editTextItemName);
        addItemQtyText =  (EditText) findViewById(R.id.editTextItemQuantity);
        addItemDateText =  (EditText) findViewById(R.id.editTextItemExpiredDate);
        if(container.getType() == Container.TYPE_FREEZER) {
            addItemDateText.setVisibility(View.INVISIBLE);
            addItemDateText.setWidth(0);
        }

        addFoodBtn = (Button) findViewById(R.id.bntAddItem);
    }

    private void CreateDatePickerExpired(){
        Calendar now = Calendar.getInstance();
        datePickerExpired = new DatePickerDialog(this,new OnExpiredDateSetListener(),
                now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
    }

    private void initListener(){
        addItemDateText.setOnClickListener(new OnClickListenerShowDatePickerDialog());
        addFoodBtn.setOnClickListener(new OnClickListenerAddFood());
    }

    private void initContainer(int idContainer){
        FappDAO fappDAO = new FappDAO(this);
        container = fappDAO.getContainerByID(idContainer);
    }

    //listener
    class OnExpiredDateSetListener implements DatePickerDialog.OnDateSetListener{

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            GregorianCalendar expiredDate = new GregorianCalendar(year, monthOfYear, dayOfMonth);//TODO vérifier que pas une date du passé
            item.setExpiryDate(expiredDate);
            addItemDateText.setText(formatter.format(expiredDate.getTime()));
        }
    }

    class OnClickListenerShowDatePickerDialog implements  View.OnClickListener {
        @Override
        public void onClick(View v) {
            datePickerExpired.show();
        }
    }

    class OnClickListenerAddFood implements View.OnClickListener{
        @Override
        public void onClick(View v){
            if(allFieldsIsFilled()) {
                item.setName(addItemNameText.getText().toString());
                item.setQuantity(Integer.valueOf(addItemQtyText.getText().toString()));
                DialogChooseTypeItem dialog = new DialogChooseTypeItem(v.getContext(), item,
                        container);
                dialog.setTitle(getString(R.string.title_dialog_add_item));
                dialog.setOnDismissListener( new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        new AsyncGetItemForContainer().execute(container.getIdCont());
                        addItemNameText.setText("");
                        addItemQtyText.setText("");
                        addItemDateText.setText("");
                    }
                });
                dialog.show();
            }
        }
    }
    private boolean allFieldsIsFilled() {
        if(addItemNameText.getText().toString().isEmpty()) {
            Toast.makeText(ContainerActivity.this, getString(R.string.error_item_name),
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if(addItemQtyIsEmpty()) {
            Toast.makeText(ContainerActivity.this, getString(R.string.error_item_quantity),
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!expiredDateIsCorrect()){
            Toast.makeText(ContainerActivity.this, getString(R.string.error_item_expired_date),
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean addItemQtyIsEmpty(){
        return addItemQtyText.getText().toString().isEmpty() ||
                Integer.valueOf(addItemQtyText.getText().toString()) == 0;
    }

    private boolean expiredDateIsCorrect() {
        if(container.getType() == Container.TYPE_FREEZER)
            return true;
        if(addItemDateText.getText().toString().isEmpty()) {
            return false;
        }
        return item.getExpiryDate().compareTo(new GregorianCalendar()) > 0;
    }

    //asyncTask
    private class AsyncGetItemForContainer extends AsyncTask<Integer, Void, ArrayList<Item>>
    {

        @Override
        protected ArrayList<Item> doInBackground(Integer... params) {
            FappDAO fappDAO = new FappDAO(ContainerActivity.this);
            return fappDAO.getContainerItems(params[0]); //TODO verifier qu'il y a bien un paramètre
        }

        @Override
        protected void onPostExecute (ArrayList<Item> result) {
            listItemAdapter.setItems(result);
            listItemAdapter.notifyDataSetChanged();
        }
    }
}
