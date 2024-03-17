package site.balpyo.ai.controller;

import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.balpyo.ai.dto.PollyDTO;
import site.balpyo.ai.service.PollyService;
import site.balpyo.common.dto.CommonResponse;
import site.balpyo.common.dto.ErrorEnum;

import java.io.IOException;
import java.io.InputStream;


/**
 * @author dongheonlee
 * AWS polly를 활용한 tts 구현 컨트롤러
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/polly")
public class PollyController {

    @Value("${secrets.BALPYO_API_KEY}") //TODO :: 임시 api 시크릿 키 구현 (차후 로그인 연동시 삭제예정)
    public String BALPYO_API_KEY;

    private final PollyService pollyService;

    /**
     * @param pollyDTO
     * @return 호출 시, 요청정보에 따른 mp3 음성파일을 반환(audioBytes)한다.
     */
    @PostMapping("/generateAudio")
    public ResponseEntity<?> synthesizeText(@RequestBody PollyDTO pollyDTO) {

        if (!BALPYO_API_KEY.equals(pollyDTO.getBalpyoAPIKey())) {
            return CommonResponse.error(ErrorEnum.BALPYO_API_KEY_ERROR);
        }

        try {
            // Amazon Polly와 통합하여 텍스트를 음성으로 변환
            InputStream audioStream = pollyService.synthesizeSpeech(pollyDTO);

            // InputStream을 byte 배열로 변환
            byte[] audioBytes = IOUtils.toByteArray(audioStream);

            // MP3 파일을 클라이언트에게 반환
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("testAudio", "speech.mp3");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(audioBytes);

        } catch (IOException e) {
            e.printStackTrace();
            return CommonResponse.error(ErrorEnum.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 입력한 대본을 음성 파일 변환 시, 음절당 기준값에 따라 계산된 소요시간을 반환한다.
     * @param pollyDTO
     * @return
     */
    @PostMapping("/estimateDuration")
    public ResponseEntity<CommonResponse> estimateSpeechDuration(@RequestBody PollyDTO pollyDTO) {

        if (!BALPYO_API_KEY.equals(pollyDTO.getBalpyoAPIKey())) {
            return CommonResponse.error(ErrorEnum.BALPYO_API_KEY_ERROR);
        }

        String inputText = pollyDTO.getText();

        try {
            int durationInSeconds = pollyService.estimateSpeechDuration(inputText);


            return CommonResponse.success(durationInSeconds);

        } catch (Exception e) {
            e.printStackTrace();
            return CommonResponse.error(ErrorEnum.INTERNAL_SERVER_ERROR);
        }
    }
}
