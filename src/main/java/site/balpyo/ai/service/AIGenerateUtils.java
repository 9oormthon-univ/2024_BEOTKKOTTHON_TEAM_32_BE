package site.balpyo.ai.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import site.balpyo.ai.dto.GPTResponse;

import java.util.HashMap;
import java.util.Map;

@Component
public class AIGenerateUtils {

    private static final String ENDPOINT = "https://api.openai.com/v1/completions";

    public String createPromptString(String topic, String keywords , Integer sec){
        return "Ignore all previous instructions. \n" +
                "\n" +
                "I want you to act as a presenter specialized in "+ topic +" My first request is for you to generate a script:\n" +
                "\n" +
                "Here's some context:\n" +
                "Topic - " + topic +"\n" +
                "Keywords - " + keywords +"\n" +
                "amount - "+sec + "sec"+
                "\n" +
                "Please write in Korean.";
    }

    public ResponseEntity<Map> requestGPTTextGeneration(String prompt, float temperature, int maxTokens ,String API_KEY) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + API_KEY);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model","gpt-3.5-turbo-instruct");
        requestBody.put("prompt", prompt);
        requestBody.put("temperature", temperature);
        requestBody.put("max_tokens", maxTokens);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.postForEntity(ENDPOINT, requestEntity, Map.class);

        return response;
    }




}
