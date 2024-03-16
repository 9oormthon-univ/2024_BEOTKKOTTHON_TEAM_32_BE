package site.balpyo.ai.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.balpyo.ai.dto.AIGenerateRequest;
import site.balpyo.ai.service.AIGenerateService;
import site.balpyo.common.dto.CommonResponse;
import site.balpyo.common.dto.ErrorEnum;

import javax.validation.Valid;


@CrossOrigin
@RestController
@RequestMapping("/user/ai")
@RequiredArgsConstructor
public class AIUserController {

    private final AIGenerateService aiGenerateService;

    @Value("${secrets.BALPYO_API_KEY}") //TODO :: 임시 api 시크릿 키 구현 (차후 로그인 연동시 삭제예정)
    public String BALPYO_API_KEY;

    @PostMapping("/script")
    public ResponseEntity<CommonResponse> generateScript(@Valid @RequestBody AIGenerateRequest aiGenerateRequest){

        if(!BALPYO_API_KEY.equals(aiGenerateRequest.getBalpyoAPIKey()))return CommonResponse.error(ErrorEnum.BALPYO_API_KEY_ERROR);
        //TODO :: 임시 api 시크릿 키 구현 (차후 로그인 연동시 삭제예정)

        return aiGenerateService.generateScript(aiGenerateRequest);
    }

}
