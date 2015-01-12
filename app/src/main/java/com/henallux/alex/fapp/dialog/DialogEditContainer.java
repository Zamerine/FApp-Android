package com.henallux.alex.fapp.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.henallux.alex.fapp.R;
import com.henallux.alex.fapp.model.Container;
import com.henallux.alex.fapp.sql.FappDAO;

/**
 * Created by Alexandre on 12/01/2015.
 */
public class DialogEditContainer extends Dialog {
    private Container container;
    private boolean containerIsDeleted;

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
        this.containerIsDeleted = false;
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
                    container.setName(nameContainer.getText().toString());
                    FappDAO fappDAO = new FappDAO(context);
                    fappDAO.updateContainer(container);
                }
                DialogEditContainer.this.dismiss();
            }
        }
    }

    private class OnClickDeleteContainer implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            AlertDialog.Builder alertDialogBuilder = createAlertDialogBuilder();
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.setOnDismissListener(new OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    if(containerIsDeleted)
                        DialogEditContainer.this.dismiss();
                }
            });
            alertDialog.show();
            Log.i("debug","passage alert");
        }

        public AlertDialog.Builder createAlertDialogBuilder() {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle(context.getResources().getString(
                    R.string.title_dialog_confirm_deleting));
            alertDialogBuilder.setMessage(context.getResources().getString(
                    R.string.message_dialog_confirm_deleting) + " " + container.getName()
                    + context.getResources().getString(R.string.message2_dialog_confirm_deleting));
            alertDialogBuilder.setPositiveButton(R.string.valid_dialog_confirm_deleting,
                    new OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FappDAO fappDAO = new FappDAO(context);
                            fappDAO.deleteContainer(container);
                            containerIsDeleted = true;
                        }
                    });
            alertDialogBuilder.setNegativeButton(R.string.no_dialog_confirm_deleting,
                    new OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            return alertDialogBuilder;
         }
    }

    private class OnClickBack implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            DialogEditContainer.this.dismiss();
        }
    }
}
