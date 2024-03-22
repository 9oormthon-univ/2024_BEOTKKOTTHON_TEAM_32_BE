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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class AIGenerateUtils {

    private static final String ENDPOINT = "https://api.openai.com/v1/chat/completions";

    public String createPromptString(String topic, String keywords, Integer sec) {
        return "Ignore all previous instructions. \n" +
                "\n" +
                "I want you to act as a presenter specialized in " + topic + ". My first request is for you to generate a script:\n" +
                "\n" +
                "Make a script by calculating 150ms per syllable, including spaces, and 250ms for line breaks, commas, and periods." +
                "Here's some context:\n" +
                "Topic - " + topic + "\n" +
                "Keywords - " + keywords + "\n" +
                "Amount - " + sec + " sec" +
                "\n" +
                "Please write in Korean.";
    }


    public ResponseEntity<Map> requestGPTTextGeneration(String prompt, float temperature, int maxTokens ,String API_KEY) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + API_KEY);

        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", prompt);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("messages", Arrays.asList(message));
        requestBody.put("temperature", temperature);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.postForEntity(ENDPOINT, requestEntity, Map.class);

        return response;
    }


//    public ResponseEntity<>Map> requestGPTTextToSpeech(){
//
//        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
//
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<Map> response = restTemplate.postForEntity(ENDPOINT, requestEntity, Map.class);
//
//        return new response;
//    }

}
