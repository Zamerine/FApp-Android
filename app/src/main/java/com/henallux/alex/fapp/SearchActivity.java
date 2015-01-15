package com.henallux.alex.fapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.henallux.alex.fapp.model.Container;
import com.henallux.alex.fapp.model.Type;
import com.henallux.alex.fapp.sql.FappDAO;

import java.util.ArrayList;


public class SearchActivity extends ActionBarActivity {
    private ArrayList<Type> types;
    private ArrayList<Container> containers;

    private CheckBox checkBoxContainer;
    private CheckBox checkBoxTypeItem;
    private Spinner spinnerContainer;
    private Spinner spinnerTypeItem;
    private TextView textNbDays;
    private SeekBar seekBarNbDays;
    private Button fastSearchBtn;
    private Button completeSearchBtn;
    private EditText searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        types = getAllType();
        containers = getAllContainer();

        initComponent();
        initListener();
    }

    private void initComponent() {
        searchText = (EditText)findViewById(R.id.editSearchName);
        checkBoxContainer = (CheckBox)findViewById(R.id.checkBoxSearchContainer);
        checkBoxTypeItem = (CheckBox)findViewById(R.id.checkBoxSearchTypeItem);
        spinnerContainer = (Spinner)findViewById(R.id.spinContainer);
        ArrayAdapter<Container> spinnerContainerAdapter = new ArrayAdapter<Container>(
                SearchActivity.this, android.R.layout.simple_spinner_item, containers);
        spinnerContainer.setAdapter(spinnerContainerAdapter);
        spinnerTypeItem = (Spinner)findViewById(R.id.spinnerTypeFood);
        ArrayAdapter<Type> spinnerTypeAdapter = new ArrayAdapter<Type>(
                SearchActivity.this, android.R.layout.simple_spinner_item, types);
        spinnerTypeItem.setAdapter(spinnerTypeAdapter);
        textNbDays = (TextView)findViewById(R.id.showNbDays);
        textNbDays.setText("0");
        seekBarNbDays = (SeekBar)findViewById(R.id.seekBarNbDays);
        fastSearchBtn= (Button) findViewById(R.id.fastSearchBtn);
        completeSearchBtn= (Button) findViewById(R.id.completeSearchBtn);
    }

    private void initListener() {
        seekBarNbDays.setOnSeekBarChangeListener(new OnSeekBarNbDaysChange());
        fastSearchBtn.setOnClickListener(new OnClickListenerFastSearch());
        completeSearchBtn.setOnClickListener(new OnClickListenerCompleteSearch());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_page, menu);
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
                Intent intent = new Intent(SearchActivity.this, SearchActivity.class);
                startActivity(intent);
                return true;
            case R.id.homeBtn:
                intent = new Intent(SearchActivity.this, HomeActivity.class);
                startActivity(intent);
                return true;
            default :
                return super.onOptionsItemSelected(item);
        }
    }

    private ArrayList<Type> getAllType() {
        FappDAO fappDAO = new FappDAO(SearchActivity.this);
        return fappDAO.getAllType();
    }

    private ArrayList<Container> getAllContainer() {
        FappDAO fappDAO = new FappDAO(SearchActivity.this);
        return fappDAO.getAllContainers();
    }

    //Listener
    class OnSeekBarNbDaysChange implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            textNbDays.setText(String.valueOf(progress));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    class OnClickListenerFastSearch implements View.OnClickListener
    {

        public OnClickListenerFastSearch(){}

        public void onClick(View v)
        {
            Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
            intent.putExtra("Nbday",15);
            intent.putExtra("idContainer", -1);
            intent.putExtra("idType", -1);
            intent.putExtra("searchName", "");
            startActivity(intent);
        }
    }

    class OnClickListenerCompleteSearch implements View.OnClickListener
    {

        public void onClick(View v)
        {
            Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
            intent.putExtra("searchName", searchText.getText().toString());

            if(checkBoxContainer.isChecked())
                intent.putExtra("idContainer", containers.get(
                        spinnerContainer.getSelectedItemPosition()).getIdCont());
            else
                intent.putExtra("idContainer", -1);

            if(checkBoxTypeItem.isChecked())
                intent.putExtra("idType", types.get(spinnerTypeItem.getSelectedItemPosition()).getId());
            else
                intent.putExtra("idType", -1);

            intent.putExtra("Nbday", seekBarNbDays.getProgress());
            startActivity(intent);
        }
    }
}