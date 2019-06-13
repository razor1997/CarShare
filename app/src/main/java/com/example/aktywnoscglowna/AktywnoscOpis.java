package com.example.aktywnoscglowna;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class AktywnoscOpis extends AppCompatActivity {

    TextView nazwa, opis;
    ImageView obraz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aktywnosc_opis);

        nazwa =  findViewById(R.id.nazwa);
        opis =  findViewById(R.id.opis);
        obraz = findViewById(R.id.obraz);

        int id = (int) getIntent().getLongExtra("id", 0);

        final SQLiteOpenHelper DBHelper = new MarketDatabaseHelper(this);
        int ver = MarketDatabaseHelper.getDBVER();
        FloatingActionButton floatingActionButton = findViewById(R.id.fbutton);
        if(ver == 1) {
            try {
                SQLiteDatabase DB = DBHelper.getReadableDatabase();
                Cursor cursor = DB.query(
                        "STAND ",
                        new String[]{"NAME "},
                        "_id = ? ",
                        new String[]{Integer.toString(id + 1)},
                        null, null, null);
                cursor.moveToFirst();//Kursor zawsze trzeba ustawic
                nazwa.setText(cursor.getString(0));
                id %= 11;
                obraz.setImageResource(AktywnoscGlowna.IMAGE.getResourceId(id, 0));

                cursor.close();
                DB.close();
            } catch (Exception e) {
                Toast.makeText(AktywnoscOpis.this,
                        "EXCEPTION: Bazav1", Toast.LENGTH_SHORT).show();
            }

        }
        else if(ver == 2) {
            try {
                SQLiteDatabase DB = DBHelper.getReadableDatabase();
                Cursor cursor = DB.query(
                        "STAND ",
                        new String[]{"NAME ", "DESCRIPTION "},
                        "_id = ? ",
                        new String[]{Integer.toString(id + 1)},
                        null, null, null);
                cursor.moveToFirst();//Kursor zawsze trzeba ustawic

                nazwa.setText(cursor.getString(0));
                opis.setText(cursor.getString(1));
                if(id < 11)
                    obraz.setImageResource(AktywnoscGlowna.IMAGE.getResourceId(id, 0));

                cursor.close();
                DB.close();
            } catch (Exception e) {
                Toast.makeText(AktywnoscOpis.this,
                        "EXCEPTION: Bazav2", Toast.LENGTH_SHORT).show();
            }
        }
        if(ver == 2){
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = (int) getIntent().getLongExtra("id", 0);
                Intent intent = new Intent(AktywnoscOpis.this, AddDescriptionActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
                finish();
            }
        });
        } else {
            floatingActionButton.hide();
        }
    }
}
