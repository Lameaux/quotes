package com.euromoby.model;

public class Rate {

	private String base;
	private String currency;
	private double value;

	public Rate(String base, String currency, double value) {
		this.base = base;
		this.currency = currency;
		this.value = value;
	}

	public String getBase() {
		return base;
	}

	public String getCurrency() {
		return currency;
	}

	public double getValue() {
		return value;
	}

}
