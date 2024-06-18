package site.balpyo.ai.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import site.balpyo.ai.dto.AIGenerateRequest;
import site.balpyo.ai.dto.AIGenerateResponse;
import site.balpyo.ai.entity.AIGenerateLogEntity;
import site.balpyo.ai.entity.GPTInfoEntity;
import site.balpyo.ai.repository.AIGenerateLogRepository;
import site.balpyo.common.dto.CommonResponse;
import site.balpyo.common.dto.ErrorEnum;
import site.balpyo.common.util.CommonUtils;
import site.balpyo.guest.entity.GuestEntity;
import site.balpyo.guest.repository.GuestRepository;


import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AIGenerateService {


    private final AIGenerateUtils aiGenerateUtils;

    private final AIGenerateLogRepository aiGenerateLogRepository;

    private final GuestRepository guestRepository;

    @Value("${secrets.GPT_API_KEY}")
    public String GPT_API_KEY;
    @Transactional
    public ResponseEntity<CommonResponse> generateScript(AIGenerateRequest request,String uid){

        // TODO :: TEST인 경우 TEST값 반환 <- 개발 완료 후 삭제 예정
        if(request.isTest()) return CommonResponse.success(new GPTTestObject().getGPTTestObject());

        //API KEY가 없는경우 에러 반환
        String CURRENT_GPT_API_KEY = GPT_API_KEY; if(CommonUtils.isAnyParameterNullOrBlank(CURRENT_GPT_API_KEY)) return CommonResponse.error(ErrorEnum.GPT_API_KEY_MISSING);

        //1. 주제, 소주제, 시간을 기반으로 프롬프트 생성
        String currentPromptString = aiGenerateUtils.createPromptString(request.getTopic(), request.getKeywords(), request.getSecTime());
        //2. 작성된 프롬프트를 기반으로 GPT에게 대본작성 요청
        ResponseEntity<Map> generatedScriptObject = aiGenerateUtils.requestGPTTextGeneration(currentPromptString, 0.5f, 100000, CURRENT_GPT_API_KEY);
        //3. GPT응답을 기반으로 대본 추출 + 대본이 없다면 대본 생성 실패 에러 반환
        Object resultScript = generatedScriptObject.getBody().get("choices"); if(CommonUtils.isAnyParameterNullOrBlank(resultScript)) return CommonResponse.error(ErrorEnum.GPT_GENERATION_ERROR);

        //4. GPT 응답에서 Body 추출
        Object resultBody = generatedScriptObject.getBody();
        //5. GPT 응답에서 GPTInfoEntity 추출 및 jpa로 저장할 수 있도록 GPTInfoEntity로변환
        GPTInfoEntity gptInfoData = new GPTInfoEntity().ResponseBodyToGPTInfoEntity(resultBody);


        GuestEntity guestEntity = null;
        Optional<GuestEntity> optionalGuestEntity= guestRepository.findById(uid);
        if(optionalGuestEntity.isPresent()){
            guestEntity = optionalGuestEntity.get();
        }

        //6. AI 사용기록에 gpt정보와 요청값들을 AIGenerateLogEntity형태로 변환
        AIGenerateLogEntity aiGenerateLog = new AIGenerateLogEntity().convertToEntity(request , gptInfoData,guestEntity);



        aiGenerateLogRepository.save(aiGenerateLog); //저장
        String GPTId = aiGenerateLog.getGptInfoEntity().getGptInfoId();

        log.info("-------------------- 저장된 사용 기록 : " + aiGenerateLog);

        return CommonResponse.success(new AIGenerateResponse(resultScript,GPTId));
    }






}
