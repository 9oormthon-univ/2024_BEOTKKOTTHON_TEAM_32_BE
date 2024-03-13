package site.balpyo.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BasicController {
    @GetMapping("/")
    public String welcome(){
        return "발표몇분 발표짱즈 빠이팅";
    }
}
