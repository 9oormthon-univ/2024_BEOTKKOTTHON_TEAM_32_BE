package site.balpyo.script.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.balpyo.common.dto.CommonResponse;
import site.balpyo.common.dto.ErrorEnum;
import site.balpyo.common.util.CommonUtils;
import site.balpyo.script.dto.ScriptRequest;
import site.balpyo.script.service.ScriptService;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/every/manage")
public class EveryScriptController {

    private final ScriptService scriptService;

    @PostMapping("/script")
    public ResponseEntity<CommonResponse> saveScript(@RequestBody ScriptRequest scriptRequest,
                                                     @RequestHeader(value = "UID", required = false) String uid){

        if(CommonUtils.isAnyParameterNullOrBlank(uid)) return CommonResponse.error(ErrorEnum.BALPYO_UID_KEY_MISSING);
        return scriptService.saveScript(scriptRequest, uid);
    }

    @GetMapping("/script")
    public ResponseEntity<CommonResponse> getScript(@RequestHeader(value = "UID", required = false) String uid) {

        System.out.println("진입");
        if (CommonUtils.isAnyParameterNullOrBlank(uid)) return CommonResponse.error(ErrorEnum.BALPYO_UID_KEY_MISSING);
        return scriptService.getScript(uid);
    }

}
