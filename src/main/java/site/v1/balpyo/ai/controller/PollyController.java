package site.v1.balpyo.ai.controller;

import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.v1.balpyo.ai.dto.PollyDTO;
import site.v1.balpyo.ai.dto.upload.UploadResultDTO;
import site.v1.balpyo.ai.service.PollyService;
import site.v1.balpyo.common.dto.CommonResponse;
import site.v1.balpyo.common.dto.ErrorEnum;

import java.io.IOException;
import java.io.InputStream;


/**
 * @author dongheonlee
 * AWS polly를 활용한 tts 구현 컨트롤러
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/polly")
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

        log.info("--------------------controller로 텍스트 음성 변환 요청");
        
        if (!BALPYO_API_KEY.equals(pollyDTO.getBalpyoAPIKey())) {
            return CommonResponse.error(ErrorEnum.BALPYO_API_KEY_ERROR);
        }

        try {
            // Amazon Polly와 통합하여 텍스트를 음성으로 변환
            InputStream audioStream = pollyService.synthesizeSpeech(pollyDTO);


            if (audioStream == null) {
                log.error("Amazon Polly 음성 변환 실패: 반환된 오디오 스트림이 null입니다.");
                return CommonResponse.error(ErrorEnum.INTERNAL_SERVER_ERROR);
            }

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
            log.error("내부 서버 오류: " + e.getMessage());
            return CommonResponse.error(ErrorEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/uploadSpeech")
    public ResponseEntity<UploadResultDTO> synthesizeAndUploadSpeech(@RequestBody PollyDTO pollyDTO) throws IOException, CannotReadException, TagException, InvalidAudioFrameException, ReadOnlyFileException {
        UploadResultDTO uploadResultDTO = pollyService.synthesizeAndUploadSpeech(pollyDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(uploadResultDTO, headers, HttpStatus.OK);
    }

}
