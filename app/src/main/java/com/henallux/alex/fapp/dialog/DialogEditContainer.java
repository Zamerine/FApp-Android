package com.henallux.alex.fapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.henallux.alex.fapp.R;
import com.henallux.alex.fapp.model.Container;

/**
 * Created by Alexandre on 12/01/2015.
 */
public class DialogEditContainer extends Dialog {
    private Container container;

    private EditText nameContainer;
    private Button bntEditContainer;
    private Button bntDeleteContainer;
    private Button bntBack;
    private Context context;

    public DialogEditContainer(Context context, Container container) {
        super(context);
        this.context = context;
        this.setContentView(R.layout.dialog_edit_container);
        this.container = container;

        initComponent();
        initListener();
    }

    private void initComponent() {
        nameContainer = (EditText)findViewById(R.id.edit_name_container);
        nameContainer.setText(container.getName());
        bntEditContainer = (Button)findViewById(R.id.bnt_edit_container);
        bntDeleteContainer = (Button)findViewById(R.id.bnt_delete_container);
        bntBack = (Button)findViewById(R.id.bnt_back);
    }

    private void initListener(){
        bntEditContainer.setOnClickListener(new OnClickEditContainer());
        bntDeleteContainer.setOnClickListener(new OnClickDeleteContainer());
        bntBack.setOnClickListener(new OnClickBack());
    }

    private class OnClickEditContainer implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if(nameContainer.getText().toString().equals("")){
                Toast.makeText(context, context.getResources().getString(R.string.container_name_empty),
                        Toast.LENGTH_SHORT).show();
            } else {
                if(!nameContainer.getText().toString().equals(container.getName())) {
                    //TODO appel methode update container dand fappdao
                }
                DialogEditContainer.this.dismiss();
            }
        }
    }

    private class OnClickDeleteContainer implements View.OnClickListener {

        @Override
        public void onClick(View v) {

        }
    }

    private class OnClickBack implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            DialogEditContainer.this.dismiss();
        }
    }
}
