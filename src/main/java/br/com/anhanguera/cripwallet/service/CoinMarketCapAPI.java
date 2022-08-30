package br.com.anhanguera.cripwallet.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CoinMarketCapAPI {

	private static String apiKey = "e1a69d1b-1bf1-4426-8a45-47e607808dfb";

	private ObjectMapper objectMapper;

	public List<Map<String, Object>> getPrice(List<String> names) {
		String uri = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest";
		List<NameValuePair> paratmers = new ArrayList<>();
		paratmers.add(new BasicNameValuePair("slug", String.join(",", names)));
		try {
			String result = makeAPICall(uri, paratmers);
			Map<String, Object> resultObject = objectMapper.readValue(result, Map.class);
			Map<String, Object> data = (Map<String, Object>) resultObject.get("data");
			Collection<Object> moedas = (Collection<Object>) data.values();
			return moedas.stream().map((moeda) -> {
				Map<String, Object> moeadaMap = (Map<String, Object>) moeda;
				Map<String, Object> quoteMap = (Map<String, Object>) moeadaMap.get("quote");
				Map<String, Object> usdMap = (Map<String, Object>) quoteMap.get("USD");
				Number price = (Number) usdMap.get("price");
				return Map.of("slug", moeadaMap.get("slug"), "price", (Object) price.doubleValue());
			}).collect(Collectors.toList());
		} catch (IOException e) {
			throw new RuntimeException("Error: cannont access content - " + e.toString());
		} catch (URISyntaxException e) {
			throw new RuntimeException("Error: Invalid URL " + e.toString());
		}
	}

	public String makeAPICall(String uri, List<NameValuePair> parameters) throws URISyntaxException, IOException {
		String response_content = "";

		URIBuilder query = new URIBuilder(uri);
		query.addParameters(parameters);

		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet request = new HttpGet(query.build());

		request.setHeader(HttpHeaders.ACCEPT, "application/json");
		request.addHeader("X-CMC_PRO_API_KEY", apiKey);

		CloseableHttpResponse response = client.execute(request);

		try {
			HttpEntity entity = response.getEntity();
			response_content = EntityUtils.toString(entity);
			EntityUtils.consume(entity);
		} finally {
			response.close();
		}

		return response_content;
	}

	@Autowired
	public void setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}
}