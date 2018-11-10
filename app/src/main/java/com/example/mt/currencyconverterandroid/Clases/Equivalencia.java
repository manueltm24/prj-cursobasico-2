package com.example.mt.currencyconverterandroid.Clases;

public class Equivalencia {
    private String moneda;
    private Double valor;

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Equivalencia() {

    }

    public Equivalencia(String moneda, Double valor) {
        this.moneda = moneda;
        this.valor = valor;
    }
}
