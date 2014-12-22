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
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;


public class HomeActivity extends ActionBarActivity {

    private String[] filenames = {
            "Frigo 1",
            "Frigo 2",
            "Congélateur"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ListView listViewCont = (ListView) findViewById(R.id.containerGrid);
        listViewCont.setAdapter(new ButtonAdapter(this));
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class ButtonAdapter extends BaseAdapter {
        private Context mContext;

        // Gets the context so it can be used later
        public ButtonAdapter(Context c) {
            mContext = c;
        }

        // Total number of things contained within the adapter
        public int getCount() {
            return filenames.length;
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

        public View getView(int position,
                            View convertView, ViewGroup parent) {
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
            btn.setText(filenames[position]);
            // filenames is an array of strings
            btn.setTextColor(Color.WHITE);
            //btn.setBackgroundResource(R.drawable.button);
            btn.setId(position);
            // Set the onclicklistener so that pressing the button fires an event
            // We will need to implement this onclicklistner.
            btn.setOnClickListener(new MyOnClickListener(position));

            return btn;
        }
    }

    class MyOnClickListener implements View.OnClickListener
    {
        private final int position;

        public MyOnClickListener(int position)
        {
            this.position = position;
        }

        public void onClick(View v)
        {
            //TODO utiliser un activityForResult pour récuperer le résultat ?
            Intent intent = new Intent(HomeActivity.this, ContainerActivity.class);
            intent.putExtra("textTitleContainer",filenames[this.position]);
            startActivity(intent);
        }
    }
}
