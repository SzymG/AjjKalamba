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

    private Button losuj;
    private TextView kategoia, haslo;

    private DatabaseHelper db;
    private ArrayList<String> listItem;
    private Cursor cursor;
    private Random rand;


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
                int n = rand.nextInt(listItem.size()/2);
                kategoia.setText(listItem.get(2*n));
                haslo.setText(listItem.get(2*n+1));
            }
        });

        db = new DatabaseHelper(this);
        db.clear();
        db.insertData("Osoba", "Gryfica Gnilda Bauza");
        db.insertData("Osoba", "Flubbershy Nikodem");
        db.insertData("Osoba","Bumfight Sparkle Gągor");
        db.insertData("Osoba","Dziunia Paulina");
        db.insertData("Osoba","Bumple Jack Krysia");
        db.insertData("Osoba", "Bimkie Guy Śmigło");
        db.insertData("Osoba","Random Bash");



        listItem = new ArrayList<>();
        rand = new Random();

        viewData();

    }

    private void viewData() {

        this.cursor = db.vievData();

        if (cursor.getCount() == 0){
            Toast.makeText(this,"Brak haseł", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                listItem.add(cursor.getString(1));
                listItem.add(cursor.getString(2));
            }
        }
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
