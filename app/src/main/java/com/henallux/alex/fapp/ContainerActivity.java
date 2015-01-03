package com.henallux.alex.fapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import com.henallux.alex.fapp.model.Container;
import com.henallux.alex.fapp.model.Item;
import com.henallux.alex.fapp.model.Type;


public class ContainerActivity extends ActionBarActivity {

    private TextView titleContainer;
    private ListView listNameFood;
    private ListView listQuantitiesFood;
    private ListView listDatesFood;

    ArrayList<String> names=new ArrayList<>();
    ArrayList<Integer> quantities=new ArrayList<>();
    ArrayList<String> dates=new ArrayList<>();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    private EditText addFoodNameText;
    private EditText addFoodQtyText;
    private EditText addFoodDateText;
    private Button addFoodBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        Bundle bundle=this.getIntent().getExtras();

        // pour les TESTS
        Type lactoseProduct = new Type("0","Produit laitier", new GregorianCalendar(2014,12,31));
        Type meat = new Type("1","Viande", new GregorianCalendar(2014,12,31));
        Container frigo1 = new Container("0",bundle.getString("textTitleContainer"),new GregorianCalendar(2014,12,24),Container.TYPE_FRIGO);
        frigo1.getItems().add(new Item("0", "Fromage", new GregorianCalendar(2014,12,31),5,lactoseProduct));
        frigo1.getItems().add(new Item("1","Jambon",new GregorianCalendar(2015,10,01),4,meat));
        frigo1.getItems().add(new Item("2","Gervais",new GregorianCalendar(2015,01,05),9,lactoseProduct));



        for(int i=0;i<frigo1.getItems().size();i++) {
            if(frigo1.getName() == bundle.getString("textTitleContainer")) {
                names.add(frigo1.getItems().get(i).getName());
                quantities.add(frigo1.getItems().get(i).getQuantity());
                java.util.Date datetest = frigo1.getItems().get(i).getExpiryDate().getTime();
                dates.add(formatter.format(datetest));
            }

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


        addFoodNameText =  (EditText) findViewById(R.id.editTextNam);
        addFoodQtyText =  (EditText) findViewById(R.id.editTextQty);
        addFoodDateText =  (EditText) findViewById(R.id.editTextDate);

        addFoodBtn = (Button) findViewById(R.id.addItemBtn);
        addFoodBtn.setOnClickListener(new OnClickListenerAddFood());
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

    class OnClickListenerAddFood implements View.OnClickListener{
        @Override
        public void onClick(View v){
            names.add(addFoodNameText.getText().toString());
            quantities.add(Integer.parseInt(addFoodQtyText.getText().toString()));  //TODO changer ces editText en numberPicker et datePicker
            dates.add(addFoodDateText.getText().toString());
            listNameFood.invalidateViews(); // TODO OK CA MARCHE pour le moment; possible que quand ca sera lié à la DB ca ne marchera plus ==>  http://stackoverflow.com/questions/19656325/listview-not-updating-after-database-update-and-adapter-notifydatasetchanged/19657500#19657500
            listQuantitiesFood.invalidateViews();
            listDatesFood.invalidateViews();
        }
    }
}
