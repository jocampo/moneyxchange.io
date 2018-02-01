package com.belatrix.moneyxchange.api.business.impl;

import com.belatrix.moneyxchange.api.business.IExchangeRateService;
import com.belatrix.moneyxchange.api.data.CurrencyHelper;
import com.belatrix.moneyxchange.api.data.entities.Exception;
import com.belatrix.moneyxchange.api.data.repository.ExceptionRepository;
import com.belatrix.moneyxchange.api.models.ExchangeRateModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.*;

public class ExchangeRateService implements IExchangeRateService {


    private final Logger logger = LoggerFactory.getLogger("com.belatrix.moneyxchange.api.business.ExchangeRateService");

    ExceptionRepository exceptionsRepository;
    CurrencyHelper currencyHelper;

    public ExchangeRateService(ExceptionRepository exceptionsRepository, CurrencyHelper currencyHelper) {
        this.exceptionsRepository = exceptionsRepository;
        this.currencyHelper = currencyHelper;
    }


    @Override
    public ExchangeRateModel getExchangeRates(String base, String symbols) {

        ExchangeRateModel exchangeRates = new ExchangeRateModel();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            exchangeRates.setDate(sdf.format(new Date()));
            exchangeRates.setBase(base);
            if (symbols != null && symbols.trim().length()>0) {
                List<String> currencies = breakSymbolsIntoArray(symbols);
                Map<String, Double> rates = currencyHelper.getRates();

                Map<String, Double> ratesToReturn = new HashMap<>();
                for(String symbol : currencies) {
                    if(rates.containsKey(symbol) && symbol!=base) {
                        ratesToReturn.put(symbol, rates.get(symbol));
                    }
                }
                exchangeRates.setRates(ratesToReturn);
            } else {
                //return all currencies and rates
                exchangeRates.setRates(new HashMap<>(currencyHelper.getRates()));
                exchangeRates.getRates().remove(base);
            }
        } catch (java.lang.Exception ex) {
            logger.error("An error occurred while trying to build a response. " + ex.getMessage());
            Exception exModel = new Exception(ex);
            exceptionsRepository.save(exModel);
            return new ExchangeRateModel();
        }
        return exchangeRates;
    }

    private List<String> breakSymbolsIntoArray(String symbols){
        List<String> currencies = new ArrayList<>();
        if(symbols.length() > 3) {
            //Constains more than one symbol
            if(symbols.contains(",")) {
                currencies = Arrays.asList(symbols.split(","));
            } else if (symbols.contains("+")) {
                currencies = Arrays.asList(symbols.split("\\+"));
            }

        } else {
            currencies.add(symbols);
        }
        return currencies;
    }
}
