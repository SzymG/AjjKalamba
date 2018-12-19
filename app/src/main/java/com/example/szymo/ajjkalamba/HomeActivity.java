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

public class HomeActivity extends AppCompatActivity {

    private Button losuj;
    private TextView kategoia, haslo;

    private DatabaseHelper db;
    ArrayList<String> listItem;
    ArrayAdapter adapter;

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
                kategoia.setText("Osoba");
                haslo.setText("Bauza");
            }
        });

        db = new DatabaseHelper(this);

        listItem = new ArrayList<>();

        //viewData();

    }

    private void viewData() {

        Cursor cursor = db.vievData();

        if (cursor.getCount() != 0){
            Toast.makeText(this,"Brak haseł", Toast.LENGTH_SHORT).show();
        }

        else if(cursor.getCount() == 1){
            Toast.makeText(this,"Mam jeden rekord!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
