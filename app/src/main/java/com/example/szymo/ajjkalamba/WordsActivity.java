package com.example.szymo.ajjkalamba;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class WordsActivity extends AppCompatActivity {

    private ArrayAdapter<String> adapter;
    private ArrayList listItem;
    private DatabaseHelper db;
    private ListView words_list;
    private Button back, edit, delete;
    private View selected;
    private String kategoria, haslo;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        db = new DatabaseHelper(this);

        edit = findViewById(R.id.edit_btn);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        delete = findViewById(R.id.delete_btn);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected.setSelected(false);
                db.deleteData(kategoria, haslo);
                adapter.remove(adapter.getItem(pos));
                adapter.notifyDataSetChanged();
                Toast.makeText(WordsActivity.this,"Pomyślnie usunięto " + kategoria + ": " + haslo, Toast.LENGTH_SHORT).show();

            }
        });

        words_list = findViewById(R.id.word_list);
        words_list.setClickable(true);
        words_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position, long id) {
                pos = position;
                arg1.setSelected(true);
                selected = arg1;
                String s = words_list.getItemAtPosition(position).toString();
                String parts[] = s.split(": ");
                kategoria = parts[0];
                haslo = parts[1];

            }
        });
        listItem = new ArrayList();
        back = findViewById(R.id.button7);

        adapter=new ArrayAdapter<String>(this,
                R.layout.mytextview, R.id.text1,
                listItem);
        words_list.setAdapter(adapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        viewData();
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void viewData() {
        Cursor cursor = db.vievData();
        while (cursor.moveToNext()){

            String s = "";
            //s+=cursor.getString(0)+" ";                       ID
            s+=cursor.getString(1) + ": ";      //   Kategoria
            s+=cursor.getString(2);             //   Hasło
            listItem.add(s);
        }
    }
}
