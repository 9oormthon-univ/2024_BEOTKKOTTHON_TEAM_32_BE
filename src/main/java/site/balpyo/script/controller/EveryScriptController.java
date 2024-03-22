package site.balpyo.script.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import site.balpyo.script.dto.ScriptRequest;
import site.balpyo.script.service.ScriptService;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/every")
public class EveryScriptController {

    private final ScriptService scriptService;

    @PostMapping("/script")
    public void saveScript(@RequestBody ScriptRequest scriptRequest){
        scriptService.saveScript(scriptRequest);
    }
}
