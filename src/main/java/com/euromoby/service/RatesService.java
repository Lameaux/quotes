package com.euromoby.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.euromoby.model.Currency;
import com.euromoby.model.Rate;

@Service
@PropertySource("classpath:currencies.properties")
public class RatesService {

	private static final Logger logger = LoggerFactory.getLogger(RatesService.class);

	@Autowired
	EcbClient ecbClient;

	@Autowired
    private Environment env;
	
	Map<String, Double> ecbRates = new ConcurrentHashMap<String, Double>();
	Set<String> currencyCodes = new ConcurrentSkipListSet<String>();
	long ratesUpdated = 0L;
	

	@PostConstruct
	private void loadSpotRates() {
		
		if (ratesUpdated + TimeUnit.HOURS.toMillis(1) > System.currentTimeMillis()) {
			return;
		}		
		
		try {
			Map<String, Double> rates = ecbClient.spotRates();
			for (Map.Entry<String, Double> entry : rates.entrySet()) {
				ecbRates.put(entry.getKey(), entry.getValue());
				currencyCodes.add(entry.getKey());
			}
			ratesUpdated = System.currentTimeMillis();
			logger.info("ECB rates loaded");
		} catch (Exception e) {
			logger.error("Unable to load rates from ECB", e);
		}
	}

	public List<Rate> spot(String baseCurrency) {
		loadSpotRates();
		List<Rate> rates = new ArrayList<Rate>();
		for (String currency : getSortedCurrencyCodes()) {
			Rate rate = new Rate(baseCurrency, currency, ecbRates.get(currency));
			rates.add(rate);
		}
		return rates;
	}

	public List<Currency> getCurrencies() {
		loadSpotRates();
		List<Currency> currencies = new ArrayList<Currency>();
		for (String currencyCode : currencyCodes) {
			String currencyName = env.getProperty("currency." + currencyCode);
			currencies.add(new Currency(currencyCode, currencyName != null ? currencyName : currencyCode ));
		}
		return currencies;
	}	
	
	private List<String> getSortedCurrencyCodes() {
		List<String> currencyCodesList = new ArrayList<String>(currencyCodes);
		Collections.sort(currencyCodesList);
		return currencyCodesList;
	}
	
}
