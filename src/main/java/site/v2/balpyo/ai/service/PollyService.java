package site.v2.balpyo.ai.service;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.polly.AmazonPolly;
import com.amazonaws.services.polly.AmazonPollyClient;
import com.amazonaws.services.polly.model.*;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.Permission;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import site.v2.balpyo.ai.dto.PollyDTO;
import site.v2.balpyo.ai.dto.upload.UploadResultDTO;
import site.v2.balpyo.common.s3.S3Client;


import java.io.*;
import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author dongheonlee
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PollyService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    private final S3Client s3Client;

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

    public UploadResultDTO synthesizeAndUploadSpeech(PollyDTO pollyDTO) {
        InputStream audioStream = synthesizeSpeech(pollyDTO); // 음성 파일 생성

        // 파일 이름 생성
        String fileName = UUID.randomUUID() + ".mp3";

        log.info("--------------------- " + fileName);

        // S3에 업로드
        Map<String, Object> audioInfo = uploadToS3(audioStream, fileName);

        String baseUploadURL = audioInfo.get("baseUploadURL").toString();
        int durationInSeconds = (int) audioInfo.get("durationInSeconds");
        log.info("--------------------- " + baseUploadURL);
        log.info("--------------------- " + durationInSeconds);


        return UploadResultDTO.builder()
                .profileUrl(baseUploadURL)
                .playTime(durationInSeconds)
                .build();
    }

    private Map<String, Object> uploadToS3(InputStream inputStream, String fileName) {

        log.info("--------------------- " + fileName);

        // S3에 업로드
        s3Client.getAmazonS3().putObject(bucketName, fileName, inputStream, new ObjectMetadata());

        // ACL 설정
        setAcl(s3Client.getAmazonS3(), fileName);

        // 업로드된 파일의 URL 생성
        String baseUploadURL = "https://balpyo-bucket.s3.ap-northeast-2.amazonaws.com/" + fileName;

        log.info("업로드 위치------" + baseUploadURL);

        // 임시 파일로 저장하여 처리
        int durationInSeconds = 0; // 초기화

        try {

            URL url = new URL(baseUploadURL);
            InputStream targetStream = url.openStream();
            fileName = Paths.get(url.getPath()).getFileName().toString();
            File localFile = new File(System.getProperty("java.io.tmpdir"), fileName);

//            File localFile = new File(baseUploadURL);
            log.info("Download------" + localFile);
            Files.copy(targetStream, localFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            targetStream.close(); // 스트림 닫기

            // MP3 파일의 재생 시간 계산
            MP3File mp3File = new MP3File(localFile);

            log.info("mp3 file" + mp3File);

            MP3AudioHeader audioHeader = (MP3AudioHeader) mp3File.getAudioHeader();
            durationInSeconds = audioHeader.getTrackLength();

            log.info("------------ 재생시간: " + durationInSeconds + "초");

            // 임시 파일 삭제
            localFile.delete();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 결과를 Map에 담아 반환
        Map<String, Object> result = new HashMap<>();
        result.put("baseUploadURL", baseUploadURL);
        result.put("durationInSeconds", durationInSeconds);
        return result;
    }


    public void setAcl(AmazonS3 s3, String objectPath) {
        AccessControlList objectAcl = s3.getObjectAcl(bucketName, objectPath);
        objectAcl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
        s3.setObjectAcl(bucketName, objectPath, objectAcl);
    }
}