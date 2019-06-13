package com.example.aktywnoscglowna;

import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddBasicEntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_basic_entry);

        final EditText title = findViewById(R.id.newTitle);
        Button addButton = findViewById(R.id.adden);
        Button cancelButton = findViewById(R.id.cancel);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddBasicEntryActivity.this, AktywnoscGlowna.class);
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String titleName = title.getText().toString();

                SQLiteOpenHelper DBHelper = new MarketDatabaseHelper(AddBasicEntryActivity.this);
                ((MarketDatabaseHelper) DBHelper).addBasicEntry(titleName);

                Intent intent = new Intent(AddBasicEntryActivity.this, AktywnoscGlowna.class);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }
}
