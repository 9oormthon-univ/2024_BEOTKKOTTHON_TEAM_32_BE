package site.balpyo.ai.service;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.polly.AmazonPolly;
import com.amazonaws.services.polly.AmazonPollyClient;
import com.amazonaws.services.polly.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site.balpyo.ai.dto.PollyDTO;

import java.io.InputStream;

/**
 * @author dongheonlee
 */
@Service
@Slf4j
public class PollyService {

    /**
     * 입력된 텍스트와 선택된 빠르기에 따라 음성파일으로 변환하여 반환한다.
     *
     * @param pollyDTO
     * @return mp3 오디오 파일
     */
    public InputStream synthesizeSpeech(PollyDTO pollyDTO) {

        String inputText = pollyDTO.getText();
        int speed = pollyDTO.getSpeed();

        log.info("-------------------- 클라이언트가 요청한 대본 :" + inputText);
        log.info("-------------------- 클라이언트가 요청한 빠르기 :" + speed);

        // Amazon Polly 클라이언트 생성
        AmazonPolly amazonPolly = AmazonPollyClient.builder()
                .withRegion(Regions.AP_NORTHEAST_2) // 서울 리전
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .build();

        // 빠르기 계산
        float relativeSpeed = calculateRelativeSpeed(speed);

        log.info("-------------------- 선택한 빠르기 :" + relativeSpeed);

        // SSML 텍스트 생성
        String ssmlText = buildSsmlText(inputText, relativeSpeed);

        // SynthesizeSpeechRequest 생성 및 설정
        SynthesizeSpeechRequest synthesizeSpeechRequest = new SynthesizeSpeechRequest()
                .withText(ssmlText)
                .withOutputFormat(OutputFormat.Mp3) // MP3 형식
                .withVoiceId(VoiceId.Seoyeon) // 한국어 음성 변환 보이스
                .withTextType("ssml"); // SSML 형식 사용
        
        try { // 텍스트를 음성으로 변환하여 InputStream으로 반환
            SynthesizeSpeechResult synthesizeSpeechResult = amazonPolly.synthesizeSpeech(synthesizeSpeechRequest);

            log.info("-------------------- 요청된 문자열 개수 : " + synthesizeSpeechResult.getRequestCharacters());
            log.info("-------------------- 음성변환 요청 성공");

            return synthesizeSpeechResult.getAudioStream();
        } catch (AmazonPollyException e) {
            log.error("-------------------- 음성 변환 실패: " + e.getErrorMessage());
            throw e;
        }
    }

    /**
     * mp3 audio 생성 시, 빠르기 설정 메소드
     */
    private static float calculateRelativeSpeed(int speed) {
        // 기본 속도
        float baseSpeed = 1.1f;

        switch (speed) {
            case -2:
                return baseSpeed * 0.9f;
            case -1:
                return baseSpeed * 0.975f;
            case 1:
                return baseSpeed * 1.125f;
            case 2:
                return baseSpeed * 1.15f;
            default:
                return baseSpeed;
        }
    }

    /**
     * SSML 텍스트 생성 메소드
     */
    private String buildSsmlText(String inputText, float relativeSpeed) {
        StringBuilder ssmlBuilder = new StringBuilder();
        ssmlBuilder.append("<speak>");
        ssmlBuilder.append(String.format("<prosody rate=\"%f%%\">", relativeSpeed * 100));

        for (char ch : inputText.toCharArray()) {
            switch (ch) {
                case ',':
                    // 쉼표일 때 숨쉬기 태그 추가
                    ssmlBuilder.append("<break time=\"500ms\"/>");
                    break;
                case '.':
                case '!':
                    ssmlBuilder.append("<break time=\"600ms\"/>");
                    break;
                case '?':
                    ssmlBuilder.append("<break time=\"800ms\"/>");
                case '\n':
                    // 마침표나 개행 문자일 때 조금 더 긴 일시정지
                    ssmlBuilder.append("<break time=\"300ms\"/>");
                    break;
                default:
                    // 기본 문자 처리
                    ssmlBuilder.append(ch);
                    break;
            }
        }

        ssmlBuilder.append("</prosody>");
        ssmlBuilder.append("</speak>");
        return ssmlBuilder.toString();
    }
}