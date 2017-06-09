package com.euromoby.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.euromoby.model.Currency;
import com.euromoby.service.RatesService;

@RestController
@RequestMapping("/api")
public class CurrencyController {

	@Autowired
	private RatesService ratesService;

	@RequestMapping("/currencies")
	public List<Currency> currencies() {
		return ratesService.getCurrencies();
	}

}
