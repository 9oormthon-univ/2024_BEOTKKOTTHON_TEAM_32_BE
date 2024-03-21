package site.balpyo.guest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.balpyo.common.dto.CommonResponse;
import site.balpyo.guest.service.GuestService;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/guest")
public class GuestController {

    private final GuestService guestService;
    @PostMapping("/uid")
    public ResponseEntity<CommonResponse> generateUniqueIdentifier(){
        return guestService.generateUID();
    }

    @GetMapping("/uid")
    public ResponseEntity<CommonResponse> verifyUID(@RequestParam String uid){
        return guestService.verifyUID(uid);
    }

}
