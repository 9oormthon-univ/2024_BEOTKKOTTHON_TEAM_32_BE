package site.balpyo.script.service;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import site.balpyo.ai.entity.AIGenerateLogEntity;
import site.balpyo.ai.entity.GPTInfoEntity;
import site.balpyo.ai.repository.AIGenerateLogRepository;
import site.balpyo.ai.repository.GPTInfoRepository;
import site.balpyo.common.dto.CommonResponse;
import site.balpyo.guest.entity.GuestEntity;
import site.balpyo.guest.repository.GuestRepository;
import site.balpyo.script.dto.ScriptRequest;
import site.balpyo.script.entity.ScriptEntity;
import site.balpyo.script.repository.ScriptRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScriptService {

    private final ScriptRepository scriptRepository;

    private final GuestRepository guestRepository;
    private final AIGenerateLogRepository aiGenerateLogRepository;
    private final GPTInfoRepository gptInfoRepository;


    public ResponseEntity<CommonResponse> saveScript(ScriptRequest scriptRequest) {


        Optional<GuestEntity> guestEntity = guestRepository.findById(scriptRequest.getUid());
        Optional<GPTInfoEntity> gptInfoEntity = gptInfoRepository.findById(scriptRequest.getGptId());
        Optional<AIGenerateLogEntity> aiGenerateLogEntity = aiGenerateLogRepository.findByGptInfoEntity(gptInfoEntity.get());


        ScriptEntity scriptEntity = ScriptEntity.builder()
                .title(scriptRequest.getTitle())
                .script(scriptRequest.getScript())
                .secTime(scriptRequest.getSecTime())
                .guestEntity(guestEntity.get())
                .aiGenerateLogEntity(aiGenerateLogEntity.get())
                .build();

        scriptRepository.save(scriptEntity);

        return CommonResponse.success(scriptEntity);
    }
}
