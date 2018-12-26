package com.example.szymo.ajjkalamba;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class AddDialog extends AppCompatDialogFragment {

    private boolean createNew, wordsAct;
    private EditText editTextKategoria, editTextHasło;
    private  AlertDialog.Builder builder;
    private View view;
    private String kat, has;

    public AddDialog(boolean createNew) {
        this.createNew = createNew;
    }

    public AddDialog(boolean createNew, String kategoria, String haslo) {
        this.createNew = createNew;
        this.kat = kategoria;
        this.has = haslo;
    }

    public AddDialog(boolean createNew, boolean wordsAct) {
        this.createNew = createNew;
        this.wordsAct = wordsAct;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.layout_dialog, null);
        String title;

        editTextKategoria = view.findViewById(R.id.category);
        editTextHasło = view.findViewById(R.id.word);

        if (createNew){
            title = "Dodaj nowe hasło!";
        }else {
            title = "Edytuj hasło!";
            editTextHasło.setText(has);
            editTextKategoria.setText(kat);
        }
        builder.setView(view).setTitle(title).setNegativeButton("Powrót", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        })
        .setPositiveButton("Zatwierdź", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        return builder.create();
    }

    @Override   //Przeciążenie kliknięcia na przycisk zatwierdz
    public void onResume()
    {
        super.onResume();
        final AlertDialog d = (AlertDialog)getDialog();
        if(d != null)
        {
            Button positiveButton = (Button) d.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Boolean wantToCloseDialog = false;
                    //Do stuff, possibly set wantToCloseDialog to true then...
                    if(editTextKategoria.getText().toString().compareTo("") == 0){
                        Toast.makeText(getContext(),"Wpisz nazwę kategorii!", Toast.LENGTH_SHORT).show();
                    }
                    else if (editTextHasło.getText().toString().compareTo("") == 0){
                        Toast.makeText(getContext(),"Wpisz hasło!", Toast.LENGTH_SHORT).show();
                    }else{
                        wantToCloseDialog = true;
                        if (createNew){
                            if(wordsAct){
                                WordsActivity wa = (WordsActivity) getActivity();
                                wa.insert(editTextKategoria.getText().toString(),
                                        editTextHasło.getText().toString());
                            }else{
                                HomeActivity ha = (HomeActivity) getActivity();
                                ha.insertNew(editTextKategoria.getText().toString(),
                                        editTextHasło.getText().toString());
                            }

                            Toast.makeText(getContext(),"Pomyślnie dodano nowe hasło!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            WordsActivity wa = (WordsActivity) getActivity();
                            wa.insertNew(editTextKategoria.getText().toString(),
                                    editTextHasło.getText().toString());
                            Toast.makeText(getContext(),"Pomyślnie edytowano hasło!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    if(wantToCloseDialog)
                        d.dismiss();
                    //else dialog stays open. Make sure you have an obvious way to close the dialog especially if you set cancellable to false.
                }
            });
        }
    }
}
