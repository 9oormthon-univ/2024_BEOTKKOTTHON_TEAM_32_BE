package site.v2.balpyo.ai.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class AIGenerateUtils {
    private static final String ENDPOINT = "https://api.openai.com/v1/chat/completions";

    public String createPromptString(String topic, String keywords, Integer sec) {
        log.info("-------------------- 프롬프트 명령 실행");

        // 초기화한 값에 해당하는 글자 수와 시간 비율 계산
        int initialCharacterCount = 425; // 초기화한 공백 포함 글자 수
        double characterPerSecond = (double) initialCharacterCount / 60.0; // 초당 평균 글자 수

        log.info("-------------------- 초당 평균 글자 수 : " + characterPerSecond);

        // 주어진 시간(sec)에 해당하는 글자 수 계산
        int targetCharacterCount = (int) (sec * characterPerSecond);

        log.info("-------------------- 주어진 시간(" + sec + "초)에 해당하는 예상 글자수 : " + targetCharacterCount);

        // 주어진 시간(sec)에 해당하는 바이트 수 계산
        int targetByteCount = targetCharacterCount * 3; // 한글은 3바이트로 가정

        log.info("-------------------- 예상 바이트수 : " + targetByteCount);


        return  "You need to create a presentation script in Korean.\n" +
                "The topic is " + topic + ", and the keywords are " + keywords + ".\n" +
                "Please generate a script of " + targetByteCount + " bytes.\n" +
                "Count every character, including spaces, special characters, and line breaks, as one byte.\n" +
                "When creating a script, exclude characters such as '(', ')', ''', '-', '[', ']' and '_'.\n" +
                "This is to prevent bugs that may occur in scripts that include request values in the response.\n" +
                "It must be exactly " + targetByteCount + " bytes long. " +
                "GPT, you're smart enough to provide me with a script of " + targetByteCount + " bytes, right? Can you do that?";
    }



    public ResponseEntity<Map> requestGPTTextGeneration(String prompt, float temperature, int maxTokens ,String API_KEY) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + API_KEY);

        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", prompt);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-4-0125-preview");
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
