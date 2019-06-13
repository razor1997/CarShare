package com.example.aktywnoscglowna;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;

public class DeleteEntryDialogFragment extends DialogFragment {
    final SQLiteOpenHelper DBHelper = new MarketDatabaseHelper(getContext());

    private int temp;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Czy chcesz usunąć wybrany element?")
                .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("qwe", Integer.toString(temp));
                        //((MarketDatabaseHelper) DBHelper).fullDelete(temp);
                    }
                })
                .setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.create();
        return builder.create();
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }
}