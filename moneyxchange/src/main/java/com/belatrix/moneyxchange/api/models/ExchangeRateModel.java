package com.belatrix.moneyxchange.api.models;

import java.util.Date;
import java.util.Map;

public class ExchangeRateModel {
    private String base;

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Map<String, Double> getRates() {
        return rates;
    }

    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }

    private String date;
    private Map<String, Double> rates;
}
