package com.example.aktywnoscglowna;

import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddFullEntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_full_entry);

        final EditText title = findViewById(R.id.newTitle);
        final EditText description = findViewById(R.id.newDescription);
        Button addButton = findViewById(R.id.adden);
        Button cancelButton = findViewById(R.id.cancel);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddFullEntryActivity.this, AktywnoscGlowna.class);
                Toast.makeText(AddFullEntryActivity.this, "cofaj", Toast.LENGTH_SHORT).show();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String titleName = title.getText().toString();
                String descriptionText = description.getText().toString();

                SQLiteOpenHelper DBHelper = new MarketDatabaseHelper(AddFullEntryActivity.this);
                ((MarketDatabaseHelper) DBHelper).addFullEntry(titleName, descriptionText);

                Intent intent = new Intent(AddFullEntryActivity.this, AktywnoscGlowna.class);
                Toast.makeText(AddFullEntryActivity.this, "Dodano do bazy", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }
}

