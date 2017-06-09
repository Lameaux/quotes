package com.euromoby.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.euromoby.model.Rate;
import com.euromoby.service.RatesService;

@RestController
@RequestMapping("/api/rates")
public class RatesController {

	private static final String CURRENCY_EUR = "EUR";	
	
	@Autowired
	private RatesService ratesService;

	@RequestMapping("/spot")	
	public List<Rate> spotRates() {
		return ratesService.spot(CURRENCY_EUR);
	}
	
}
