package site.v2.balpyo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BalpyoHelperApplication {
	static {
		System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");
	} //  spring-cloud-starter-aws 의존성 주입시, 로컬 환경은 aws 환경이 아니라서 발생하는 에러 없애기

	public static void main(String[] args) {
		SpringApplication.run(BalpyoHelperApplication.class, args);
	}

}
