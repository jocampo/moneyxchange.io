package com.belatrix.moneyxchange.api.controller;

import com.belatrix.moneyxchange.api.business.impl.ExchangeRateService;
import com.belatrix.moneyxchange.api.business.IExchangeRateService;
import com.belatrix.moneyxchange.api.data.CurrencyHelper;
import com.belatrix.moneyxchange.api.data.entities.Exception;
import com.belatrix.moneyxchange.api.data.repository.ExceptionRepository;
import com.belatrix.moneyxchange.api.models.ExchangeRateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.*;

import java.sql.Timestamp;

@RestController
public class ExchangeRateController {

    @Autowired
    private ExceptionRepository exceptionsRepository;

    @Autowired
    private CurrencyHelper currencyHelper;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(
            "/rates/exchange"
    )
    public ExchangeRateModel exchange(@RequestParam(required = false, value="base", defaultValue="USD") String base,
                                      @RequestParam(required = false, value="symbols", defaultValue="") String symbols) {

        IExchangeRateService exchangeRatesService = new ExchangeRateService(exceptionsRepository, currencyHelper);
        ExchangeRateModel exchangeRates = exchangeRatesService.getExchangeRates(base, symbols);

        return exchangeRates;
    }

}
