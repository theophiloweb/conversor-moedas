package com.teophilosilva.model.app;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class Coin {
    private static final List<String> SIX_MAJOR_CURRENCIES = Arrays.asList("USD", "EUR", "GBP", "JPY", "CAD", "BRL");
    private List<String> coin  = new ArrayList<>();
    private JsonObject currencies;

    public Collection<String> getCoin(){
        return this.coin;
    }

    public void setCoin(List<String> coin) {
        this.coin = coin;
    }

    public void currenciesCoin() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.fastforex.io/currencies?api_key=905e901757-fd2c8764c0-scb4ww"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Parse the response JSON to get currencies
        String responseBody = response.body();
        Gson gson = new Gson();
        this.currencies = gson.fromJson(responseBody, JsonObject.class);

        if (currencies != null && currencies.has("currencies")) {
            JsonObject currenciesObject = currencies.getAsJsonObject("currencies");
            ArrayList<String> coin = new ArrayList<>();
            for (String key : SIX_MAJOR_CURRENCIES) {
                if (currenciesObject.has(key)) {
                    String value = currenciesObject.get(key).getAsString();
                    String result = "\"" + key + "\":\"" + value + "\"\n";
                    coin.add(result);
                }
            }
            this.setCoin(coin);
        }
        
        
    }

    // Getter para acessar os dados das moedas
    public JsonObject getCurrencies() {
        return currencies;
    }
}



