package site.balpyo.script.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.balpyo.common.dto.CommonResponse;
import site.balpyo.common.dto.ErrorEnum;
import site.balpyo.common.util.CommonUtils;
import site.balpyo.script.dto.ScriptRequest;
import site.balpyo.script.service.ScriptServiceDeprecated;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/every/manage")
public class EveryScriptController {

    private final ScriptServiceDeprecated scriptService;

    @PostMapping("/script")
    public ResponseEntity<CommonResponse> saveScript(@RequestBody ScriptRequest scriptRequest,
                                                     @RequestHeader(value = "UID", required = false) String uid){

        if(CommonUtils.isAnyParameterNullOrBlank(uid)) return CommonResponse.error(ErrorEnum.BALPYO_UID_KEY_MISSING);
        return scriptService.saveScript(scriptRequest, uid);
    }

    @GetMapping("/script/all")
    public ResponseEntity<CommonResponse> getAllScript(@RequestHeader(value = "UID", required = false) String uid) {

        if (CommonUtils.isAnyParameterNullOrBlank(uid)) return CommonResponse.error(ErrorEnum.BALPYO_UID_KEY_MISSING);
        return scriptService.getAllScript(uid);
    }

    @GetMapping("/script/detail/{scriptId}")
    public ResponseEntity<CommonResponse> getDetailScript(@RequestHeader(value = "UID", required = false) String uid,
    @PathVariable Long scriptId) {

        if (CommonUtils.isAnyParameterNullOrBlank(uid)) return CommonResponse.error(ErrorEnum.BALPYO_UID_KEY_MISSING);
        return scriptService.getDetailScript(uid,scriptId);
    }

    @PatchMapping("/script/detail/{scriptId}")
    public ResponseEntity<CommonResponse> patchDetailScript(@RequestBody ScriptRequest scriptRequest,
                                                     @RequestHeader(value = "UID", required = false) String uid,
                                                            @PathVariable Long scriptId){

        if(CommonUtils.isAnyParameterNullOrBlank(uid)) return CommonResponse.error(ErrorEnum.BALPYO_UID_KEY_MISSING);
        return scriptService.patchScript(scriptRequest, uid,scriptId);
    }

    @DeleteMapping("/script/detail/{scriptId}")
    public ResponseEntity<CommonResponse> deleteDetailScript(@RequestHeader(value = "UID", required = false) String uid,
                                                            @PathVariable Long scriptId){

        if(CommonUtils.isAnyParameterNullOrBlank(uid)) return CommonResponse.error(ErrorEnum.BALPYO_UID_KEY_MISSING);
        return scriptService.deleteScript(uid,scriptId);
    }

}
