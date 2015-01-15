package com.henallux.alex.fapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.henallux.alex.fapp.adapter.ListContainerAdapter;
import com.henallux.alex.fapp.azure.AzureDAO;
import com.henallux.alex.fapp.dialog.DialogEditContainer;
import com.henallux.alex.fapp.model.Container;
import com.henallux.alex.fapp.sql.FappDAO;

import java.util.ArrayList;
import java.util.Date;



public class HomeActivity extends ActionBarActivity{

    private EditText addContText;
    private Button addContBtn;
    ListContainerAdapter listContainerAdapter;
    private ListView listViewCont;
    private Spinner chooseTypeCont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initComponent();
        new AsyncGetAllContainers().execute();
        initListener();
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
            case R.id.homeBtn:
                intent = new Intent(HomeActivity.this, HomeActivity.class);
                startActivity(intent);
                return true;
            default :
                return super.onOptionsItemSelected(item);
        }
    }

    private void initComponent() {
        listViewCont = (ListView) findViewById(R.id.containerGrid);
        addContText =  (EditText) findViewById(R.id.addNewContText);
        addContBtn = (Button) findViewById(R.id.addContainerBtn);
        chooseTypeCont = (Spinner) findViewById(R.id.spinnerTypeContainer);
        listContainerAdapter = new ListContainerAdapter(HomeActivity.this, new ArrayList<Container>());
        listViewCont.setAdapter(listContainerAdapter);
    }

    private void initListener(){
        addContBtn.setOnClickListener(new OnClickListenerAddContainer());
        listViewCont.setOnItemClickListener(new OnChooseContainerListener());
        listViewCont.setOnItemLongClickListener(new OnContainerLongClickListener());
    }

    //Listener
    private class OnClickListenerAddContainer implements View.OnClickListener{
        @Override
        public void onClick(View v){
            if(addContText.getText().toString().equals("")){
                Toast.makeText(HomeActivity.this, getString(R.string.container_name_empty),
                        Toast.LENGTH_SHORT).show();
            } else {
                Container container = new Container(null, addContText.getText().toString(),
                        new Date(),chooseTypeCont.getSelectedItemPosition());

                FappDAO fappDAO = new FappDAO(HomeActivity.this);
                fappDAO.createContainer(container);

                container.setIdAndroid(fappDAO.getLastIdContainer());
                AzureDAO azureDAO= new AzureDAO();
                azureDAO.addContainer(container, HomeActivity.this);

                addContText.setText("");
                Toast.makeText(HomeActivity.this, getString(R.string.toast_add_container),
                        Toast.LENGTH_LONG).show();
                new AsyncGetAllContainers().execute();
            }
        }
    }

    private class OnChooseContainerListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(HomeActivity.this, ContainerActivity.class);
            intent.putExtra("idContainer",
                    listContainerAdapter.getContainers().get(position).getIdAndroid());
            startActivity(intent);
        }
    }

    private class OnContainerLongClickListener implements AdapterView.OnItemLongClickListener {

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            DialogEditContainer dialog  = new DialogEditContainer(view.getContext(),
                    listContainerAdapter.getContainers().get(position));
            dialog.setTitle(getString(R.string.title_dialog_edit_container) + " " +
                    listContainerAdapter.getContainers().get(position).getName());
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    new AsyncGetAllContainers().execute();
                }
            });
            dialog.show();
            return false;
        }
    }

    //asyncTask
    private class AsyncGetAllContainers extends AsyncTask<Void, Void, ArrayList<Container>>
    {

        @Override
        protected ArrayList<Container> doInBackground(Void... params) {
            FappDAO fappDAO = new FappDAO(HomeActivity.this);
            return fappDAO.getAllContainers();
        }

        @Override
        protected void onPostExecute (ArrayList<Container> result) {
            listContainerAdapter.setContainers(result);
            listContainerAdapter.notifyDataSetChanged();
        }
    }
}
