import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class Client {
    public static void main(String[] args) {
        String sensorName = "Sensor";
        registerSensor(sensorName);

        double min = -100;
        double max = 100;
        Random random = new Random();

        for (int i = 0; i < 500; i++) {
            System.out.println(i);
            sendMeasurement(min + (max - min) * random.nextDouble(), random.nextBoolean(), sensorName);
        }
    }

    public static void registerSensor(String sensorName) {
        String url = "http://localhost:8080/sensors/registration";

        Map<String, Object> jsonData = new HashMap<>();
        jsonData.put("name", sensorName);

        makePostRequestWithJSONData(url, jsonData);
    }

    public static void sendMeasurement(double value, boolean raining, String sensorName) {
        String url = "http://localhost:8080/measurements/add";

        Map<String, Object> jsonData = new HashMap<>();
        jsonData.put("value", value);
        jsonData.put("raining", raining);
        jsonData.put("sensor", Map.of("name", sensorName));

        makePostRequestWithJSONData(url, jsonData);
    }

    private static void makePostRequestWithJSONData(String url, Map<String, Object> jsonData) {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> request = new HttpEntity<>(jsonData, headers);

        restTemplate.postForObject(url, request, String.class);

        try {
            System.out.println("Measurement successfully sent to the server");
        } catch (HttpClientErrorException e) {
            System.out.println("Error");
            System.out.println(e.getMessage());
        }
    }

}
