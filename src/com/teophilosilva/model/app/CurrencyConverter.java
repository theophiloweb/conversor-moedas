package com.teophilosilva.model.app;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CurrencyConverter {

    private double value;
    private String coinOne;
    private String coinTwo;

    private String result;

    public CurrencyConverter(double value, String coinOne, String coinTwo) {
        this.value = value;
        this.coinOne = coinOne;
        this.coinTwo = coinTwo;
        this.result = "";
    }

    public double getValue() {
        return value;
    }
    public void setValue(double value) {
        this.value = value;
    }
    public String getCoinOne() {
        return this.coinOne;
    }
    public void setCoinOne(String coinOne) {
       this.coinOne = coinOne;
    }
    public String getCoinTwo() {
        return this.coinTwo;
    }
    public void setCoinTwo(String coinTwo) {
        this.coinTwo = coinTwo;
    }

    public String getResult() {
        return this.result;
    }
    private void setResult(String result) {
        this.result = result;
    }

    public void currencyConverter() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.fastforex.io/convert?from=" +getCoinOne() + "&to=" + getCoinTwo() + "&amount=" + getValue() + "&api_key=905e901757-fd2c8764c0-scb4ww"))
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .join();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String responseBody = response.body();
        Gson gson = new Gson();
        JsonObject res = gson.fromJson(responseBody, JsonObject.class);
        this.setResult(String.valueOf(res.get("result")));


    }

}
