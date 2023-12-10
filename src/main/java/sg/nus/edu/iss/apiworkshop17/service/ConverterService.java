package sg.nus.edu.iss.apiworkshop17.service;

import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;

@Service
public class ConverterService {
    
    private final String apiKey = "apiKey=7f5d584c5e66cc7eb5f5";
    private final String allCurrenciesUrl = "https://free.currconv.com/api/v7/countries?";
    private final String conversionUrl = "https://free.currconv.com/api/v7/convert?q=";

    public List<String> getAllCurrencies() {
        List<String> lst = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        String url = sb.append(allCurrenciesUrl)
            .append(apiKey)
            .toString();

        System.out.printf("\n URL ALL CURRENCIES: %s\n", url);

        RequestEntity req = RequestEntity
            .get(url)
            .build();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> resp = restTemplate.exchange(req, String.class);

        String jsonStr = resp.getBody();
        // System.out.printf("\n JSONSTRING OF ALL CURRENCIES: %s\n", jsonStr);
        JsonReader jr = Json.createReader(new StringReader(jsonStr));

        JsonObject jsonObj = jr.readObject().getJsonObject("results");

        Set<Entry<String, JsonValue>> entries = jsonObj.entrySet();
        
        for (Entry<String, JsonValue> entry: entries) {
            // System.out.printf("\n PER CURRENCY: %s\n", entry);
            String currName = entry.getValue().asJsonObject().getString("currencyId");
            if (!lst.contains(currName)) {
                lst.add(currName);
            }
        }
        return lst;
    }

    public Float convert(String currFrom, String currTo, String amount) {
        Float amountFloat = Float.parseFloat(amount);

        StringBuilder sb = new StringBuilder();
        String full_url = sb
            .append(conversionUrl)
            .append(currFrom)
            .append("_")
            .append(currTo)
            .append("&compact=ultra&")
            .append(apiKey)
            .toString();

        RequestEntity req = RequestEntity
            .get(full_url)
            .build();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> resp = restTemplate.exchange(req, String.class);
        
        JsonReader jr = Json.createReader(new StringReader(resp.getBody()));
        JsonObject jsonObj = jr.readObject();
        String key = jsonObj.keySet().iterator().next();

        Float rate = jsonObj.getJsonNumber(key).bigDecimalValue().floatValue();

        Float convertedAmount = amountFloat * rate;

        return convertedAmount;
    }
}
