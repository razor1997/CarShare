package com.example.aktywnoscglowna;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.HashMap;
public class AktywnoscGlowna extends AppCompatActivity {

    int CODE_BASIC = 1;
    int CODE_FULL = 2;

    private final String COL0_NAME = "name" ;
    private final String COL1_NAME = "image";
    ListView list;
    ViewFlipper flipper;
    SimpleAdapter simpleAdapter;

    public static TypedArray IMAGE;

    final SQLiteOpenHelper DBHelper = new MarketDatabaseHelper(this);
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();

    String[] from;
    int[] to;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Resources resources = getResources();
        IMAGE = resources.obtainTypedArray(R.array.pictures);

        sqlSelect();


        simpleAdapter=
                new SimpleAdapter(this,arrayList,R.layout.row_table,from,to);
        list = findViewById(R.id.list);
        list.setAdapter(simpleAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AktywnoscGlowna.this,AktywnoscOpis.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });

        list.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });

        flipper = findViewById(R.id.viewflipper);
        flipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AktywnoscGlowna.this,AktywnoscOpis.class);
                intent.putExtra("id",(long)(flipper.getDisplayedChild()));
                startActivity(intent);
            }
        });

        Button b1 = findViewById(R.id.add);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ver = MarketDatabaseHelper.getDBVER();
                if(ver == 1) {
                    Intent intent = new Intent(AktywnoscGlowna.this, AddBasicEntryActivity.class);
                    startActivityForResult(intent, CODE_BASIC);
                }
                else if(ver == 2){
                    Intent intent = new Intent(AktywnoscGlowna.this, AddFullEntryActivity.class);
                    startActivityForResult(intent, CODE_FULL);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        arrayList.clear();
        sqlSelect();
        simpleAdapter.notifyDataSetChanged();


    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("flipper", flipper.getDisplayedChild());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        flipper.setDisplayedChild(savedInstanceState.getInt("flipper"));
    }

    public void sqlSelect(){
        SQLiteDatabase DB = DBHelper.getReadableDatabase();

        try
        {
            Cursor cursor = DB.query(
                    "STAND",
                    new String[]{"NAME ", "_id "},
                    null,
                    null,
                    null, null, null);
            cursor.moveToFirst();//Kursor zawsze trzeba ustawic

            for(int i = 0; i < cursor.getCount(); i++) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(COL0_NAME, cursor.getString(0));
                if(i < 11)
                    hashMap.put(COL1_NAME, Integer.toString(IMAGE.getResourceId(cursor.getInt(1) - 1,1)));
                else
                    hashMap.put(COL1_NAME, Integer.toString(IMAGE.getResourceId(0, 0)));
                arrayList.add(hashMap);
                cursor.moveToNext();
            }
            cursor.close();
            DB.close();
        }
        catch(Exception e)
        {
            Toast.makeText(AktywnoscGlowna.this,
                    "EXCEPTION: Baza",Toast.LENGTH_SHORT).show();
        }
        from= new String[]{COL0_NAME, COL1_NAME};
        to = new int[]{R.id.row_name, R.id.row_image};
    }

}
