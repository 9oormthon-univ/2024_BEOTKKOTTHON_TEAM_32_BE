package site.v1.balpyo.guest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.v1.balpyo.common.dto.CommonResponse;
import site.v1.balpyo.common.dto.ErrorEnum;
import site.v1.balpyo.common.util.CommonUtils;
import site.v1.balpyo.guest.service.GuestService;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/guest/manage")
public class GuestController {

    private final GuestService guestService;
    @PostMapping("/uid")
    public ResponseEntity<CommonResponse> generateUniqueIdentifier(){
        return guestService.generateUID();
    }

    @GetMapping("/uid")
    public ResponseEntity<CommonResponse> verifyUID(@RequestHeader(value = "UID", required = false) String uid){
        if(CommonUtils.isAnyParameterNullOrBlank(uid))return CommonResponse.error(ErrorEnum.BALPYO_UID_KEY_MISSING);
        return guestService.verifyUID(uid);
    }

}
