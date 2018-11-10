package com.example.mt.currencyconverterandroid;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mt.currencyconverterandroid.Clases.Equivalencia;
import com.example.mt.currencyconverterandroid.Clases.Moneda;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static ArrayList<Moneda> monedasApp = new ArrayList<>();
    static Moneda monedaBaseApp = new Moneda();
    private static Context appContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appContext = getApplicationContext();

        /**
         * MONEDAS QUE SE CREAN AL INICIAR LA VENTANA PRINCIPAL
         */
        if(monedasApp.isEmpty()){
            monedasApp.add(new Moneda("USD",null));
            monedasApp.add(new Moneda("DOP",null));
            monedasApp.add(new Moneda("EUR",null));
        }
        /**
         * CICLO QUE BUSCA LA MONEDA BASE SELECCIONADA DENTRO DE LAS QUE YA EXISTEN.
         */
        for (Moneda moneda: monedasApp) {
            if(moneda.getMoneda().equals("DOP")){
                monedaBaseApp=moneda;
                break;
            }
        }
        /**
         * CICLO QUE ACTUALIZA CADA MONEDA DESDE UN SERVICIO QUE RECIBE UN JSON
         */
        for (Moneda moneda: monedasApp) {
            getData(moneda.getMoneda(),getAppContext());

        }
    }
    public static Context getAppContext() {
        return appContext;
    }
    public void gotoVerificarEquivalencia(View view){
        goToActivity(new Intent(this,VerificarEquivalencia.class));
    }
    public void gotoSettings(View view){
        goToActivity(new Intent(this,Ajustes.class));
    }
    public void gotoCambiarDivisa(View view){
        goToActivity(new Intent(this,CambiarDivisa.class));
    }
    private void goToActivity(Intent intent){
        startActivity(intent);
    }
    public static void getData(final String monedaBase, final Context appContext){
        final String TASA_US = "USD";
        final String TASA_DOP = "DOP";
        final String TASA_EUR = "EUR";

        final ArrayList<String> monedasConvertir = new ArrayList<>();
        switch (monedaBase){
            case TASA_US:
                monedasConvertir.add(TASA_US+"_"+TASA_DOP);
                monedasConvertir.add(TASA_US+"_"+TASA_EUR);
                break;
            case TASA_DOP:
                monedasConvertir.add(TASA_DOP+"_"+TASA_US);
                monedasConvertir.add(TASA_DOP+"_"+TASA_EUR);
                break;
            case TASA_EUR:
                monedasConvertir.add(TASA_EUR+"_"+TASA_DOP);
                monedasConvertir.add(TASA_EUR+"_"+TASA_US);
                break;
        }
        StringBuilder builder = new StringBuilder();
        String url = "http://free.currencyconverterapi.com/api/v5/convert?q=";
        builder.append(url);
        for(int i=0;i<monedasConvertir.size();i++){
            builder.append(monedasConvertir.get(i));
            if(i!=monedasConvertir.size()-1){
                builder.append(",");
            }
        }
        builder.append("&compact=y");
        StringRequest request = new StringRequest(builder.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                JSONObject obj;
                System.out.println(string);
                try {
                    String output = "";
                    List<Equivalencia> equivalencias = new ArrayList<>();
                    for (String conversion: monedasConvertir) {
                        obj = (JSONObject) new JSONObject(string).get(conversion);
                        equivalencias.add(new Equivalencia(conversion.replace(monedaBase+"_",""),obj.getDouble("val")));
                    }
                    for (Moneda moneda: MainActivity.monedasApp) {
                        if(moneda.getMoneda().equals(monedaBase)){
                            moneda.setEquivalencias(equivalencias);
                            break;
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(appContext, "Some error occurred!!", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue rQueue = Volley.newRequestQueue(appContext);
        rQueue.add(request);
    }


}
