package com.henallux.alex.fapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class SearchActivity extends ActionBarActivity {

    private Button fastSearchBtn;
    private Button completeSearchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        fastSearchBtn= (Button) findViewById(R.id.fastSearchBtn);
        fastSearchBtn.setOnClickListener(new OnClickListenerFastSearch());

        completeSearchBtn= (Button) findViewById(R.id.completeSearchBtn);
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
            default :
                return super.onOptionsItemSelected(item);
        }
    }

    class OnClickListenerFastSearch implements View.OnClickListener
    {

        public OnClickListenerFastSearch(){}

        public void onClick(View v)
        {
            //TODO utiliser un activityForResult pour récuperer le résultat et ainsi vérifier qu'il n'y a pas eu d'erreur ?
            Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
            intent.putExtra("sortOfSearch",0);
            startActivity(intent);
        }
    }

    class OnClickListenerCompleteSearch implements View.OnClickListener
    {

        public OnClickListenerCompleteSearch()
        {

        }

        public void onClick(View v)
        {
            //TODO utiliser un activityForResult pour récuperer le résultat et ainsi vérifier qu'il n'y a pas eu d'erreur ?
            Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
            intent.putExtra("sortOfSearch",1);
            startActivity(intent);
        }
    }
}
