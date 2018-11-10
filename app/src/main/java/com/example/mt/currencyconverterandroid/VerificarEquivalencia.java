package com.example.mt.currencyconverterandroid;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mt.currencyconverterandroid.Clases.Equivalencia;
import com.example.mt.currencyconverterandroid.Clases.Moneda;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class VerificarEquivalencia extends AppCompatActivity {

    private static Context appContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificar_equivalencia);
        appContext = getApplicationContext();

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
    public static Context getAppContext() {
        return appContext;
    }
    public void gotoCambiarDivisa(View view){
        goToActivity(new Intent(this,CambiarDivisa.class));
    }
    private void goToActivity(Intent intent){
        startActivity(intent);
    }
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.verificarEquivalencia:
//                View linearLayout = findViewById(R.id.linearLayout);
//                ((LinearLayout) linearLayout).removeAllViews();
//
//                TextView textView;
//                textView = new TextView(this);
//                textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
//                ((LinearLayout) linearLayout).addView(textView);
//
//                Spinner monedasSelectBox = findViewById(R.id.monedasSelectBox);
//                String monedaSeleccionada = monedasSelectBox.getSelectedItem().toString();
//                textView.setText("\nEquivalencias de las de la moneda ("+monedaSeleccionada+"): ");
//
////                Toast.makeText(this, monedaSeleccionada, Toast.LENGTH_SHORT).show();
//                getData(monedaSeleccionada,getAppContext());
//                for (Moneda moneda: MainActivity.monedasApp) {
//                    if(moneda.getMoneda().equals(monedaSeleccionada)){
//                        for(Equivalencia equivalencia: moneda.getEquivalencias()){
//                            textView = new TextView(this);
//                            textView.setText(equivalencia.getMoneda()+" $"+equivalencia.getValor());
//                            textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
//                            ((LinearLayout) linearLayout).addView(textView);
//                        }
//                        break;
//                    }
//
//                }
//                break;
//        }
//    }


}
