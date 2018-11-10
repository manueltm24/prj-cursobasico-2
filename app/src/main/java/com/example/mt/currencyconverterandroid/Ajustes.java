package com.example.mt.currencyconverterandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mt.currencyconverterandroid.Clases.Equivalencia;
import com.example.mt.currencyconverterandroid.Clases.Moneda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.mt.currencyconverterandroid.MainActivity.getAppContext;
import static com.example.mt.currencyconverterandroid.MainActivity.getData;

public class Ajustes extends AppCompatActivity implements View.OnClickListener {
    int[] idsButtons = {
            R.id.guardarConfiguraciones,
            R.id.updateTasas,
            R.id.guardarMonedaBase
    };
    @BindView(R.id.monedasBaseAjustes)
    Spinner spiner_monedasBase;

    @BindView(R.id.monedaBaseApp)
    Spinner spiner_monedaBaseApp;

    @BindView(R.id.usd)
    EditText usd;

    @BindView(R.id.dop)
    EditText dop;

    @BindView(R.id.eur)
    EditText eur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);
        ButterKnife.bind(this);

        List<String> divisas = new ArrayList<String>();
        for (Moneda moneda : MainActivity.monedasApp) {
            divisas.add(moneda.getMoneda());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, divisas);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiner_monedasBase.setAdapter(dataAdapter);
        spiner_monedaBaseApp.setAdapter(dataAdapter);

        spiner_monedaBaseApp.setSelection(dataAdapter.getPosition(MainActivity.monedaBaseApp.getMoneda()));

        for(int id: idsButtons){
            findViewById(id).setOnClickListener(this);
        }

        for (Moneda moneda : MainActivity.monedasApp) {
            if (moneda.getMoneda().equals(MainActivity.monedaBaseApp.getMoneda())) {
                for (Equivalencia equivalencia : moneda.getEquivalencias()) {
                    switch (equivalencia.getMoneda()) {
                        case "USD":
                            usd.setText(String.format("%.2f",equivalencia.getValor()), TextView.BufferType.EDITABLE);
                            break;
                        case "DOP":
                            dop .setText(String.format("%.2f",equivalencia.getValor()), TextView.BufferType.EDITABLE);
                            break;
                        case "EUR":
                            eur.setText(String.format("%.2f",equivalencia.getValor()), TextView.BufferType.EDITABLE);
                            break;
                    }
                }
            }
        }

        spiner_monedasBase.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                for (Moneda moneda : MainActivity.monedasApp) {
                    if (moneda.getMoneda().equals(spiner_monedasBase.getSelectedItem().toString())) {
                        usd.setText("N/A", TextView.BufferType.EDITABLE);
                        dop.setText("N/A", TextView.BufferType.EDITABLE);
                        eur.setText("N/A", TextView.BufferType.EDITABLE);

                        for (Equivalencia equivalencia : moneda.getEquivalencias()) {
                            switch (equivalencia.getMoneda()) {
                                case "USD":
                                    usd.setText(String.format("%.3f",equivalencia.getValor()), TextView.BufferType.EDITABLE);
                                    break;
                                case "DOP":
                                    dop .setText(String.format("%.3f",equivalencia.getValor()), TextView.BufferType.EDITABLE);
                                    break;
                                case "EUR":
                                    eur.setText(String.format("%.3f",equivalencia.getValor()), TextView.BufferType.EDITABLE);
                                    break;
                            }
                        }
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }

        });
    }
    private void goToActivity(Intent intent){
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.guardarMonedaBase:
                spiner_monedaBaseApp = findViewById(R.id.monedaBaseApp);
                for (Moneda moneda : MainActivity.monedasApp) {
                    if (moneda.getMoneda().equals(spiner_monedaBaseApp.getSelectedItem().toString())) {
                        MainActivity.monedaBaseApp= moneda;
                        AlertDialog alertDialog = new AlertDialog.Builder(Ajustes.this).create();
                        alertDialog.setTitle("Felicidades!");
                        alertDialog.setMessage("Configuracion salvada exitosamente.");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                }
                break;
            case R.id.guardarConfiguraciones:
                dop = findViewById(R.id.dop);
                usd = findViewById(R.id.usd);
                eur = findViewById(R.id.eur);
                for (Moneda moneda : MainActivity.monedasApp) {
                    if (moneda.getMoneda().equals(spiner_monedasBase.getSelectedItem().toString())) {
                        for (Equivalencia equivalencia : moneda.getEquivalencias()) {
                            switch (equivalencia.getMoneda()) {
                                case "USD":
                                    if(!usd.getText().toString().equals("N/A"))
                                        equivalencia.setValor(Double.valueOf(usd.getText().toString()));
                                    break;
                                case "DOP":
                                    if(!dop.getText().toString().equals("N/A"))
                                        equivalencia.setValor(Double.valueOf(dop.getText().toString()));
                                    break;
                                case "EUR":
                                    if(!eur.getText().toString().equals("N/A"))
                                        equivalencia.setValor(Double.valueOf(eur.getText().toString()));
                                    break;
                            }
                        }
                        AlertDialog alertDialog = new AlertDialog.Builder(Ajustes.this).create();
                        alertDialog.setTitle("Felicidades!");
                        alertDialog.setMessage("Configuracion salvada exitosamente.");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                }
                break;
                case R.id.updateTasas:
                    spiner_monedasBase=findViewById(R.id.monedasBaseAjustes);
                    usd= findViewById(R.id.usd);
                    dop= findViewById(R.id.dop);
                    eur= findViewById(R.id.eur);

                    usd.setText("N/A", TextView.BufferType.EDITABLE);
                    dop.setText("N/A", TextView.BufferType.EDITABLE);
                    eur.setText("N/A", TextView.BufferType.EDITABLE);
                    for (Moneda moneda: MainActivity.monedasApp) {
                        getData(moneda.getMoneda(),getAppContext());
                    }
                    AlertDialog alertDialog = new AlertDialog.Builder(Ajustes.this).create();
                    alertDialog.setTitle("Felicidades!");
                    alertDialog.setMessage("JSON recibido, actualizacion completada!");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    goToActivity(new Intent(getAppContext(),MainActivity.class));
                                }
                            });
                    alertDialog.show();
                    break;
        }
    }
}
