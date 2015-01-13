package com.henallux.alex.fapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class SearchResultActivity extends ActionBarActivity {

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        bundle=this.getIntent().getExtras();

        switch(bundle.getInt("sortOfSearch")){
            case 0 : fastSearch();
                break;
            case 1 : completeSearch();

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_result, menu);
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
                Intent intent = new Intent(SearchResultActivity.this, SearchActivity.class);
                startActivity(intent);
                return true;
            case R.id.homeBtn:
                intent = new Intent(SearchResultActivity.this, HomeActivity.class);
                startActivity(intent);
                return true;
            default :
                return super.onOptionsItemSelected(item);
        }
    }

    private void fastSearch(){
        //TODO récupération des données de azure
    }

    private void completeSearch(){
        //TODO récupération des données de azure
    }
}