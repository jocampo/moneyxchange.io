package com.belatrix.moneyxchange.api.data;

import com.belatrix.moneyxchange.api.data.entities.Exception;
import com.belatrix.moneyxchange.api.data.repository.ExceptionRepository;
import com.belatrix.moneyxchange.api.models.ExchangeRateModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Configuration
public class CurrencyHelper {

    private final String currenciesFilePath = "currencies.txt";
    private final Logger logger = LoggerFactory.getLogger("com.belatrix.moneyxchange.api.data.CurrencyHelper");
    private Map<String, Double> rates;
    private List<String> currencies;

    public CurrencyHelper(ExceptionRepository exceptionsRepository) {
        //Load currencies into an array
        currencies = new ArrayList<>();

        Resource resource = new ClassPathResource(currenciesFilePath);
        try {
            InputStream resourceInputStream = resource.getInputStream();
            StringBuilder textBuilder = new StringBuilder();
            try (Reader reader = new BufferedReader(new InputStreamReader
                    (resourceInputStream, Charset.forName(StandardCharsets.UTF_8.name())))) {
                int c = 0;
                while ((c = reader.read()) != -1) {
                    textBuilder.append((char) c);
                }
                currencies = Arrays.asList(textBuilder.toString().split("\n"));
            }
        } catch (IOException ioEx) {
            logger.error("An error occurred while loading the currency codes. " + ioEx.getMessage());
            Exception ex = new Exception(ioEx);
            exceptionsRepository.save(ex);
        }
    }

    @Scheduled(fixedRate=600000)
    public void calculateExchangeRates() {
        logger.info("Attempting to recalculate exchange rates.");
        //"Calculate" (Randomize) the currencies exch. rate every X ms (10minutes)
        if(currencies != null) {
            if(rates == null) {
                rates = new HashMap<>();
                for(String curr : currencies) {
                    Double rate = ThreadLocalRandom.current().nextDouble(0.1, 4);
                    rates.put(curr, new BigDecimal(rate).setScale(5, RoundingMode.HALF_UP).doubleValue());
                }
            } else {
                for (Map.Entry<String, Double> entry : rates.entrySet())
                {
                    Double rate = ThreadLocalRandom.current().nextDouble(0.1, 4);
                    entry.setValue(new BigDecimal(rate).setScale(5, RoundingMode.HALF_UP).doubleValue());
                }
            }
        }
    }


    public Map<String, Double> getRates() {
        return rates;
    }
}
