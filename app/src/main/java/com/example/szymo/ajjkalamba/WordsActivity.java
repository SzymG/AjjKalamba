package com.example.szymo.ajjkalamba;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class WordsActivity extends AppCompatActivity {

    private ArrayAdapter<String> adapter;
    private ArrayList<String> listItem;
    private DatabaseHelper db;
    private ListView words_list;
    private Button back, edit, delete, add;
    private View selected;
    private String kategoria, haslo;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        db = new DatabaseHelper(this);
        selected = null;

        edit = findViewById(R.id.edit_btn);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selected != null){
                    AddDialog addDialog = new AddDialog(false, kategoria, haslo);
                    addDialog.show(getSupportFragmentManager(),"Add dialog");
                }else {
                    Toast.makeText(WordsActivity.this,"Wybierz hasło do edytowania!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        delete = findViewById(R.id.delete_btn);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selected != null){
                    selected.setSelected(false);
                    db.deleteData(kategoria, haslo);
                    adapter.remove(adapter.getItem(pos));
                    adapter.notifyDataSetChanged();
                    Toast.makeText(WordsActivity.this,"Pomyślnie usunięto " + kategoria.toUpperCase() + ": " + haslo.toUpperCase(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(WordsActivity.this,"Nie zaznaczono hasła do usunięcia",Toast.LENGTH_SHORT).show();
                }
            }
        });

        add = findViewById(R.id.add_btn);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDialog addDialog = new AddDialog(true, true);
                addDialog.show(getSupportFragmentManager(),"Add dialog");
            }
        });

        words_list = findViewById(R.id.word_list);
        words_list.setClickable(true);
        words_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position, long id) {
                pos = position;

                if(selected == arg1){
                    arg1.setSelected(false);
                    selected = null;
                }else {
                    arg1.setSelected(true);
                    selected = arg1;
                }

                String s = words_list.getItemAtPosition(position).toString();
                String parts[] = s.split(": ");
                kategoria = parts[0];
                haslo = parts[1];

            }
        });
        listItem = new ArrayList();
        back = findViewById(R.id.button7);

        adapter = new ArrayAdapter<String>(this,
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
            //s+=cursor.getString(0)+" ";                       ID1
            s+=cursor.getString(1) + ": ";      //   Kategoria
            s+=cursor.getString(2);             //   Hasło
            listItem.add(s);
        }
    }

    public boolean insertNew(String kat, String has) {

        boolean b = false;

        if (checkRepeat(kat, has)){

            db.deleteData(kategoria,haslo);
            adapter.remove(adapter.getItem(pos));
            db.insertData(kat,has);
            Toast.makeText(this,"Pomyślnie dodano nowe hasło!", Toast.LENGTH_SHORT).show();
            b = true;
            listItem.add(kat+": " +has);
            adapter.notifyDataSetChanged();
        }
        return b;
    }

    public void insert(String kat, String has) {

        if (checkRepeat(kat, has)){

            db.insertData(kat,has);
            Toast.makeText(this,"Pomyślnie dodano nowe hasło!", Toast.LENGTH_SHORT).show();
            listItem.add(kat+": " +has);
            adapter.notifyDataSetChanged();
        }
    }

    private boolean checkRepeat(String kat, String has) {

        boolean noRepeat = true;

        for(int i = 0; i < listItem.size(); i++){
            String s = listItem.get(i);
            String parts[] = s.split(": ");

            String katS = parts[0];
            String hasS = parts[1];

            if (katS.toUpperCase().compareTo(kat.toUpperCase()) == 0){

                if (hasS.toUpperCase().compareTo(has.toUpperCase()) == 0){
                    noRepeat = false;
                    Toast.makeText(this,"Wpisane hasło już istnieje!", Toast.LENGTH_SHORT).show();
                }
            }
        }

        return noRepeat;

    }
}
