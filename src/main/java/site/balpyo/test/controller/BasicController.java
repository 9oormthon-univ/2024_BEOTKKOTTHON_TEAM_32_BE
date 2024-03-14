package site.balpyo.test.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.balpyo.test.entity.TestEntity;
import site.balpyo.test.repository.TestRepository;

import java.time.LocalDate;
import java.time.LocalTime;


@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class BasicController {
    private final TestRepository testRepository;
    @GetMapping("/status")
    public String welcome(){
        return "발표몇분 발표짱즈 빠이팅" + LocalTime.now();
    }

    @GetMapping("/db_insert")
    public Object dbInsert() {
        try {
            TestEntity testEntity = TestEntity.builder()
                    .test_content("data")
                    .build();
            testRepository.save(testEntity);
            return testEntity;
        } catch (Exception e) {
            return (e);
        }
    }

    @GetMapping("/db_read")
    public Object dbRead() {
        try {
            return testRepository.findAll();
        } catch (Exception e) {
            return (e);
        }
    }
}
