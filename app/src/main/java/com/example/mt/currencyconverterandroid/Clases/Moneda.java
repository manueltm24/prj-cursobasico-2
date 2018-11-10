package com.example.mt.currencyconverterandroid.Clases;

import java.util.List;

public class Moneda {
    final static String MONEDA_US = "USD";
    final static String MONEDA_DOP = "DOP";
    final static String MONEDA_EUR = "EUR";

    private String moneda;
    private List<Equivalencia> equivalencias;

    public String getMoneda() {
        return moneda;
    }
    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }
    public List<Equivalencia> getEquivalencias() {
        return equivalencias;
    }
    public void setEquivalencias(List<Equivalencia> equivalencias) {
        this.equivalencias = equivalencias;
    }
    public Moneda() {
    }

    public Moneda(String moneda, List<Equivalencia> equivalencias) {
        this.moneda = moneda;
        this.equivalencias = equivalencias;
    }

    @Override
    public String toString() {
        return moneda;
    }
}
