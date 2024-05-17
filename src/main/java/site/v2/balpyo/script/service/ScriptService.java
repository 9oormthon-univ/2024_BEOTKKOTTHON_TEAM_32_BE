package site.v2.balpyo.script.service;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import site.v2.balpyo.ai.entity.AIGenerateLogEntity;
import site.v2.balpyo.ai.entity.GPTInfoEntity;
import site.v2.balpyo.ai.repository.AIGenerateLogRepository;
import site.v2.balpyo.ai.repository.GPTInfoRepository;
import site.v2.balpyo.common.dto.CommonResponse;
import site.v2.balpyo.common.dto.ErrorEnum;
import site.v2.balpyo.guest.entity.GuestEntity;
import site.v2.balpyo.guest.repository.GuestRepository;
import site.v2.balpyo.script.dto.ScriptRequest;
import site.v2.balpyo.script.dto.ScriptResponse;
import site.v2.balpyo.script.entity.ScriptEntity;
import site.v2.balpyo.script.entity.ScriptStatus;
import site.v2.balpyo.script.repository.ScriptRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScriptService {

    private final ScriptRepository scriptRepository;
    private final GuestRepository guestRepository;
    private final AIGenerateLogRepository aiGenerateLogRepository;
    private final GPTInfoRepository gptInfoRepository;


    public ResponseEntity<CommonResponse> saveScript(ScriptRequest scriptRequest, String uid) {

        GuestEntity guestEntity = null;
        if (uid != null) {
            guestEntity = guestRepository.findById(uid).orElse(null);
        }

        GPTInfoEntity gptInfoEntity = null;
        if (scriptRequest.getGptId() != null) {
            gptInfoEntity = gptInfoRepository.findById(scriptRequest.getGptId()).orElse(null);
        }

        AIGenerateLogEntity aiGenerateLogEntity = null;
        if (gptInfoEntity != null) {
            Optional<AIGenerateLogEntity> aiGenerateLogEntityOptional = aiGenerateLogRepository.findByGptInfoEntity(gptInfoEntity);
            aiGenerateLogEntity = aiGenerateLogEntityOptional.orElse(null);
            System.out.println(aiGenerateLogEntity.getTopic());
        }

        ScriptEntity scriptEntity = ScriptEntity.builder()
                .title(scriptRequest.getTitle())
                .script(scriptRequest.getScript())
                .secTime(scriptRequest.getSecTime())
                .guestEntity(guestEntity)
                .aiGenerateLogEntity(aiGenerateLogEntity)
                .voiceFilePath(scriptRequest.getVoiceFilePath())
                .build();

        scriptRepository.save(scriptEntity);


        ScriptResponse scriptResponse = ScriptResponse.builder()
                .scriptId(scriptEntity.getScript_id())
                .script(scriptRequest.getScript())
                .gptId(scriptRequest.getGptId())
                .uid(uid)
                .title(scriptRequest.getTitle())
                .secTime(scriptRequest.getSecTime())
                .build();


        return CommonResponse.success(scriptResponse);
    }


    //new버전 1. 임시 스크립트 저장
    public ScriptEntity saveTemporaryScript(String title, Integer secTime, String uid, String generatedScript, AIGenerateLogEntity aiGenerateLogEntity) {

        GuestEntity guestEntity = null;
        if (uid != null) {
            guestEntity = guestRepository.findById(uid).orElse(null);
        }

        ScriptEntity scriptEntity = ScriptEntity.builder()
                .title(title)
                .script(generatedScript)
                .secTime(secTime)
                .guestEntity(guestEntity)
                .aiGenerateLogEntity(aiGenerateLogEntity)
                .voiceFilePath("")
                .status(ScriptStatus.TEMP)
                .build();

        ScriptEntity savedEntity = scriptRepository.save(scriptEntity);

        //저장된 스크립트 아이디 반환
        return scriptEntity;
    }

    //new버전 2. 최종 스크립트 저장완료
    public ResponseEntity<CommonResponse> saveFinalScript(String title, String script, Integer secTime, String uid, Long scriptId) {

        GuestEntity guestEntity = null;
        if (uid != null) {
            guestEntity = guestRepository.findById(uid).orElse(null);
        }

        Optional<ScriptEntity> optionalScriptEntity = scriptRepository.findById(scriptId);

        if(optionalScriptEntity.isPresent()){
            ScriptEntity scriptEntity = ScriptEntity.builder()
                    .title(title)
                    .script(script)
                    .secTime(secTime)
                    .guestEntity(guestEntity)
                    .status(ScriptStatus.SCRIPTCOMPLETED)
                    .build();

            ScriptEntity savedEntity = scriptRepository.save(scriptEntity);
            return CommonResponse.success(savedEntity);
        }

        return CommonResponse.error(ErrorEnum.SCRIPT_NOT_FOUND);
    }

    public ResponseEntity<CommonResponse> getAllTemporaryScript(String uid) {
        Optional<GuestEntity> guestEntity = guestRepository.findById(uid);

        if (guestEntity.isEmpty()) {
            return CommonResponse.error(ErrorEnum.GUEST_NOT_FOUND);
        }

        List<ScriptResponse> scriptResponses = new ArrayList<>();
        List<ScriptEntity> scriptEntities = guestEntity.get().getScriptEntities();

        // 필터링하여 Status가 TEMP인 ScriptEntity만 가져옴
        scriptEntities.stream()
                .filter(scriptEntity -> scriptEntity.getStatus() == ScriptStatus.TEMP)
                .forEach(scriptEntity -> {
                    ScriptResponse scriptResponse = ScriptResponse.builder()
                            .scriptId(scriptEntity.getScript_id())
                            .uid(uid)
                            .title(scriptEntity.getTitle())
                            .secTime(scriptEntity.getSecTime())
                            .voiceFilePath(scriptEntity.getVoiceFilePath())
                            .status(scriptEntity.getStatus().name())
                            .build();

                    scriptResponses.add(scriptResponse);
                });

        return CommonResponse.success(scriptResponses);
    }






    public ResponseEntity<CommonResponse> getAllScript(String uid) {
        Optional<GuestEntity> guestEntity = guestRepository.findById(uid);

        if(guestEntity.isEmpty())return CommonResponse.error(ErrorEnum.GUEST_NOT_FOUND);
        List<ScriptResponse> scriptResponses = new ArrayList<>();
        if(guestEntity.get().getScriptEntities().isEmpty())return CommonResponse.success(scriptResponses);

        List<ScriptEntity> scriptEntities = guestEntity.get().getScriptEntities();


        for(ScriptEntity scriptEntity: scriptEntities){
              ScriptResponse scriptResponse = ScriptResponse.builder()
                .scriptId(scriptEntity.getScript_id())
                .uid(uid)
                .title(scriptEntity.getTitle())
                .secTime(scriptEntity.getSecTime())
                      .voiceFilePath(scriptEntity.getVoiceFilePath())
                      .status(scriptEntity.getStatus().name())
                .build();

        scriptResponses.add(scriptResponse);
    }

        return CommonResponse.success(scriptResponses);

    }

    public ResponseEntity<CommonResponse> getDetailScript(String uid, Long scriptId) {
        Optional<ScriptEntity> optionalScriptEntity = scriptRepository.findScriptByGuestUidAndScriptId(uid,scriptId);

        if(optionalScriptEntity.isEmpty())return CommonResponse.error(ErrorEnum.SCRIPT_DETAIL_NOT_FOUND);

        ScriptEntity scriptEntity = optionalScriptEntity.get();

        ScriptResponse scriptResponse = ScriptResponse
                .builder()
                .scriptId(scriptEntity.getScript_id())
                .secTime(scriptEntity.getSecTime())
                .uid(scriptEntity.getGuestEntity().getUid())
                .title(scriptEntity.getTitle())
                .script(scriptEntity.getScript())
                .voiceFilePath(scriptEntity.getVoiceFilePath())
                .build();

        return CommonResponse.success(scriptResponse);


    }
    public ResponseEntity<CommonResponse> patchScript(ScriptRequest scriptRequest, String uid, Long scriptId) {
        Optional<ScriptEntity> optionalScriptEntity = scriptRepository.findScriptByGuestUidAndScriptId(uid, scriptId);

        if(optionalScriptEntity.isEmpty())return CommonResponse.error(ErrorEnum.SCRIPT_DETAIL_NOT_FOUND);

        ScriptEntity scriptEntity = optionalScriptEntity.get();


        scriptEntity.setScript(scriptRequest.getScript());
        scriptEntity.setTitle(scriptRequest.getTitle());
        scriptEntity.setSecTime(scriptRequest.getSecTime());



        if (scriptRequest.getVoiceFilePath() != null && !scriptRequest.getVoiceFilePath().isEmpty()) {
            scriptEntity.setVoiceFilePath(scriptRequest.getVoiceFilePath());
        }

        scriptRepository.save(scriptEntity);

        return CommonResponse.success("");


    }


    public ResponseEntity<CommonResponse> deleteScript(String uid, Long scriptId) {
        Optional<ScriptEntity> optionalScriptEntity = scriptRepository.findScriptByGuestUidAndScriptId(uid, scriptId);

        if(optionalScriptEntity.isEmpty())return CommonResponse.error(ErrorEnum.SCRIPT_DETAIL_NOT_FOUND);

        ScriptEntity scriptEntity = optionalScriptEntity.get();

        scriptRepository.delete(scriptEntity);

        return CommonResponse.success("");

    }
}
