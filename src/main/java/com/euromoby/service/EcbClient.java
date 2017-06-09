package com.euromoby.service;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

@Component
public class EcbClient {

	private static final String URL_ECB_DAILY = "http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";

	private static final int CONNECTION_REQUEST_TIMEOUT = 1000;
	private static final int CONNECT_TIMEOUT = 2000;
	private static final int SOCKET_TIMEOUT = 5000;

	public Map<String, Double> spotRates() throws Exception {

		RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)
				.setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
		HttpClientBuilder builder = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig);

		CloseableHttpClient client = builder.build();

		CloseableHttpResponse response = client.execute(new HttpGet(URL_ECB_DAILY));
		try {
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new IOException();
			}
			String bodyAsString = EntityUtils.toString(response.getEntity());
			return parseXml(bodyAsString);

		} finally {
			response.close();
		}

	}

	private Map<String, Double> parseXml(String xmlString) throws Exception {

		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser = factory.newSAXParser();

		Map<String, Double> rates = new HashMap<String, Double>();

		DefaultHandler handler = new DefaultHandler() {
			public void startElement(String uri, String localName, String qName, Attributes attributes)
					throws SAXException {

				String currency = attributes.getValue("currency");
				String rate = attributes.getValue("rate");

				if (currency != null && rate != null) {
					rates.put(currency, Double.valueOf(rate));
				}
			}
		};

		saxParser.parse(new InputSource(new StringReader(xmlString)), handler);

		return rates;
	}

}
