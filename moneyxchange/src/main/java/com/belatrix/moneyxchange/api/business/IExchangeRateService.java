package com.belatrix.moneyxchange.api.business;

import com.belatrix.moneyxchange.api.models.ExchangeRateModel;

public interface IExchangeRateService {
    ExchangeRateModel getExchangeRates(String base, String symbols);
}
