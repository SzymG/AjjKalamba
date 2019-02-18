package com.example.szymo.ajjkalamba;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class HomeActivity extends AppCompatActivity {

    private Button losuj, add;
    private TextView kategoia, haslo;

    private DatabaseHelper db;
    private ArrayList<String> listItem;
    private Cursor cursor;
    private Random rand;
    private String previousCategory = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        losuj = (Button) findViewById(R.id.button);
        kategoia = (TextView) findViewById(R.id.textView3);
        haslo = (TextView) findViewById(R.id.textView5);
        losuj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                losuj();
            }
        });

        add = (Button) findViewById(R.id.button3);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
                //Toast.makeText(HomeActivity.this,"Dodaj nowe hasło", Toast.LENGTH_SHORT).show();
            }
        });

        db = new DatabaseHelper(this);
        //db.clear();

        Cursor c = db.vievData();

        if(c.getCount() == 0){

            /*db.insertData("Osoba", "Gryfica Gnilda Bauza");
            db.insertData("Osoba", "Flubbershy Nikodem");
            db.insertData("Osoba","Bumfight Sparkle Gągor");
            db.insertData("Osoba","Dziunia Paulina");
            db.insertData("Osoba","Bumple Jack Krysia");
            db.insertData("Osoba", "Bimkie Guy Śmigło");
            db.insertData("Osoba","Random Bash")*/;
            db.insertData("Osoba","Szymon");
            db.insertData("Osoba","Marta");
            db.insertData("Osoba","Paulina");
            db.insertData("Zwierzę","Cynia");
            db.insertData("Zwierzę","Krysia");
            db.insertData("Osoba","Mama");
            db.insertData("Osoba","Tata");
        }

        listItem = new ArrayList<>();
        rand = new Random();

        viewData();

    }

    private void losuj() {

        int n = rand.nextInt(listItem.size()/2);
        if(listItem.get(2*n).compareTo(previousCategory) != 0 ){
            previousCategory = listItem.get(2*n);
            kategoia.setText(listItem.get(2*n));
            haslo.setText(listItem.get(2*n+1));
        }else{
            losuj();
        }
    }

    private void openDialog() {
        AddDialog addDialog = new AddDialog(true);
        addDialog.show(getSupportFragmentManager(),"Add dialog");
    }

    private void viewData() {

        this.cursor = db.vievData();
        this.listItem.clear();

        if (cursor.getCount() == 0){
            Toast.makeText(this,"Brak haseł", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()) {
                listItem.add(cursor.getString(1));
                listItem.add(cursor.getString(2));
            }

        }
    }

    public void insertNew(String kat, String has){

        boolean noRepeat = true;

        for(int i = 0; i < listItem.size(); i += 2){

            if (listItem.get(i).toUpperCase().compareTo(kat.toUpperCase()) == 0){
                if (listItem.get(i+1).toUpperCase().compareTo(has.toUpperCase()) == 0){
                    noRepeat = false;
                    Toast.makeText(this,"Wpisane hasło już istnieje!", Toast.LENGTH_SHORT).show();
                }
            }
        }

        if (noRepeat){
            db.insertData(kat,has);
            viewData();
            Toast.makeText(this,"Pomyślnie dodano nowe hasło!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
