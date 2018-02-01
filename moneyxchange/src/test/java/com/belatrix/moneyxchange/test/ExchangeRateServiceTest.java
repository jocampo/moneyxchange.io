package com.belatrix.moneyxchange.test;

import com.belatrix.moneyxchange.api.business.IExchangeRateService;
import com.belatrix.moneyxchange.api.business.impl.ExchangeRateService;
import com.belatrix.moneyxchange.api.data.CurrencyHelper;
import com.belatrix.moneyxchange.api.data.repository.ExceptionRepository;
import com.belatrix.moneyxchange.api.models.ExchangeRateModel;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
public class ExchangeRateServiceTest {

    @Test
    public void getExchangeRates() {
        ExceptionRepository exR = mock(ExceptionRepository.class);

        CurrencyHelper cur = mock(CurrencyHelper.class);
        HashMap<String, Double> rates = new HashMap<>();
        rates.put("PEN", 5.000);
        when(cur.getRates()).thenReturn(rates);
        IExchangeRateService service = new ExchangeRateService(exR, cur);


        ExchangeRateModel resultRates = service.getExchangeRates("USD","PEN");


        Assert.assertEquals(resultRates.getRates().size(),1);
        Assert.assertEquals(resultRates.getRates().get("PEN"),(Double) 5.000);
        Assert.assertEquals(resultRates.getBase(),"USD");
    }
}