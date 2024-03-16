package site.balpyo.test.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.balpyo.common.dto.CommonResponse;
import site.balpyo.test.entity.TestEntity;
import site.balpyo.test.repository.TestRepository;
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

    @GetMapping("/db-insert")
    public Object dbInsert() {
            TestEntity testEntity = TestEntity.builder()
                    .testContent("testing")
                    .build();
            testRepository.save(testEntity);
            return CommonResponse.success(testEntity);
    }

    @GetMapping("/db-read")
    public Object dbRead() {
            return testRepository.findAll();
    }

}
