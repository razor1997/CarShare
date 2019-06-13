package com.example.aktywnoscglowna;

import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddDescriptionActivity extends AppCompatActivity {
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_description_entry);
        id = getIntent().getIntExtra("id", 0);

        final EditText description = findViewById(R.id.newDescription);
        Button addButton = findViewById(R.id.adden);
        Button cancelButton = findViewById(R.id.cancel);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddDescriptionActivity.this, AktywnoscOpis.class);
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String descriptionText = description.getText().toString();

                SQLiteOpenHelper DBHelper = new MarketDatabaseHelper(AddDescriptionActivity.this);
                ((MarketDatabaseHelper) DBHelper).addDescription(descriptionText, id);

                Intent intent = new Intent(AddDescriptionActivity.this, AktywnoscOpis.class);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}