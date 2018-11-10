package com.example.mt.currencyconverterandroid;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.TextView;

import com.example.mt.currencyconverterandroid.Clases.Equivalencia;
import com.example.mt.currencyconverterandroid.Clases.Moneda;


public class VerificarEquivalencia extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificar_equivalencia);
        TextView monedaBase = findViewById(R.id.monedaBaseTextView);
        TextView usd = findViewById(R.id.usdTextView);
        TextView dop = findViewById(R.id.dopTextView);
        TextView eur = findViewById(R.id.eurTextView);

        monedaBase.setText(MainActivity.monedaBaseApp.getMoneda());//SETEAR MONEDA BASE A TEXTVIEW

        for (Moneda moneda: MainActivity.monedasApp) {
            if(moneda.getMoneda().equals(MainActivity.monedaBaseApp.getMoneda())){
                for(Equivalencia equivalencia: moneda.getEquivalencias()){
                    switch (equivalencia.getMoneda()){
                        case "USD":
                            usd.setText(equivalencia.getValor().toString());
                            break;
                        case "DOP":
                            dop.setText(equivalencia.getValor().toString());
                            break;
                        case "EUR":
                            eur.setText(equivalencia.getValor().toString());
                            break;
                    }
                }
                break;
            }

        }




    }
    public void gotoCambiarDivisa(View view){
        goToActivity(new Intent(this,CambiarDivisa.class));
    }
    private void goToActivity(Intent intent){
        startActivity(intent);
    }

}
