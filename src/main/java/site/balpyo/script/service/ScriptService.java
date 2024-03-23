package site.balpyo.script.service;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import site.balpyo.ai.entity.AIGenerateLogEntity;
import site.balpyo.ai.entity.GPTInfoEntity;
import site.balpyo.ai.repository.AIGenerateLogRepository;
import site.balpyo.ai.repository.GPTInfoRepository;
import site.balpyo.common.dto.CommonResponse;
import site.balpyo.common.dto.ErrorEnum;
import site.balpyo.guest.entity.GuestEntity;
import site.balpyo.guest.repository.GuestRepository;
import site.balpyo.script.dto.ScriptRequest;
import site.balpyo.script.dto.ScriptResponse;
import site.balpyo.script.entity.ScriptEntity;
import site.balpyo.script.repository.ScriptRepository;

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
                .build();

        scriptRepository.save(scriptEntity);

        ScriptResponse scriptResponse = new ScriptResponse(scriptRequest.getScript(), scriptRequest.getGptId(),uid,scriptRequest.getTitle(),scriptRequest.getSecTime());

        return CommonResponse.success(scriptResponse);
    }

    public ResponseEntity<CommonResponse> getScript(String uid) {
        Optional<GuestEntity> guestEntity = guestRepository.findById(uid);

        if(guestEntity.isEmpty())return CommonResponse.error(ErrorEnum.GUEST_NOT_FOUND);
        if(guestEntity.get().getScriptEntities().isEmpty())return CommonResponse.error(ErrorEnum.SCRIPT_NOT_FOUND);

        List<ScriptEntity> scriptEntities = guestEntity.get().getScriptEntities();

        List<ScriptResponse> scriptResponses = new ArrayList<>();
        for(ScriptEntity scriptEntity: scriptEntities){
            ScriptResponse scriptResponse = ScriptResponse.builder()
                    .scriptId(scriptEntity.getScript_id())
                    .uid(uid)
                    .title(scriptEntity.getTitle())
                    .secTime(scriptEntity.getSecTime())
                    .build();

            scriptResponses.add(scriptResponse);
        }

        return CommonResponse.success(scriptResponses);

    }
}
