package site.v2.balpyo.ai.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/admin/ai")
public class AIAdminController {
    @GetMapping("/log")
    public void getAIGenerateLog(){
        return;
    }

}
