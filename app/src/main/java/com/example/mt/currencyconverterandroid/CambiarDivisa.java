package com.example.mt.currencyconverterandroid;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mt.currencyconverterandroid.Clases.Equivalencia;
import com.example.mt.currencyconverterandroid.Clases.Moneda;

import java.util.ArrayList;
import java.util.List;

public class CambiarDivisa extends AppCompatActivity implements View.OnClickListener {
    int[] idsButtons = {
            R.id.convertirDivisa,
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_divisa);

        Spinner spinner_monedasBase = findViewById(R.id.monedasBaseSelectBox);
        Spinner spinner_monedasConvertir = findViewById(R.id.monedasSelectBox);
        // Spinner Drop down elements
        List<String> divisas = new ArrayList<String>();
        for (Moneda moneda: MainActivity.monedasApp) {
            divisas.add(moneda.getMoneda());
        }
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, divisas);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner_monedasBase.setAdapter(dataAdapter);
        spinner_monedasConvertir.setAdapter(dataAdapter);

        for(int id: idsButtons){
            findViewById(id).setOnClickListener(this);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.convertirDivisa:
//                Toast.makeText(this, "Cambiar!", Toast.LENGTH_SHORT).show();
                Moneda monedaBase = new Moneda();
                EditText montoConvertir = findViewById(R.id.montoConvertir);
                Spinner spinner_monedasBase = findViewById(R.id.monedasBaseSelectBox);
                String monedasBaseSeleccionada = spinner_monedasBase.getSelectedItem().toString();

                Spinner spinner_monedasConvertir = findViewById(R.id.monedasSelectBox);
                String monedasConvertirSeleccionada = spinner_monedasConvertir.getSelectedItem().toString();


                if(montoConvertir.getText().toString().matches("")){
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

                    // set title
                    alertDialogBuilder.setTitle("Error!");

                    // set dialog message
                    alertDialogBuilder
                            .setMessage("Digite un monto para convertir!")
                            .setCancelable(true);
                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();
                }
                else if(monedasConvertirSeleccionada.equals(monedasBaseSeleccionada)){
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                    // set title
                    alertDialogBuilder.setTitle("Error!");
                    // set dialog message
                    alertDialogBuilder
                            .setMessage("Moneda Base y Moneda de cambio no pueden ser iguales.!")
                            .setCancelable(true);
                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    // show it
                    alertDialog.show();
                }
                else{
                    for (Moneda moneda: MainActivity.monedasApp) {
                        if(moneda.getMoneda().equals(monedasBaseSeleccionada)){
                            monedaBase=moneda;
                            break;
                        }
                    }

                    for (Equivalencia equivalencia: monedaBase.getEquivalencias()) {
                        if(equivalencia.getMoneda().equals(monedasConvertirSeleccionada)){
                            TextView resultado = findViewById(R.id.resultado);
                            Double resultadoMostrar = Double.parseDouble(montoConvertir.getText().toString())*equivalencia.getValor();
                            resultado.setText(String.valueOf(String.format("%.4f",resultadoMostrar)));
                            break;
                        }
                    }
                }


                break;

        }
    }
}
