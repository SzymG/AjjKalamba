package com.example.szymo.ajjkalamba;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class HomeActivity extends AppCompatActivity {

    private Button losuj;
    private TextView kategoia, haslo;

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
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
