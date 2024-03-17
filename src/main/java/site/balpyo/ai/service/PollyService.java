package site.balpyo.ai.service;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.polly.AmazonPolly;
import com.amazonaws.services.polly.AmazonPollyClient;
import com.amazonaws.services.polly.model.*;
import org.springframework.stereotype.Service;
import site.balpyo.ai.dto.PollyDTO;

import java.io.InputStream;

/**
 * @author dongheonlee
 */
@Service
public class PollyService {

    /**
     * 입력된 텍스트의 예상 음성 파일 재생 시간을 계산하여 반환한다.
     *
     * @param text 입력 텍스트
     * @return 예상 음성 파일 재생 시간 (초)
     */
    public int estimateSpeechDuration(String text) {
        // 예상 소요 시간 계산을 위한 변수
        int totalDuration = 0;

        // 입력 텍스트에서 "\n"과 "."을 제외한 문자열 추출
        String cleanedText = text.replaceAll("[\\n.]", "");

        // 입력 텍스트를 한 음절씩 분리하여 처리
        for (char c : cleanedText.toCharArray()) {
            // 띄어쓰기나 쉼표가 있을 때마다 한 번씩 숨을 쉬는 시간 추가
            if (c == ' ' || c == ',') {
                totalDuration += 1000; // 1초의 숨쉬는 시간으로 가정
            } else {
                int durationPerSyllable = 150; // 음절당 150ms로 설정
                totalDuration += durationPerSyllable;
            }
        }

        // 소요 시간을 초로 변환하여 반환
        return totalDuration / 1000; // ms를 초로 변환
    }


    /**
     * 입력된 텍스트와 선택된 빠르기에 따라 음성파일으로 변환하여 반환한다.
     *
     * @param pollyDTO
     * @return mp3 오디오 파일
     */
    public InputStream synthesizeSpeech(PollyDTO pollyDTO) {

        String inputText = pollyDTO.getText();
        int speed = pollyDTO.getSpeed();

        // Amazon Polly 클라이언트 생성
        AmazonPolly amazonPolly = AmazonPollyClient.builder()
                .withRegion(Regions.AP_NORTHEAST_2) // 서울 리전
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .build();

        // 빠르기 계산
        float relativeSpeed = calculateRelativeSpeed(speed);

        // SynthesizeSpeechRequest 생성
        SynthesizeSpeechRequest synthesizeSpeechRequest = new SynthesizeSpeechRequest()
                .withText(inputText)
                .withOutputFormat(OutputFormat.Mp3) // MP3 형식
                .withVoiceId(VoiceId.Seoyeon) // 한국어 음성 변환 보이스
                .withTextType("ssml") // SSML 형식 사용 -> <prosody> 태그와 rate로 설정 가능
                .withText("<speak><prosody rate=\"" + relativeSpeed + "\">" + inputText + "</prosody></speak>");

        // 텍스트를 음성으로 변환하여 InputStream으로 반환
        SynthesizeSpeechResult synthesizeSpeechResult = amazonPolly.synthesizeSpeech(synthesizeSpeechRequest);
        return synthesizeSpeechResult.getAudioStream();
    }


    /**
     * mp3 audio 생성 시, 빠르기 설정 메소드
     */
    private static float calculateRelativeSpeed(int speed) {
        // 기본 속도
        float baseSpeed = 1.0f;

        switch (speed) {
            case -2:
                return baseSpeed * 0.5f;
            case -1:
                return baseSpeed * 0.8f;
            case 1:
                return baseSpeed * 1.2f;
            case 2:
                return baseSpeed * 1.5f;
            default:
                return baseSpeed;
        }
    }


}