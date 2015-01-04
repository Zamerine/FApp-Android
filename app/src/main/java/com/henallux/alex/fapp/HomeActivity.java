package com.henallux.alex.fapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


public class HomeActivity extends ActionBarActivity{

    private ArrayList<String> filenames = new ArrayList<>();

    private EditText addContText;
    private Button addContBtn;
    private ListView listViewCont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //POUR LES TESTS
        filenames.add("Frigo1");
        filenames.add("Frigo2");
        filenames.add("Congélateur");

        listViewCont = (ListView) findViewById(R.id.containerGrid);
        listViewCont.setAdapter(new ButtonAdapter(this));

        addContText =  (EditText) findViewById(R.id.addNewContText);

        addContBtn = (Button) findViewById(R.id.addContainerBtn);
        addContBtn.setOnClickListener(new OnClickListenerAddContainer());







    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(intent);
                return true;
            default :
                return super.onOptionsItemSelected(item);
        }
    }

    public class ButtonAdapter extends BaseAdapter {
        private Context mContext;

        // Gets the context so it can be used later
        public ButtonAdapter(Context c) {

            mContext = c;
        }

        // Total number of things contained within the adapter
        public int getCount() {
            return filenames.size();
        }

        // Require for structure, not really used in my code.
        public Object getItem(int position) {

            return null;
        }

        // Require for structure, not really used in my code. Can
        // be used to get the id of an item in the adapter for
        // manual control.
        public long getItemId(int position) {

            return position;
        }

        public View getView(int position,View convertView, ViewGroup parent) {

            Button btn;
            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                btn = new Button(mContext);
                btn.setLayoutParams(new ListView.LayoutParams(100, 55));
                btn.setPadding(8, 8, 8, 8);
            }
            else {
                btn = (Button) convertView;
            }
            //exus
            btn.setText(filenames.get(position));
            // filenames is an array of strings
            btn.setTextColor(Color.WHITE);
            //btn.setBackgroundResource(R.drawable.button);
            btn.setId(position);
            // Set the onclicklistener so that pressing the button fires an event
            // We will need to implement this onclicklistner.
            btn.setOnClickListener(new OnClickListenerChooseContainer(position));

            return btn;
        }
    }

    class OnClickListenerAddContainer implements View.OnClickListener{
        @Override
        public void onClick(View v){
            filenames.add(addContText.getText().toString());
            listViewCont.invalidateViews(); // TODO OK CA MARCHE pour le moment; possible que quand ca sera lié à la DB ca ne marchera plus ==>  http://stackoverflow.com/questions/19656325/listview-not-updating-after-database-update-and-adapter-notifydatasetchanged/19657500#19657500
        }
    }


    class OnClickListenerChooseContainer implements View.OnClickListener
    {
        private final int position;

        public OnClickListenerChooseContainer(int position)
        {
            this.position = position;
        }

        public void onClick(View v)
        {
            //TODO utiliser un activityForResult pour récuperer le résultat et ainsi vérifier qu'il n'y a pas eu d'erreur ?
            Intent intent = new Intent(HomeActivity.this, ContainerActivity.class);
            intent.putExtra("textTitleContainer",filenames.get(this.position));
            startActivity(intent);
        }
    }
}
