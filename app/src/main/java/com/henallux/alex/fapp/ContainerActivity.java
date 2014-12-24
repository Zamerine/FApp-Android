package com.henallux.alex.fapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import model.Container;
import model.Item;
import model.Type;


public class ContainerActivity extends ActionBarActivity {

    private TextView titleContainer;
    private ListView listNameFood;
    private ListView listQuantitiesFood;
    private ListView listDatesFood;

    private ArrayList <Item> items=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        Bundle bundle=this.getIntent().getExtras();

        // pour les TESTS
        Type lactoseProduct = new Type(0,"Produit laitier",30);
        Type meat = new Type(1,"Viande",25);
        Container frigo1 = new Container(0,bundle.getString("textTitleContainer"),new GregorianCalendar(2014,12,24),"Frigo");
        items.add(new Item(0,"Fromage",new GregorianCalendar(2014,12,31),5,lactoseProduct,frigo1));
        items.add(new Item(1,"Jambon",new GregorianCalendar(2014,12,31),4,meat,frigo1));
        items.add(new Item(2,"Gervais",new GregorianCalendar(2014,12,31),9,lactoseProduct,frigo1));

        ArrayList<String> names=new ArrayList<>();
        ArrayList<Integer> quantities=new ArrayList<>();
        ArrayList<String> dates=new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/mm/dd");
        for(int i=0;i<items.size();i++) {
            names.add(items.get(i).getName());
            quantities.add(items.get(i).getQuantity());
            dates.add(formatter.format(items.get(i).getExpiryDate()));
        }

        titleContainer=(TextView) findViewById(R.id.textTitleContainer);
        titleContainer.setText(bundle.getString("textTitleContainer"));

        listNameFood=(ListView) findViewById(R.id.listViewNameFood);
        listQuantitiesFood=(ListView) findViewById(R.id.listViewQtyFood);
        listDatesFood=(ListView) findViewById(R.id.listViewDateFood);

        ArrayAdapter<String> arrayAdaptName=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,names);
        ArrayAdapter<Integer> arrayAdaptQuantities=new ArrayAdapter<Integer>(this,android.R.layout.simple_list_item_1,quantities);
        ArrayAdapter<String> arrayAdaptDates=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,dates);
        listNameFood.setAdapter(arrayAdaptName);
        listQuantitiesFood.setAdapter(arrayAdaptQuantities);
        listDatesFood.setAdapter(arrayAdaptDates);
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
