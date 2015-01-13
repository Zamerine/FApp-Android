package com.henallux.alex.fapp;

import android.app.DatePickerDialog;
import android.content.Intent;
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
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.henallux.alex.fapp.adapter.ListItemAdapter;
import com.henallux.alex.fapp.model.Container;
import com.henallux.alex.fapp.model.Item;
import com.henallux.alex.fapp.model.Type;


public class ContainerActivity extends ActionBarActivity {
    static final int DATE_DIALOG_ID = 999;

    private TextView titleContainer;
    private ListView listViewItems;

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

        initComponent();
        CreateDatePickerExpired();
        initListener();
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
        // pour les TESTS
        Type lactoseProduct = new Type(0,"Produit laitier", new GregorianCalendar(2014,12,31));
        Type meat = new Type(1, "Viande", new GregorianCalendar(2014,12,31));
        Container frigo1 = new Container(0,"Frigo1",new GregorianCalendar(2014,12,24),Container.TYPE_FRIGO);
        frigo1.getItems().add(new Item(0, "Fromage", new GregorianCalendar(2014,12,31),5,lactoseProduct,null));
        frigo1.getItems().add(new Item(1, "Jambon", new GregorianCalendar(2015, 10, 01), 4, meat,null));
        frigo1.getItems().add(new Item(2, "Gervais",new GregorianCalendar(2015,01,05),9,lactoseProduct,null));
        //fin TESTS

        titleContainer=(TextView) findViewById(R.id.textTitleContainer);
        titleContainer.setText(frigo1.getName());

        listViewItems = (ListView) findViewById(R.id.listViewItems);
        ListItemAdapter listItemAdapter = new ListItemAdapter(this, frigo1.getItems());
        listViewItems.setAdapter(listItemAdapter);

        addItemNameText =  (EditText) findViewById(R.id.editTextItemName);
        addItemQtyText =  (EditText) findViewById(R.id.editTextItemQuantity);
        addItemDateText =  (EditText) findViewById(R.id.editTextItemExpiredDate);

        //        addFoodBtn = (Button) findViewById(R.id.addItemBtn);
        //        addFoodBtn.setOnClickListener(new OnClickListenerAddFood());
    }

    private void CreateDatePickerExpired(){
        Calendar now = Calendar.getInstance();
        datePickerExpired = new DatePickerDialog(this,new OnExpiredDateSetListener(),
                now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
    }

    private void initListener(){
        addItemDateText.setOnClickListener(new OnClickListenerShowDatePickerDialog());
    }

    //listener
    class OnExpiredDateSetListener implements DatePickerDialog.OnDateSetListener{

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Toast.makeText(ContainerActivity.this, dayOfMonth + " " + monthOfYear + " " + year, Toast.LENGTH_SHORT).show();
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
//            names.add(addFoodNameText.getText().toString());
//            quantities.add(Integer.parseInt(addFoodQtyText.getText().toString()));  //TODO changer ces editText en numberPicker et datePicker
//            dates.add(addFoodDateText.getText().toString());
//            listNameFood.invalidateViews(); // TODO OK CA MARCHE pour le moment; possible que quand ca sera lié à la DB ca ne marchera plus ==>  http://stackoverflow.com/questions/19656325/listview-not-updating-after-database-update-and-adapter-notifydatasetchanged/19657500#19657500
//            listQuantitiesFood.invalidateViews();
//            listDatesFood.invalidateViews();
        }
    }
}
